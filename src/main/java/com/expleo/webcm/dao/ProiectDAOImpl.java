package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.*;
import com.expleo.webcm.service.UserService;
import com.expleo.webcm.service.UserServiceImpl;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
    public List<Proiect> findManagerProjects(UserExpleo userExpleo) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Proiect where manager = :nrMat");
        query.setParameter("nrMat", userExpleo.getNumarMatricol());

        List<Proiect> result = (List<Proiect>) query.list();

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

//        Hibernate.initialize(result.getUsers());
//        Hibernate.initialize(result.getSkills());
//        for (ProiectSkill temp : result.getSkills()) {
//            Hibernate.initialize(temp.getSkill());
//        }

        session.getTransaction().commit();
        session.close();

        return result;
    }

    @Override
    public void findProjectByCodProiect(String codProiect, Proiect proiect, List<UserExpleo> users, List<Skill> skills) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Proiect where codProiect = :codProiect");
        query.setParameter("codProiect", codProiect);

        proiect = (Proiect) query.getSingleResult();

        for(UserExpleo temp: proiect.getUsers()){
            users.add(temp);
        }

        for(ProiectSkill temp: proiect.getSkills()){
            skills.add(temp.getSkill());
            Hibernate.initialize(temp.getSkill());
        }

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<ProiectSkill> findProjectSkillsByCodProiect(String codProject) {
        System.out.println("***********************************************");
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
        System.out.println("***********************************************");


        return foundProjectSkill;
    }

    @Override
    public void addUserToProject(String codProiect, Integer userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Proiect proiect = session.get(Proiect.class, findProjectByCodProiect(codProiect).getProiectId());
        List<ProiectSkill> listSkills = proiect.getSkills();

        UserExpleo user = session.get(UserExpleo.class, userId);
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
        UserExpleo user = session.get(UserExpleo.class, userId);

        user.removeProiecte(proiect);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropTheProject(String codProiect) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Proiect proiect = session.get(Proiect.class, findProjectByCodProiect(codProiect).getProiectId());
        proiect.setManager(null);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Proiect> getFreeProjects() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Proiect where manager = null");

        List<Proiect> result = (List<Proiect>) query.list();

        session.getTransaction().commit();
        session.close();

        return result;
    }

    @Override
    public void addFreeProject(String codProiect, UserExpleo principal) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Proiect proiect = session.get(Proiect.class, findProjectByCodProiect(codProiect).getProiectId());
        proiect.setManager(principal.getNumarMatricol());

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void addSkillToProject(String codProiect, Integer skillId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        boolean hasSkill;
        Proiect proiect = session.get(Proiect.class, findProjectByCodProiect(codProiect).getProiectId());
        Skill skill = session.get(Skill.class, skillId);

        List<ProiectSkill> proiectSkills = proiect.getSkills();

        for (ProiectSkill temp : proiectSkills) {

            for (UserExpleo ue : temp.getProiect().getUsers()) {
                hasSkill = false;
                for (UserSkill us : ue.getUserSkills()) {
                    if (us.getSkill().getNumeSkill().equals(skill.getNumeSkill())) {
                        hasSkill = true;
                    }
                }
                if (hasSkill == false) {
                    UserSkill userSkill = new UserSkill(skill, session.get(UserExpleo.class, ue.getId()));
                    session.merge(userSkill);
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

        Proiect proiect = session.get(Proiect.class, findProjectByCodProiect(codProiect).getProiectId());
        Skill skill = session.get(Skill.class, skillId);

        ProiectSkill ps = new ProiectSkill(proiect, skill, 1);

        session.remove(ps);
        session.getTransaction().commit();
        session.close();
    }
}
