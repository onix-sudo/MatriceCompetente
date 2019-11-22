package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.UserSkill;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserSkillDAOImpl implements UserSkillDAO {

    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<UserSkill> getUserSkill() {

        Session currentSession = sessionFactory.openSession();

        currentSession.beginTransaction();

        Query<UserSkill> theQuery = currentSession.createQuery("from UserSkill", UserSkill.class);

        List<UserSkill> userSkills = theQuery.getResultList();


        currentSession.getTransaction().commit();

        currentSession.close();

        return userSkills;
    }



    @Override
    public void saveUserSkill(int idUserExpleo, int idSkill) {

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        UserSkill userSkill = new UserSkill(session.get(Skill.class, idSkill),session.get(UserExpleo.class, idUserExpleo));

        session.merge(userSkill);

        session.getTransaction().commit();

        session.close();

    }


    @Override
    public List<UserSkill> getUserSkillByUser(UserExpleo userExpleo) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from UserSkill where ID_user = :id");

        query.setParameter("id", userExpleo.getId());

        List<UserSkill> result = (List<UserSkill>) query.list();

        for (UserSkill userSkill : result){
            Hibernate.initialize(userSkill.getSkill());
        }

        session.getTransaction().commit();

        session.close();

        return result;
    }

    @Override
    public List<UserSkill> getUserSkillBySkill(Skill skill) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from UserSkill where ID_skill = :id");

        query.setParameter("id", skill.getIdSkill());

        List<UserSkill> result = (List<UserSkill>) query.list();

        for (UserSkill userSkill : result){
            Hibernate.initialize(userSkill.getSkill());
            Hibernate.initialize(userSkill.getUser());
        }

        session.getTransaction().commit();

        session.close();

        return result;
    }


    @Override
    public void removeUserSkill(int idUserExpleo, int idSkill) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        UserSkill userSkill = new UserSkill(session.get(Skill.class, idSkill),session.get(UserExpleo.class, idUserExpleo));

//        session.clear();

        session.delete(userSkill);

        session.getTransaction().commit();

        session.close();

    }

    public void saveUserSkill(int idUser, int idSkill, int eval) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        UserSkill userSkill = new UserSkill(session.get(Skill.class, idSkill), session.get(UserExpleo.class, idUser));
        userSkill.setEvaluation(eval);

        session.merge(userSkill);

        session.getTransaction().commit();

        session.close();

    }

}
