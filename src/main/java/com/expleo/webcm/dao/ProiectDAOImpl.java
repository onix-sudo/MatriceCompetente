package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.*;
import com.expleo.webcm.helper.Principal;
import com.expleo.webcm.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * This class is implementing ProiectDAO interface which contains all methods that information from
 * proiect column is manipulated.
 * */

@Repository
public class ProiectDAOImpl implements ProiectDAO {

    private static final String COD_PROIECT = "codProiect";

    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    @Qualifier("sessionSecurityFactory")
    @Autowired
    private SessionFactory sessionSecurityFactory;

    @Autowired
    UserService userService;

    private Logger logger = Logger.getLogger(getClass().getName());
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * This method will save a detached Proiect object to the database.
     *
     *  @param proiect is a detached Proiect object.
     * */
    @Override
    public void saveNewProject(Proiect proiect) {
        Session session = openSession();
        session.save(proiect);
        closeSession(session);
    }

    /**
     * Returns a list which contains all Proiect objects of an user, objects found by searching in database by an email address.
     *
     *  @param principal is a string which contains the email address of the an user. Is called principal because was
     *                   meant to be for the principal user.
     *  @return a list with all projects of user whom email address belongs.
     * */
    @Override
    public List<Proiect> findManagerProjects(String principal) {
        Session session = openSession();

        Query<Proiect> query = session.createQuery("from Proiect where manager = :emailManager", Proiect.class);
        query.setParameter("emailManager", principal);
        List<Proiect> result = query.list();

        closeSession(session);
        return result;
    }

    /**
     * Returns a Proiect object, objects found by searching in database by its project code.
     *
     *  @param codProiect is a string which contains the project code.
     *  @return a Proiect object which has found.
     * */
    @Override
    public Proiect findProjectByCodProiect(String codProiect) {
        Session session = openSession();
        Proiect result = getProiect(codProiect, session);
        closeSession(session);
        return result;
    }

    /**
     * This method has as an input a string and two empty lists.
     * Returns a Proiect object which is found by searching of project code. Before return this method will fill
     * the users list with all users who are in the projects and skills list with all skills required for the project.
     *
     *  @param codProiect is a string which contains the project code of the an project.
     *  @param users is an empty list to populate it with users from project;
     *  @param skills is an empty list to populate it with skills from project;
     *  @return a list with all projects of user whom email address belongs.
     * */
    @Override
    public Proiect getProjectListsUsersSkills(String codProiect, List<UserExpleo> users, List<ProiectSkill> skills) {
        Session session = openSession();

        Proiect proiect = getProiect(codProiect, session);
        users.addAll(proiect.getUsers());

        Query<ProiectSkill> queryProjectSkill = session.createQuery(
                "Select ps from ProiectSkill ps JOIN FETCH ps.skill where ps.proiect.proiectId = :nrProiect", ProiectSkill.class);
        queryProjectSkill.setParameter("nrProiect", proiect.getProiectId());
        skills.addAll(queryProjectSkill.list());

        closeSession(session);
        return proiect;
    }

    /**
     * The method will add an user to a project. Also it will check if the  wanted user in the project has required skills
     * and, if not, will add the missing skills to our user.
     *
     *  @param codProiect is a project code.
     *  @param userId is the id of the wanted user into the project.
     * */
    @Override
    public void addUserToProject(String codProiect, Integer userId) {
        Session session = openSession();
        try {
            Proiect proiect = getProiect(codProiect, session);
            session.flush();

            UserExpleo user = session.get(UserExpleo.class, userId);
            user.addProiecte(proiect);

            Query<ProiectSkill> projectSkillQuery = session.createQuery("Select ps from ProiectSkill ps " +
                    "JOIN FETCH ps.skill where ps.proiect.proiectId=:id", ProiectSkill.class);
            projectSkillQuery.setParameter("id", proiect.getProiectId());
            List<ProiectSkill> projectSkillsQueryList = projectSkillQuery.list();
            session.flush();

            Query<UserSkill> userSkillQuery = session.createQuery("select us from UserSkill us " +
                    "JOIN FETCH us.skill where us.user.id=:id", UserSkill.class);
            userSkillQuery.setParameter("id", userId);
            List<UserSkill> userSkills = userSkillQuery.list();
            session.flush();

            if (userSkills.isEmpty()) {
                for (ProiectSkill proiectSkill : projectSkillsQueryList) {
                    session.merge(new UserSkill(proiectSkill.getSkill(), user));
                    session.flush();

                    saveHistory(user, proiectSkill.getSkill(), session, dateFormat);
                }
            } else {
                List<Skill> projectSkill = new LinkedList<>();
                List<Skill> userSkill = new LinkedList<>();

                for (ProiectSkill proiectSkill : projectSkillsQueryList) {
                    projectSkill.add(proiectSkill.getSkill());
                }
                for (UserSkill tempUserSkill : userSkills) {
                    userSkill.add(tempUserSkill.getSkill());
                }

                projectSkill.removeAll(userSkill);
                if (!projectSkill.isEmpty()) {
                    for (Skill tempSkill : projectSkill) {
                        session.merge(new UserSkill(tempSkill, user));
                        session.flush();

                        saveHistory(user, tempSkill, session, dateFormat);
                    }
                }
            }

        } catch (NoResultException e) {
            logger.info("addUserToProject" + e.getMessage());
        }

        closeSession(session);
    }

