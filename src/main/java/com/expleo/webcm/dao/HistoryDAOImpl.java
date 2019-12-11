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
    public List<History> getHistoryByUserId(int id) {

        Session session = sessionFactory.openSession();

        Query<History> query = session.createQuery("FROM History where idUser= :id order by idSkill", History.class);

        query.setParameter("id", id);

        List<History> result = query.list();

        session.close();

        return result;
    }
}
