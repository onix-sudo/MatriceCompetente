package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.Record;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Repository
public class RetexDAOImpl implements RetexDAO {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public List<Record> searchRecords(String searchTerms, String searchCategory) {
        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        Transaction tx = fullTextSession.beginTransaction();

        QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder()
                .forEntity(Record.class).get();

        org.apache.lucene.search.Query luceneQuery = switchQuery(searchTerms, searchCategory, queryBuilder);
        Query query = fullTextSession.createFullTextQuery(luceneQuery, Record.class);

        Iterator iterator = query.iterate();
        List<Record> recordsFound = new LinkedList<>();
        while (iterator.hasNext()){
            recordsFound.add((Record) iterator.next());
        }

        tx.commit();
        session.close();
        return recordsFound;
    }

    private org.apache.lucene.search.Query switchQuery(String searchTerms, String searchCategory, QueryBuilder queryBuilder) {
        switch (searchCategory){
            case "categorie":
            case "titlu":
            case "descriere":
                return getLuceneQuery(searchTerms, searchCategory, queryBuilder);
            default: return getLuceneQuery(searchTerms, queryBuilder);
        }
    }

    private org.apache.lucene.search.Query getLuceneQuery(String searchTerms, String searchCategory, QueryBuilder queryBuilder) {
        return queryBuilder.keyword()
                .onFields(searchCategory)
                .matching(searchTerms).createQuery();
    }

    private org.apache.lucene.search.Query getLuceneQuery(String searchTerms, QueryBuilder queryBuilder) {
        return queryBuilder.keyword()
                .onFields("","")
                .matching(searchTerms).createQuery();
    }
}
