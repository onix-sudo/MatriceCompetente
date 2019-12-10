package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.Skill;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HistoryDAOImpl implements HistoryDAO {

    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public List<Skill> getHistoryByUserId(int principalId) {

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Query<Skill> theQuery = session.createQuery("from user_skill where id_user=:skill", Skill.class);

        theQuery.setParameter("skillId", principalId);

        List<Skill> skills = theQuery.getResultList();

        session.getTransaction().commit();

        session.close();

        return skills;
    }
}
