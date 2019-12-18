package com.expleo.webcm.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Contains the configuration which starts Hibernate Search indexing .
 * */

@Component
@Transactional("transactionExpleoDBManager")
public class HibernateSearchIndexConfig  {

    @Autowired
    @Qualifier("sessionFactory")
    SessionFactory sessionFactory;

    @Bean("searchIndexer")
    public void searchIndexer() throws InterruptedException {
        Session session = sessionFactory.getCurrentSession();

        FullTextSession fullTextSession = Search.getFullTextSession(session);
        fullTextSession.createIndexer().startAndWait();
    }
}
