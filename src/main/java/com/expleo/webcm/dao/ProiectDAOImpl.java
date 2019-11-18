package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.ProiectSkill;
import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.service.UserService;
import com.expleo.webcm.service.UserServiceImpl;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
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
//        Query query = session.createQuery("from Proiect where proiectId in \n" +
//                "(Select idProiect from UserProiectId WHERE idUser = \n" +
//                "(Select id from UserExpleo WHERE email = :email))");
//        query.setParameter("email", username);
//        UserService userService = new UserServiceImpl();
//        userService.getUserExpleoByEmail(username);

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

        Hibernate.initialize(result.getUsers());
        Hibernate.initialize(result.getSkills());

        session.getTransaction().commit();
        session.close();

        return result;
    }

    @Override
    public void addUserToProject(String codProiect, Integer userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Proiect proiect = findProjectByCodProiect(codProiect);
        UserExpleo user = session.get(UserExpleo.class, userId);
        user.addProiecte(proiect);


        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserFromProject(Integer IDcodProiect, Integer userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Proiect proiect = session.get(Proiect.class, IDcodProiect);
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

        List<Proiect> result = (List<Proiect>)query.list();

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

        Proiect proiect =(Proiect) session.get(Proiect.class, findProjectByCodProiect(codProiect).getProiectId());
        Skill skill = (Skill) session.get(Skill.class, skillId);

        ProiectSkill ps = new ProiectSkill(proiect, skill, 1);
//        ps.setProiect(proiect);
//        ps.setSkill(skill);
//        ps.setPondere(1);

        session.save(ps);
        session.getTransaction().commit();
        session.close();
    }
}
