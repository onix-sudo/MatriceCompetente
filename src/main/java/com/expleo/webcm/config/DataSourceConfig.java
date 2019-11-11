package com.expleo.webcm.config;

import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@PropertySource("classpath:persistance-mysql.properties")
public class DataSourceConfig {

    //set up variable to hold the properties
    @Autowired
    private Environment env;

    //set up a logger for diag
    private Logger myLogger = Logger.getLogger(getClass().getName());


    @Bean(name = "dataSource")
    public DataSource dataSource(){

        //Create a connection pool
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try{
            dataSource.setDriverClass(env.getProperty("jdbc.driver"));
        } catch (PropertyVetoException e){
            throw new RuntimeException(e);
        }

        //log the connection props
        myLogger.info(">>>> jdbc.url = " + env.getProperty("jdbc.url"));
        myLogger.info(">>>> jdbc.user = " + env.getProperty("jdbc.user"));

        //Set database props
        dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        dataSource.setUser(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.password"));

        //Set connection pool pros
        dataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
        dataSource.setInitialPoolSize(getIntProperty("connection.pool.minPoolSize"));
        dataSource.setInitialPoolSize(getIntProperty("connection.pool.maxPoolSize"));
        dataSource.setInitialPoolSize(getIntProperty("connection.pool.maxIdleTime"));

        myLogger.info("A INTRAT AICI" + dataSource);

        return dataSource;
    }

    private Properties getHibernateProperties(){

        //set hibernate props
        Properties props = new Properties();

        props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        props.setProperty("hibernate.search.default.directory_provider",
                env.getProperty("hibernate.search.default.directory_provider"));
        props.setProperty("hibernate.hibernate.search.default.indexBase",
                env.getProperty("hibernate.search.default.indexBase"));

        return props;
    }

    @Bean (name = "sessionFactory")
    public LocalSessionFactoryBean sessionFactory(){

        //create session factory
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        //set props
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
        sessionFactory.setHibernateProperties(getHibernateProperties());

        sessionFactory.setAnnotatedClasses(UserExpleo.class);


        return sessionFactory;
    }



    //--------------------HELPER--------------------
    private int getIntProperty(String propName){
        String propVal = env.getProperty(propName);

        //convert to int
        int intPropVal = Integer.parseInt(propVal);
        return intPropVal;
    }
}
