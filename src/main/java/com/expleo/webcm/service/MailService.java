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

    public void sendMail(String token, String mail){
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try{
            mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setTo(mail);

            mimeMessageHelper.setFrom("ovidiu-marian.milea@expleogroup.com");
            mimeMessageHelper.setSubject("Reset password");
            mimeMessageHelper.setText("<html><body>Salut ,<br/><a href='http://localhost/forgotPassword/newPassword?token="+token+"/'>" +
                    " Click here</a> to reset password</body></html>", true);
            javaMailSender.send(message);

        }catch (MessagingException e){
            System.out.println("Error sendig mail: " + e.getMessage());
        }
    }
}
