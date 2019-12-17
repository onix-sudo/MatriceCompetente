package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.Skill;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The class used for retrieving skills fields from the database
 */

@Repository
public class SkillDAOImpl implements SkillDAO {
    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    @Qualifier("sessionSecurityFactory")
    @Autowired
    private SessionFactory sessionSecurityFactory;
    /**
     * The method used for retrieving all the skills from skill table .
     * This method uses Hibernate - Query Language to the returned list .
     */
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
    /**
     * The method used for saving a skill to the skill table .
     * @param theSkill the object that will be saved
     */
    @Override
    public void saveSkill(Skill theSkill) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.saveOrUpdate(theSkill);

        session.getTransaction().commit();
        session.close();

    }
    /**
     * The method used for getting a skill from the skill table by id .
     * @param theId the id related to the retrieved Skill object
     */
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
