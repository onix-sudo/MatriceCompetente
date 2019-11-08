package com.expleo.webcm.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateSearchIndexConfig {

    @Autowired
    @Qualifier("sessionFactory")
    SessionFactory sessionFactory;

    public void searchIndexer(){
        Session session = sessionFactory.getCurrentSession();

        FullTextSession fullTextSession = Search.getFullTextSession(session);
        try {
            fullTextSession.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
