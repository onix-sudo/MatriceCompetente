package com.expleo.webcm.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Contains the configuration of dataSources transactions.
 * */

@Configuration
@EnableTransactionManagement
public class TransactionManagerController {

    @Bean(name = "transactionExpleoDBManager")
    @Autowired
    public HibernateTransactionManager transactionExpleoDBManager(SessionFactory sessionFactory){

        //setup transaction manager for expleodb
        return getTransactionManager(sessionFactory);
    }

    @Bean(name = "transactionSecurityManager")
    @Autowired
    public HibernateTransactionManager transactionSecurityManager(SessionFactory sessionFactory){

        //setup transaction manager for webcm_security
        return getTransactionManager(sessionFactory);
    }

    private HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }
}
