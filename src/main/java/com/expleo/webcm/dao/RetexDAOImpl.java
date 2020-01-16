package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.Record;
import com.expleo.webcm.entity.expleodb.Solution;
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

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class RetexDAOImpl implements RetexDAO {

    private static final String TITLU = "titlu";
    private static final String DESCRIERE = "descriere";
    private static final String CATEGORIE = "categorie";

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public List<Record> searchRecords(String searchTerms, String searchCategory) {
        if(searchTerms.trim().contains(" ")){
            List<Record> foundRecords = getRecords(searchTerms,searchCategory, "phrase");
            if(foundRecords.isEmpty()){
                foundRecords.addAll(getRecords(searchTerms,searchCategory, "keyword"));
            }
            return foundRecords;
        }else {
            return getRecords(searchTerms, searchCategory, "keyword");
        }
    }

    private List<Record> getRecords(String searchTerms, String searchCategory, String searchCase) {
        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        Transaction tx = fullTextSession.beginTransaction();

        QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder()
                .forEntity(Record.class).get();

        if(searchCase.equals("phrase")) {
            org.apache.lucene.search.Query luceneQuery = phraseQuery(searchTerms, searchCategory, queryBuilder);
            Query query = fullTextSession.createFullTextQuery(luceneQuery, Record.class);
            List<Record> recordsFound = recordsIterator(query);

            tx.commit();
            session.close();
            return recordsFound;

        } else {
            org.apache.lucene.search.Query luceneQuery = keywordQuery(searchTerms, searchCategory, queryBuilder);
            Query query = fullTextSession.createFullTextQuery(luceneQuery, Record.class);
            List<Record> recordsFound = recordsIterator(query);

            tx.commit();
            session.close();
            return recordsFound;
        }
    }

    private List<Record> recordsIterator(Query query) {
//        Iterator iterator = query.iterate();
        List<Record> recordsFound = new LinkedList<>();
//        while (iterator.hasNext()) {
//            recordsFound.add((Record) iterator.next());
//        }
        for(final Object o : query.list()){
            recordsFound.add((Record) o);
        }
        return recordsFound;
    }

    private org.apache.lucene.search.Query keywordQuery(String searchTerms, String searchCategory, QueryBuilder queryBuilder) {
        switch (searchCategory) {
            case CATEGORIE:
            case TITLU:
            case DESCRIERE:
                return getLuceneQueryFromSelected(searchTerms, searchCategory, queryBuilder);
            default:
                return getLuceneQueryFromAll(searchTerms, queryBuilder);
        }
    }

    private org.apache.lucene.search.Query phraseQuery(String searchTerms, String searchCategory, QueryBuilder queryBuilder) {
        switch (searchCategory) {
            case CATEGORIE:
            case TITLU:
            case DESCRIERE:
                return getLuceneQueryPhraseSelected(searchTerms, searchCategory, queryBuilder);
            default:
                return getLuceneQueryPhraseAll(searchTerms, queryBuilder);
        }
    }

    private org.apache.lucene.search.Query getLuceneQueryFromSelected(String searchTerms, String searchCategory, QueryBuilder queryBuilder) {
        return queryBuilder.keyword()
                .onFields(searchCategory + "Kw")
                .matching(searchTerms).createQuery();
    }

    private org.apache.lucene.search.Query getLuceneQueryFromAll(String searchTerms, QueryBuilder queryBuilder) {
        return queryBuilder.keyword()
                .onFields(TITLU + "Kw", DESCRIERE + "Kw", CATEGORIE + "Kw")
                .matching(searchTerms).createQuery();
    }

    private org.apache.lucene.search.Query getLuceneQueryPhraseSelected(String searchTerms, String searchCategory, QueryBuilder queryBuilder) {
        return queryBuilder.phrase()
                .onField(searchCategory)
                .sentence(searchTerms)
                .createQuery();
    }

    private org.apache.lucene.search.Query getLuceneQueryPhraseAll(String searchTerms, QueryBuilder queryBuilder) {
        return queryBuilder.phrase()
                .onField(DESCRIERE)
                .andField(TITLU)
                .andField(CATEGORIE)
                .sentence(searchTerms)
                .createQuery();
    }

    @Override
    public Record getRecord(Integer recordId) {
        try(Session session = sessionFactory.openSession()){
            return session.get(Record.class, recordId);
        }catch (NoResultException exp)
        {
            logger.info("getRecord - " + exp.getMessage());
            return new Record();
        }
    }

    @Override
    public void saveOrUpdateRecord(Record record) {
        try(Session session = sessionFactory.openSession()){

            session.beginTransaction();
            session.saveOrUpdate(record);
            session.getTransaction().commit();
        }
    }


    @Override
    public Solution getSolution(Integer solutionId) {
        try(Session session = sessionFactory.openSession()){
            return session.get(Solution.class, solutionId);
        }catch (NoResultException exp)
        {
            logger.info("getSolution - " + exp.getMessage());
            return new Solution();
        }
    }

    @Override
    public void saveOrUpdateSolution(Solution solution) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.saveOrUpdate(solution);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Record> getLastTenRecords() {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Record order by ID_record DESC");
            query.setMaxResults(10);
            session.getTransaction().commit();
            return query.list();
        }catch (NoResultException exp) {
            logger.info("getLastTenRecords - " + exp.getMessage());
            return new ArrayList<>();
        }


    }
}
