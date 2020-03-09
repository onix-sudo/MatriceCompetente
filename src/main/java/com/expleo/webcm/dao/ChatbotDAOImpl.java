package com.expleo.webcm.dao;


import com.expleo.webcm.entity.expleodb.Solution;
import org.hibernate.Hibernate;
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

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class ChatbotDAOImpl implements ChatbotDAO{
    private static final String SOLUTIE = "solutie";

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public List<Solution> searchSolutions(String searchTerms) {
        if(searchTerms.trim().contains(" ")){
            List<Solution> foundSolutions = getSolutions(searchTerms, "phrase");
            if(foundSolutions.isEmpty()){
                foundSolutions.addAll(getSolutions(searchTerms, "keyword"));
            }

            return foundSolutions;
        }else {
            return getSolutions(searchTerms,"keyword");
        }
    }


    private List<Solution> getSolutions(String searchTerms, String searchCase) {
        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        Transaction tx = fullTextSession.beginTransaction();

        QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder()
                .forEntity(Solution.class).get();

        if (searchCase.equals("phrase")) {
            org.apache.lucene.search.Query luceneQuery = phraseQuery(searchTerms, queryBuilder);
            Query query = fullTextSession.createFullTextQuery(luceneQuery, Solution.class);
            List<Solution> solutionsFound = solutionsIterator(query);

            for (Solution solution : solutionsFound) {
                Hibernate.initialize(solution);
                Hibernate.initialize(solution.getRecord());
            }

            tx.commit();
            session.close();
            return solutionsFound;

        } else {
            org.apache.lucene.search.Query luceneQuery = keywordQuery(searchTerms, queryBuilder);
            Query query = fullTextSession.createFullTextQuery(luceneQuery, Solution.class);
            List<Solution> solutionsFound = solutionsIterator(query);

            for (Solution solution : solutionsFound) {
                Hibernate.initialize(solution);
                Hibernate.initialize(solution.getRecord());
            }

            tx.commit();
            session.close();
            return solutionsFound;
        }
    }

    private List<Solution> solutionsIterator(Query query) {
        List<Solution> solutionsFound = new LinkedList<>();
        for(final Object o : query.list()){
            solutionsFound.add((Solution) o);
        }
        return solutionsFound;
    }

    private org.apache.lucene.search.Query keywordQuery(String searchTerms, QueryBuilder queryBuilder) {
        return getLuceneQueryFromAll(searchTerms, queryBuilder);
    }

    private org.apache.lucene.search.Query phraseQuery(String searchTerms, QueryBuilder queryBuilder) {
        return getLuceneQueryPhraseAll(searchTerms, queryBuilder);
    }

    private org.apache.lucene.search.Query getLuceneQueryFromAll(String searchTerms, QueryBuilder queryBuilder) {
        return queryBuilder.keyword()
                .onFields(SOLUTIE + "Kw")
                .matching(searchTerms).createQuery();
    }

    private org.apache.lucene.search.Query getLuceneQueryPhraseAll(String searchTerms, QueryBuilder queryBuilder) {
        return queryBuilder.phrase()
                .onField(SOLUTIE)
                .sentence(searchTerms)
                .createQuery();
    }
}
