package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.ProiectSkill;
import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.UserSkill;
import org.hibernate.Hibernate;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class SkillDAOImpl implements SkillDAO {
    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    @Qualifier("sessionSecurityFactory")
    @Autowired
    private SessionFactory sessionSecurityFactory;

    private Logger myLogger = Logger.getLogger(getClass().getName());

    public List<ProiectSkill> showSkillsforProject(int idProject) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Proiect proiect = session.get(Proiect.class, idProject);

        List<ProiectSkill> skills = proiect.getSkills();

        for(ProiectSkill skill:skills){
            Hibernate.initialize(skill.getSkill());
        }

        session.getTransaction().commit();
        session.close();

        return skills;

    }


    @Override
    public List<Skill> getSkills() {

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Query<Skill> theQuery = session.createQuery("from Skill", Skill.class);

        List<Skill> skills = theQuery.getResultList();

        session.getTransaction().commit();

        session.close();

        return skills;
    }

    @Override
    public List<UserSkill> showEvalForUserSkills(List<ProiectSkill> skills, UserExpleo userExpleo) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<UserSkill> userSkills = new ArrayList<>();
        for(ProiectSkill skill : skills) {
            UserSkill userSkill = new UserSkill(skill.getSkill(), userExpleo);
            userSkills.add(session.get(UserSkill.class, userSkill.getId()));
        }

        session.getTransaction().commit();

        return userSkills;
    }



    @Override
    public void saveSkill(Skill theSkill) {

        //get current hibernate session

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        //save

        System.out.println(theSkill);

        session.saveOrUpdate(theSkill);

        session.getTransaction().commit();

        session.close();

    }

    @Override
    public Skill getSkill(int theId) {

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        //retrieve/read from db using primary key
        Skill theSkill = session.get(Skill.class, theId);

        session.getTransaction().commit();

        session.close();

        return theSkill;
    }

    @Override
    public void deleteSkill(int theId) {

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        //delete from db using pk

        Query theQuery = session.createQuery("delete from Skill where id=:skillId");
        theQuery.setParameter("skillId", theId);

        theQuery.executeUpdate();

        session.getTransaction().commit();

        session.close();

    }

}