    /**
     * Remove an user from an project, both of them selected by params.
     *
     *  @param codProiect is a project code.
     *  @param userId is the id of the wanted user out from project.
     * */
    @Override
    public void removeUserFromProject(String codProiect, Integer userId) {
        Session session = openSession();
        Proiect proiect = getProiect(codProiect, session);
        UserExpleo user = session.load(UserExpleo.class, userId);
        user.removeProiecte(proiect);
        closeSession(session);
    }

    /**
     * Allows to an user(manager) to give up on a project.
     *
     *  @param codProiect is a project code.
     * */
    @Override
    public void dropTheProject(String codProiect) {
        Session session = openSession();
        Proiect proiect = findProjectByCodProiect(codProiect);
        proiect.setManager(null);
        session.update(proiect);
        closeSession(session);
    }

    /**
     * Returns a list which contains Proiect objects, projects which does not have a manager attached.
     *
     *  @return a list with all projects without a manager attached.
     * */
    @Override
    public List<Proiect> getFreeProjects() {
        Session session = openSession();
        Query<Proiect> query = session.createQuery("from Proiect where manager = null", Proiect.class);
        List<Proiect> result = query.list();
        closeSession(session);
        return result;
    }

    /**
     * Get a project without a manager attached and will attach the given manager.
     *
     *  @param codProiect is a project code.
     *  @param principal is a string which contains the email address of the an user. Is called principal because was
     *                   meant to be for the principal user.
     * */
    @Override
    public void addFreeProject(String codProiect, String principal) {
        Session session = openSession();
        Proiect proiect = findProjectByCodProiect(codProiect);
        proiect.setManager(principal);
        session.update(proiect);
        closeSession(session);
    }

    /**
     * The method will add a skill to a project. Also it will check every user from the project if has required skill
     * and, if not, will add the missing skill to the users.
     *
     *  @param codProiect is a project code.
     *  @param skillId is the id of the wanted skill into the project.
     * */
    @Override
    public void addSkillToProject(String codProiect, Integer skillId) {
        Session session = openSession();

        Proiect proiect = getProiect(codProiect, session);
        Skill skill = session.load(Skill.class, skillId);

        List<UserExpleo> projectUsers = proiect.getUsers();
        for (UserExpleo tempUser : projectUsers) {
            if (tempUser.getUserSkills().isEmpty()) {
                session.merge(new UserSkill(skill, tempUser));
                session.flush();
                saveHistory(tempUser, skill, session, dateFormat);
            } else {
                List<Skill> tempUserSkills = new LinkedList<>();
                for (UserSkill tempUserSkill : tempUser.getUserSkills()) {
                    tempUserSkills.add(tempUserSkill.getSkill());
                }
                if (!tempUserSkills.contains(skill)) {
                    session.merge(new UserSkill(skill, tempUser));
                    session.flush();
                    saveHistory(tempUser, skill, session, dateFormat);
                    session.flush();
                }
            }
        }
        ProiectSkill ps = new ProiectSkill(proiect, skill);
        session.merge(ps);
        closeSession(session);
    }

    /**
     * This method will remove a skill from a project, both of them selected by params;
     *
     *  @param codProiect is a project code.
     *  @param skillId is the id of the wanted skill to be out from project.
     * */
    @Override
    public void removeSkillFromProject(String codProiect, Integer skillId) {
        Session session = openSession();

        Proiect proiect = session.load(Proiect.class, findProjectByCodProiect(codProiect).getProiectId());
        Skill skill = session.load(Skill.class, skillId);

        ProiectSkill ps = new ProiectSkill(proiect, skill);

        session.remove(ps);
        closeSession(session);
    }

    /**
     * This is setting the importance of a wanted skill on a wanted project
     *
     *  @param codProiect is a code project.
     *  @param skillId is an id of a skill.
     *  @param pondere is the level of importance of the skill.
     * */
    @Override
    public void setPondere(String codProiect, Integer skillId, Integer pondere) {
        Session session = openSession();

        Proiect proiect = getProiect(codProiect, session);

        List<ProiectSkill> proiectSkills = proiect.getSkills();
        for (ProiectSkill temp : proiectSkills) {
            if (temp.getSkill().getIdSkill() == skillId) {
                temp.setPondere(pondere);
            }
        }

        closeSession(session);
    }

    /**
     * This is setting the wanted level of a skill on a wanted project
     *
     *  @param codProiect is a code project.
     *  @param skillId is an id of a skill.
     *  @param target is the wanted level of the skill.
     * */
    @Override
    public void setTarget(String codProiect, Integer skillId, Integer target) {
        Session session = openSession();

        Proiect proiect = getProiect(codProiect, session);

        List<ProiectSkill> proiectSkills = proiect.getSkills();
        for (ProiectSkill temp : proiectSkills) {
            if (temp.getSkill().getIdSkill() == skillId) {
                temp.setTarget(target);
            }
        }

        closeSession(session);
    }

