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
        String table1;

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
            table1= "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<style>\n" +
                    "table, th, td {\n" +
                    "  border: 1px solid white;\n" +
                    "  border-collapse: collapse;\n" +
                    "}\n" +
                    "th, td {\n" +
                    "  background-color: #009FD9;\n" +
                    "}\n" +
                    "</style>\n" +
                    "</head>\n" +
                    "<body>" +
                    "<table style=\"width:100%\">\n" +
                    "  <tr>\n" +
                    "    <th>Name</th>\n" +
                    "    <th>Skill</th> \n" +
                    "    <th>Category</th>\n" +
                    "    <th>Assessment</th>\n" +
                    "  </tr>\n" ;
            for( UserSkill userSkill : usersSkills) {
                table1 = table1.concat(
                        "  <tr>\n" +
                        "        <td>" + userSkill.getUser().getNume()+" "+userSkill.getUser().getPrenume() +"</th>\n" +
                        "        <td>" + userSkill.getSkill().getNumeSkill() +"</th>\n" +
                        "        <td>" + userSkill.getSkill().getCategorie() +"</th>\n" +
                        "        <td>" + userSkill.getEvaluation() + "</th>\n" +
                        "    </tr>\n");
            }
            table1 = table1.concat("</table>");
            mimeMessageHelper.setText(table1, true);
            javaMailSender.send(message);

        }catch (MessagingException e){
            System.out.println("Error sending mail: " + e.getMessage());
        }
    }
}
