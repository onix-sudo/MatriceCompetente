package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.Skill;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.logging.Logger;

@Repository
public class SkillDaoImpl implements SkillDao{
    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    @Qualifier("sessionSecurityFactory")
    @Autowired
    private SessionFactory sessionSecurityFactory;

    private Logger myLogger = Logger.getLogger(getClass().getName());

    public List<Skill> showSkillsforProject(int idProject) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Proiect proiect = session.get(Proiect.class, idProject);

        List<Skill> skills = proiect.getSkills();

        myLogger.info(skills.toString());

        return skills;

    }
}
