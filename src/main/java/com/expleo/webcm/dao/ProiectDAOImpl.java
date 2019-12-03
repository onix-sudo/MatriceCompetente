package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.*;
import com.expleo.webcm.helper.Principal;
import com.expleo.webcm.service.ProiectService;
import com.expleo.webcm.service.UserService;
import com.expleo.webcm.service.UserServiceImpl;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Repository
public class ProiectDAOImpl implements ProiectDAO {

    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    @Qualifier("sessionSecurityFactory")
    @Autowired
    private SessionFactory sessionSecurityFactory;

    @Autowired
    UserService userService;

    private Logger myLogger = Logger.getLogger(getClass().getName());

    @Override
    public List<Proiect> findProjectByUser(UserExpleo user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Proiect> proiecte = user.getProiecte();

        session.getTransaction().commit();
        session.close();

        return proiecte;
    }

    @Override
    public void saveNewProject(Proiect proiect) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(proiect);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Proiect> findManagerProjects(String principal) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Proiect where manager = :emailManager");
        query.setParameter("emailManager", principal);

        List<Proiect> result = new LinkedList<Proiect>(query.list());

        session.getTransaction().commit();
        session.close();

        return result;
    }

    @Override
    public Proiect findProjectByCodProiect(String codProiect) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Proiect where codProiect = :codProiect");
        query.setParameter("codProiect", codProiect);

        Proiect result = (Proiect) query.getSingleResult();

        session.getTransaction().commit();
        session.close();

        return result;
    }

    @Override
    public Proiect getProjectListsUsersSkills(String codProiect, List<UserExpleo> users, List<ProiectSkill> skills) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("from Proiect where codProiect = :codProiect");
        query.setParameter("codProiect", codProiect);
        Proiect proiect = (Proiect) query.getSingleResult();

        users.addAll(proiect.getUsers());

        Query queryProjectSkill = session.createQuery(
                "Select ps from ProiectSkill ps JOIN FETCH ps.skill where ps.proiect.proiectId = :nrProiect");
        queryProjectSkill.setParameter("nrProiect", proiect.getProiectId());

        for(ProiectSkill temp: (List<ProiectSkill>)queryProjectSkill.list()){
            skills.add(temp);
//            Hibernate.initialize(temp.getSkill());
        }

        tx.commit();
        session.close();
        return proiect;
    }

    @Override
    public List<ProiectSkill> findProjectSkillsByCodProiect(String codProject) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Proiect where codProiect = :codProiect");
        query.setParameter("codProiect", codProject);
        Proiect result = (Proiect) query.getSingleResult();

        List<ProiectSkill> foundProjectSkill = result.getSkills();

        for(ProiectSkill ps : foundProjectSkill){
            Hibernate.initialize(ps.getSkill());
        }


        session.getTransaction().commit();
        session.close();


        return foundProjectSkill;
    }

    @Override
    public void addUserToProject(String codProiect, Integer userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Proiect where codProiect = :codProiect");
        query.setParameter("codProiect", codProiect);
        Proiect proiect = (Proiect) query.getSingleResult();

        List<ProiectSkill> listSkills = proiect.getSkills();

        UserExpleo user = session.load(UserExpleo.class, userId);
        user.addProiecte(proiect);


        for (ProiectSkill ps : listSkills) {
            if (!user.getUserSkills().contains(ps)) {
                UserSkill userSkill = new UserSkill(ps.getSkill(), user);
                session.merge(userSkill);
            }
        }

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserFromProject(String codProiect, Integer userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Proiect where codProiect = :codProiect");
        query.setParameter("codProiect", codProiect);
        Proiect proiect = (Proiect) query.getSingleResult();

        UserExpleo user = session.load(UserExpleo.class, userId);

        user.removeProiecte(proiect);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropTheProject(String codProiect) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Proiect proiect = findProjectByCodProiect(codProiect);
        proiect.setManager(null);

        session.update(proiect);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Proiect> getFreeProjects() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Proiect where manager = null");

        List<Proiect> result = new LinkedList<Proiect> (query.list());

        session.getTransaction().commit();
        session.close();

        return result;
    }

    @Override
    public void addFreeProject(String codProiect, String principal) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Proiect proiect = findProjectByCodProiect(codProiect);
        proiect.setManager(principal);

        session.update(proiect);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void addSkillToProject(String codProiect, Integer skillId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Proiect where codProiect = :codProiect");
        query.setParameter("codProiect", codProiect);
        Proiect proiect = (Proiect) query.getSingleResult();
        Skill skill = session.get(Skill.class, skillId);

        List<UserExpleo> projectUsers = proiect.getUsers();

        if(!projectUsers.isEmpty()) {
            for (UserExpleo tempUser : projectUsers) {
                if (tempUser.getUserSkills().size() == 0) {
                    session.merge(new UserSkill(skill, tempUser));
                } else {
                    for (UserSkill tempUserSkill : tempUser.getUserSkills()) {
                        if (!tempUserSkill.getSkill().equals(skill)) {
                            session.merge(new UserSkill(skill, tempUser));
                            break;
                        }
                    }
                }
            }
        }

        ProiectSkill ps = new ProiectSkill(proiect, skill);

        session.merge(ps);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeSkillFromProject(String codProiect, Integer skillId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Proiect proiect = session.load(Proiect.class, findProjectByCodProiect(codProiect).getProiectId());
        Skill skill = session.load(Skill.class, skillId);

        ProiectSkill ps = new ProiectSkill(proiect, skill);

        session.remove(ps);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void setPondere(String codProiect, Integer skillId, Integer pondere) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Proiect where codProiect = :codProiect");
        query.setParameter("codProiect", codProiect);
        Proiect proiect = (Proiect) query.getSingleResult();

        List<ProiectSkill> proiectSkills = proiect.getSkills();
        for(ProiectSkill temp:proiectSkills){
            if(temp.getSkill().getIdSkill()==skillId){
                temp.setPondere(pondere);
            }
        }

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public boolean foundCodProiectExpleo(String codProiect) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Proiect where codProiect = :codProiect");
        query.setParameter("codProiect", codProiect);
        try {
            query.getSingleResult();
            return true;
        }catch (NoResultException e){
            System.out.println(e.getMessage());
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean hasPrincipalProject(String codProiect) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Proiect where codProiect = :codProiect and manager = :emailManager ");
        query.setParameter("codProiect", codProiect);
        query.setParameter("emailManager", Principal.getPrincipal());

        try {
            query.getSingleResult();
            return true;
        }catch (NoResultException e){
            System.out.println(e.getMessage());
            return false;
        }finally {
            session.close();
        }
    }
}
