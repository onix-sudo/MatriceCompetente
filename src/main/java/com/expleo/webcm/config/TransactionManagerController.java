package com.expleo.webcm.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class TransactionManagerController {

    @Bean(name = "transactionExpleoDBManager")
    @Autowired
    public HibernateTransactionManager transactionExpleoDBManager(SessionFactory sessionFactory){

        //setup transaction manager based on session factory
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }

    @Bean(name = "transactionSecurityManager")
    @Autowired
    public HibernateTransactionManager transactionSecurityManager(SessionFactory sessionFactory){

        //setup transaction manager based on session factory
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }
}
