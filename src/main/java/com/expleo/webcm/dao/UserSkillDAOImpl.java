package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.UserSkill;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserSkillDAOImpl implements UserSkillDAO {

    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<UserSkill> getUserSkill() {

        Session currentSession = sessionFactory.openSession();

        currentSession.beginTransaction();

        Query<UserSkill> theQuery = currentSession.createQuery("from UserSkill", UserSkill.class);

        List<UserSkill> userSkills = theQuery.getResultList();


        currentSession.getTransaction().commit();

        currentSession.close();

        return userSkills;
    }

    @Override
    public void saveUserSkill(UserSkill theUserSkill) {

        Session currentSession = sessionFactory.openSession();

        currentSession.beginTransaction();


        System.out.println("theUser = " + theUserSkill);

        currentSession.saveOrUpdate(theUserSkill);

        currentSession.getTransaction().commit();

        currentSession.close();

    }

    @Override
    public List<UserSkill> getUserSkillByUser(UserExpleo userExpleo) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from UserSkill where ID_user = :id");

        query.setParameter("id", userExpleo.getId());

        List<UserSkill> result = (List<UserSkill>) query.list();

//        List<UserSkill> result = session.get(UserExpleo.class, userExpleo.getId()).getSkills();
//        Hibernate.initialize(session.get(UserExpleo.class, userExpleo.getId()).getSkills());

        session.getTransaction().commit();

        return result;
    }

    @Override
    public void removeUserSkill(UserSkill userSkill) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(userSkill);

        session.getTransaction().commit();

        session.close();


    }


}
