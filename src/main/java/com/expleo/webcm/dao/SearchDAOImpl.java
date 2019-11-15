package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SearchDAOImpl implements SearchDAO {

    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<UserExpleo> searchUser(String text) {
        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);

        Transaction tx = fullTextSession.beginTransaction();

        QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder()
                .forEntity(UserExpleo.class).get();

        org.apache.lucene.search.Query query = qb
                .keyword()
                .onFields("nume", "prenume", "email", "numarMatricol")
                .matching(text)
                .createQuery();

        org.hibernate.query.Query hibQuery =
                fullTextSession.createFullTextQuery(query, UserExpleo.class);

        List<UserExpleo> result = hibQuery.list();

        for(UserExpleo temp:result){
            Hibernate.initialize(temp.getProiecte());
        }

        tx.commit();
        session.close();
        return result;
    }

    @Override
    public List<Skill> searchSkill(String text) {
        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);

        Transaction tx = fullTextSession.beginTransaction();

        QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder()
                .forEntity(Skill.class).get();

        org.apache.lucene.search.Query query = qb
                .keyword()
                .onFields("numeSkill", "categorie")
                .matching(text)
                .createQuery();

        org.hibernate.query.Query hibQuery =
                fullTextSession.createFullTextQuery(query, Skill.class);

        List<Skill> result = hibQuery.list();

        for(Skill temp:result){
            Hibernate.initialize(temp.getProiecte());
        }

        tx.commit();
        session.close();
        return result;
    }

}
