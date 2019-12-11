package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.History;
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
    public History getHistoryByUserId(int id) {

        Session session = sessionFactory.openSession();

        Query query = session.createQuery("FROM History WHERE idUserSkill= :id");

        query.setParameter("id", id);

        History result = session.get(History.class, id);

        session.close();

        return result;
    }
}
