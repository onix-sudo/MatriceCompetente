package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.History;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * This class is implementing HistoryDAO interface which contains all methods that information from
 * history column is manipulated
 * */
@Repository
public class HistoryDAOImpl implements HistoryDAO {

    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

/**
 * Returns a list of History objects. These objects are filled with information from database obtained by searching
 * an user evaluation history by his id
 *  @param id the search is done by this id
 *  @return a list with all evaluation history of an user
 * */

    @Override
    public List<History> getHistoryByUserId(int id) {

        Session session = sessionFactory.openSession();
        Query<History> query = session.createQuery(
                "select h FROM History h join fetch h.skill where h.user.id= :id order by h.skill.id, h.date asc", History.class);

        query.setParameter("id", id);
        List<History> result = query.list();

        session.close();
        return result;
    }
}
