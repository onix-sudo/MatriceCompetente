package com.expleo.webcm.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
public class HibernateSearchExpleoUserConfig {

//        private final Logger logger = LoggerFactory.getLogger(HibernateSearchExpleoUserConfig.class);

//        @Autowired
//        DataSource dataSource;
//
//        @Bean(name = "ExpleoUserLocalManagerFactory")
//        public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
//                LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//                em.setDataSource(dataSource);
//
//                JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//                em.setJpaVendorAdapter(vendorAdapter);
//
//                return em;
//
//        }
//
//        @Bean
//        public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
//                return new PersistenceExceptionTranslationPostProcessor();
//        }
}
