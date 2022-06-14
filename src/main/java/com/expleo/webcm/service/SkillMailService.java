package com.expleo.webcm.service;

import com.expleo.webcm.entity.expleodb.UserSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * Contains default message to reset a password sent by mail.
 *

 * */

@Service
public class SkillMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendSkillMail(List<UserSkill> usersSkills){
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        String table;

        try{
            mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setTo("ioana.popescu@htsg.eu");

            mimeMessageHelper.setFrom("testmailhtsr@gmail.com");
            mimeMessageHelper.setSubject("Skill Matrix Validate");

            table = "<table class=\"table table-striped\">\n" +
                    "    <thead class=\"thead-dark\">\n" +
                    "    <tr>\n" +
                    "        <th>Name</th>\n" +
                    "        <th>Skill</th>\n" +
                    "        <th>Category</th>\n" +
                    "        <th>Assessment</th>\n" +
                    "        <th></th>\n" +
                    "    </tr>\n" +
                    "    </thead>\n" +
                    "\n"+
                    "</table>";
            for( UserSkill userSkill : usersSkills) {
                table = table.concat("<table >\n" +
                        "    <tr>\n" +
                        "        <th>" + userSkill.getUser().getNume()+" "+userSkill.getUser().getPrenume() +"</th>\n" +
                        "        <th>" + userSkill.getSkill().getNumeSkill() +"</th>\n" +
                        "        <th>" + userSkill.getSkill().getCategorie() +"</th>\n" +
                        "        <th>" + userSkill.getEvaluation() + "</th>\n" +
                        "        <th></th>\n" +
                        "    </tr>\n" +
                        "    </thead>\n" +
                        "\n" +
                        "</table>");
            }
            mimeMessageHelper.setText(table, true);
            javaMailSender.send(message);

        }catch (MessagingException e){
            System.out.println("Error sending mail: " + e.getMessage());
        }
    }
}
