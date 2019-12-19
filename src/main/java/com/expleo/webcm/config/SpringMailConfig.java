package com.expleo.webcm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Contains the configuration of mail connection.
 * */
@Configuration
@PropertySource("classpath:emailConfig.properties")
public class SpringMailConfig {

    //to be more readable
    private static final String HOST = "mail.server.host";
    private static final String PORT = "mail.server.port";
    private static final String PROTOCOL = "mail.server.protocol";
    private static final String USERNAME = "mail.server.username";
    private static final String PASSWORD = "mail.server.password";

    //set up variable to hold the properties
    @Autowired
    private Environment env;


    @Bean
    public JavaMailSender mailSender(){

        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Basic mail sender configuration, based on emailConfig.properties
        mailSender.setHost(env.getProperty(HOST));
        mailSender.setPort(Integer.parseInt(env.getProperty(PORT)));
        mailSender.setProtocol(env.getProperty(PROTOCOL));
        mailSender.setUsername(env.getProperty(USERNAME));
        mailSender.setPassword(env.getProperty(PASSWORD));

        // JavaMail-specific mail sender configuration
        final Properties javaMailProperties = new Properties();
        javaMailProperties.setProperty("mail.smtp.auth", "true");
        javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");
        javaMailProperties.setProperty("mail.smtp.quitwait", "false");
        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;


    }
}
