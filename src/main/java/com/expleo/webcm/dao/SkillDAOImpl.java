package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.Skill;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SkillDAOImpl implements SkillDAO {
    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    @Qualifier("sessionSecurityFactory")
    @Autowired
    private SessionFactory sessionSecurityFactory;

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
    public void saveSkill(Skill theSkill) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.saveOrUpdate(theSkill);

        session.getTransaction().commit();
        session.close();

    }

    @Override
    public Skill getSkill(int theId) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Skill theSkill = session.get(Skill.class, theId);

        session.getTransaction().commit();
        session.close();
        return theSkill;
    }

}