    /**
     * Check a project is is existing in the database.
     *
     *  @param codProiect is a project code.
     *  @return is exists or not.
     * */
    @Override
    public boolean foundCodProiectExpleo(String codProiect) {
        try(Session session = openSession()) {
            Query query = session.createQuery("from Proiect where codProiect = :codProiect");
            query.setParameter(COD_PROIECT, codProiect);
            query.getSingleResult();
            return true;
        } catch (NoResultException e) {
            logger.info(e.getMessage());
            return false;
        }
    }

    /**
     * Checks if the logged user has the project from param.
     *
     *  @param codProiect is a project code;
     *  @return if the logged user has the project or not.
     * */
    @Override
    public boolean hasPrincipalProject(String codProiect) {
        try (Session session = openSession()) {
            Query query = session.createQuery("from Proiect where codProiect = :codProiect and manager = :emailManager ");
            query.setParameter(COD_PROIECT, codProiect);
            query.setParameter("emailManager", Principal.getPrincipal());
            query.getSingleResult();
            return true;
        } catch (NoResultException e) {
            logger.info("ProiectDAOImpl.hasPrincipalProject - no result");
            logger.info(e.getMessage());
            return false;
        }
    }

    /**
     * Returns all projects of the logged user.
     *  @return a list with all projects of the logged user.
     * */
    @Override
    public List<Proiect> findPrincipalProjects() {
        try (Session session = openSession()) {
            Query<UserExpleo> query = session.createQuery("Select user from UserExpleo user " +
                    "JOIN FETCH user.proiecte where email=:email", UserExpleo.class);
            query.setParameter("email", Principal.getPrincipal());
            UserExpleo foundUser = query.getSingleResult();
            return foundUser.getProiecte();
        } catch (NoResultException e) {
            logger.info("ProiectDAOImpl.findPrincipalProjects - no result");
            logger.info(e.getMessage());
            return new LinkedList<>();
        }
    }

    /**
     * Is a helper method which fill empty lists with users, skills and userSkills objects.
     * This will populate the lists with objects based on a project.
     *
     *  @param codProiect is a project code.
     *  @param foundUsers is an empty list of users.
     *  @param foundSkills is an empty list of skills.
     *  @param foundUserSkills is an empty list of userSkills.
     * */
    @Override
    public void findProjectUsersAndSkills(String codProiect, List<UserExpleo> foundUsers, List<ProiectSkill> foundSkills, List<UserSkill> foundUserSkills) {
        try (Session session = openSession()) {
            Proiect foundProject = getProiect(codProiect, session);
            foundUsers.addAll(foundProject.getUsers());

            Query<ProiectSkill> projectSkillsQuery = session.createQuery("SELECT skills from ProiectSkill " +
                    "skills JOIN FETCH skills.skill where ID_Proiect=:projectID", ProiectSkill.class);
            projectSkillsQuery.setParameter("projectID", foundProject.getProiectId());

            foundSkills.addAll(projectSkillsQuery.list());

            for (UserExpleo tempUserExpleo : foundUsers) {
                for (UserSkill tempUserSkill : tempUserExpleo.getUserSkills()) {
                    for (ProiectSkill tempProjectSkill : foundSkills) {
                        if (tempProjectSkill.getSkill().equals(tempUserSkill.getSkill())) {
                            foundUserSkills.add(tempUserSkill);
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns a Proiect object.
     *
     *  @param codProiect is a project code.
     *  @return a project.
     * */
    @Override
    public Proiect getProjectByCodProiect(String codProiect) {
        try(Session session = openSession()){
            return getProiect(codProiect,session);
        }
    }

    /**
     * A helper method to not repeat code. It will save a new user history;
     * @param user UserExpleo object.
     * @param skill Skill object.
     * @param session is an opened session.
     * @param dateFormat is an object to modify the current date with a pattern.
     * */
    private void saveHistory(UserExpleo user, Skill skill, Session session, SimpleDateFormat dateFormat) {
        session.merge(new History(user, skill, 1, dateFormat.format(Calendar.getInstance().getTime())));
        session.flush();
    }

    /**
     * A helper method to not repeat code. It will close the session;
     * @param session is an opened session.
     * */
    private void closeSession(Session session) {
        session.getTransaction().commit();
        session.close();
    }

    /**
     * A helper method to not repeat code.
     *  @return an open session.
     * */
    private Session openSession() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
    }

    /**
     * Returns a Proiect object.
     *
     *  @param codProiect is a project code.
     *  @param session is a opened session.
     *  @return a project.
     * */
    private Proiect getProiect(String codProiect, Session session) {
        Query query = session.createQuery("from Proiect where codProiect = :" + COD_PROIECT);
        query.setParameter(COD_PROIECT, codProiect);
        return (Proiect) query.getSingleResult();
    }
}
