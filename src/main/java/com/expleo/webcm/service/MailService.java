package com.expleo.webcm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String token, String mail, String name){
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try{
            mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setTo(mail);

            mimeMessageHelper.setFrom("ovidiu-marian.milea@expleogroup.com");
            mimeMessageHelper.setSubject("Reset password");
            mimeMessageHelper.setText("<html><body>Salut, "+name+"!<br/><a href='http://10.140.16.47/forgotPassword/newPassword?token="+token+"'>" +
                    " Apasa aici</a> pentru introduce o noua parola.</body></html>", true);
            javaMailSender.send(message);

        }catch (MessagingException e){
            System.out.println("Error sending mail: " + e.getMessage());
        }
    }
}
