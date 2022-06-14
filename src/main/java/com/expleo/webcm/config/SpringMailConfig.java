package com.expleo.webcm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
//        javaMailProperties.setProperty("mail.smtp.auth", "true");
        javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");
//        javaMailProperties.setProperty("mail.smtp.starttls.required", "true");
//        javaMailProperties.setProperty("mail.smtp.quitwait", "false");
        javaMailProperties.setProperty("mail.smtp.ssl.trust", "*");


//        javaMailProperties.setProperty("mail.smtp.host", "smtp.gmail.com");
//        javaMailProperties.setProperty("mail.smtp.port", "587");
//        javaMailProperties.setProperty("mail.smtp.ssl.enabled", "true");
//        javaMailProperties.setProperty("mail.smtp.auth", "true");


//        Session session = Session.getInstance(javaMailProperties, new javax.mail.Authenticator() {
//
//            protected PasswordAuthentication getPasswordAuthentication() {
//
//                return new PasswordAuthentication("onixpopescu@gmail.com", "thiwmcuqjkmqzhud"); //ioana.popescu@htsg.eu
//
//            }
//
//        });
//
//        session.setDebug(true);
//        try {
//            // Create a default MimeMessage object.
//            MimeMessage message = new MimeMessage(session);
//
//            // Set From: header field of the header.
//            message.setFrom(new InternetAddress("onixpopescu@gmail.com"));
//
//            // Set To: header field of the header.
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress("ioana.popescu@htsg.eu"));
//
//            // Set Subject: header field
//            message.setSubject("This is the Subject Line!");
//
//            // Now set the actual message
//            message.setText("This is actual message");
//
//            System.out.println("sending...");
//            // Send message
//            Transport.send(message);
//            System.out.println("Sent message successfully....");
//        } catch (MessagingException mex) {
//            mex.printStackTrace();
//        }

        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;


    }
}
