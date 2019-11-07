//package com.expleo.webcm.dao;
//
//import org.hibernate.SessionFactory;
//import org.hibernate.search.jpa.FullTextEntityManager;
//import org.hibernate.search.jpa.Search;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Repository;
//
//import javax.annotation.PostConstruct;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//@Repository
//public class UserSearchDAO {
//
//    @Qualifier("sessionFactory")
//    @Autowired
//    private SessionFactory sessionFactory;
//
//    @PersistenceContext
//    @Autowired
//    @Qualifier("ExpleoUserLocalManagerFactory")
//    private EntityManager entityManager;
//
//    @PostConstruct
//    public void initializeHibernateSearch() {
//
//        try {
//            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
//            fullTextEntityManager.createIndexer().startAndWait();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
