package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.*;
import com.expleo.webcm.helper.Principal;
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

@Repository
public class UserSkillDAOImpl implements UserSkillDAO {

    private Logger logger = Logger.getLogger(getClass().getName());
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;



    @Override
    public void saveUserSkill(int idUserExpleo, int idSkill) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        UserSkill saveUS = new UserSkill(session.load(Skill.class, idSkill),
                session.load(UserExpleo.class, idUserExpleo));
        session.save(saveUS);
        session.flush();

        session.merge(new History(idUserExpleo, idSkill, 1, dateFormat.format(Calendar.getInstance().getTime())));
        session.flush();

        session.getTransaction().commit();
        session.close();

    }


    public void updateUserSkill(int idUserExpleo, int idSkill, int eval) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try {
            UserSkill userSkill = getUserSkill(idUserExpleo, idSkill, session);
            userSkill.setEvaluation(eval);
            session.merge(userSkill);
            session.flush();
            boolean update = false;

            try {
                Query<History> historyQuery = session.createQuery(
                        "from History where idUser=:idUser and idSkill=:idSkill", History.class);
                historyQuery.setParameter("idUser", userSkill.getUser().getId());
                historyQuery.setParameter("idSkill", userSkill.getSkill().getIdSkill());

                List<History> history = historyQuery.list();
                session.flush();

                for(History temp:history){
                    if(temp.getDate().equals(userSkill.getDataEvaluare())){
                        temp.setEvaluare(eval);
                        session.update(temp);
                        session.flush();
                        update = true;
                        break;
                    }
                }
                if(!update){
                    session.save(new History(userSkill.getUser().getId(), userSkill.getSkill().getIdSkill(), eval, dateFormat.format(Calendar.getInstance().getTime())));
                    session.flush();
                }

            }catch (NoResultException e){
                logger.info("UserSkillDAOImpl.saveUserSkill = no result historyQuery");
            }

            userSkill.setDataEvaluare(dateFormat.format(Calendar.getInstance().getTime()));
            session.update(userSkill);
            session.flush();

        }finally {
            session.getTransaction().commit();
            session.close();
        }
    }


    @Override
    public void removeUserSkill(int idUserExpleo, int idSkill) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        UserSkill userSkill = new UserSkill(session.load(Skill.class, idSkill),session.load(UserExpleo.class, idUserExpleo));

        session.clear();
        session.delete(userSkill);

        session.getTransaction().commit();
        session.close();

    }

    @Override
    public void getAdditionalAndProjectSkill(int userId, List<UserSkill> userAdditionalSkills, List<UserSkill> projectSkills) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<UserSkill> allUserSkillsList = getUserSkillsList(userId, session);
        session.flush();

        Query<UserExpleo> queryUser =
                session.createQuery("select user from UserExpleo user " +
                        "where user.id = :userId", UserExpleo.class);
        queryUser.setParameter("userId", userId);
        UserExpleo principalUser = queryUser.getSingleResult();
        List<Proiect> principalProjects = principalUser.getProiecte();
        session.flush();

        List<Skill> skillsFromProjects = new LinkedList<>();
        for(Proiect tempProject: principalProjects){
            List<ProiectSkill> proiectSkills = tempProject.getSkills();
            for(ProiectSkill tempProjectSkill : proiectSkills){
                skillsFromProjects.add(tempProjectSkill.getSkill());
            }
            session.flush();
        }

        for(UserSkill tempUserSkill:allUserSkillsList){
            if(skillsFromProjects.contains(tempUserSkill.getSkill())){
                projectSkills.add(tempUserSkill);
            }else{
                userAdditionalSkills.add(tempUserSkill);
            }
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<UserSkill> getUserSkillByProjectSkills(Integer projectId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query projectQuery =
                session.createQuery(
                        "select pr from Proiect pr JOIN FETCH pr.skills where pr.proiectId=:projectId");
        projectQuery.setParameter("projectId", projectId);
        Proiect project = (Proiect) projectQuery.getSingleResult();
        List<ProiectSkill> projectSkills = project.getSkills();
        session.flush();

        Query principalQuery = session.createQuery("from UserExpleo where email=:email");
        principalQuery.setParameter("email", Principal.getPrincipal());
        UserExpleo principal = (UserExpleo) principalQuery.getSingleResult();
        session.flush();

        List<UserSkill> principalSkills = getUserSkillsList(principal.getId(), session);
        session.flush();

        List<UserSkill> userSkillFromProjectSkill = new LinkedList<>();
        for(UserSkill tempUserSkill:principalSkills){
            boolean isProjectSkill = false;
            for(ProiectSkill tempProjectSkill:projectSkills){
                if(tempProjectSkill.getSkill().equals(tempUserSkill.getSkill())){
                    isProjectSkill = true;
                    break;
                }
            }
            if(isProjectSkill){
                userSkillFromProjectSkill.add(tempUserSkill);
            }
        }
        session.getTransaction().commit();
        session.close();
        return userSkillFromProjectSkill;
    }

    private UserSkill getUserSkill(int idUserExpleo, int idSkill, Session session) {
        Query<UserSkill> usQuery = session.createQuery("from UserSkill where user.id= :id and skill.id=:skillId", UserSkill.class);
        usQuery.setParameter("id", idUserExpleo);
        usQuery.setParameter("skillId", idSkill);
        return usQuery.getSingleResult();
    }

    private List<UserSkill> getUserSkillsList(int userId, Session session) {
        Query<UserSkill> queryAllUserSkills = session.createQuery("SELECT us FROM UserSkill us JOIN FETCH us.skill JOIN FETCH us.user where us.user.id = :id", UserSkill.class);
        queryAllUserSkills.setParameter("id", userId);
        return queryAllUserSkills.list();
    }
}
