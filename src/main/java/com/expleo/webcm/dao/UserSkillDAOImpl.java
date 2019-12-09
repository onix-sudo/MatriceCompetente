package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.*;
import com.expleo.webcm.helper.Principal;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        UserSkill userSkill = new UserSkill(session.load(Skill.class, idSkill),session.load(UserExpleo.class, idUserExpleo));
        session.save(userSkill);
        session.flush();

        Query usQuery = session.createQuery("from UserSkill where user.id= :id and skill.id=:skillId");
        usQuery.setParameter("id", idUserExpleo);
        usQuery.setParameter("skillId", idSkill);

        UserSkill us = (UserSkill) usQuery.getSingleResult();
        session.merge(new History(us.getId(), 1, dateFormat.format(Calendar.getInstance().getTime())));
        session.flush();

        session.getTransaction().commit();
        session.close();

    }


    public void saveUserSkill(int idUser, int idSkill, int eval) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query userSkillQuery = session.createQuery("select us from UserSkill us where user.id=: idUser and skill.id= :idSkill");
        userSkillQuery.setParameter("idUser", idUser);
        userSkillQuery.setParameter("idSkill", idSkill);


        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            UserSkill userSkill = (UserSkill) userSkillQuery.getSingleResult();
            session.flush();
            userSkill.setEvaluation(eval);
            boolean update = false;

            try {
                Query historyQuery = session.createQuery("from History where idUserSkill=: idUser");
                historyQuery.setParameter("idUser", userSkill.getId());

                List<History> history = new LinkedList<History>(historyQuery.list());
                session.flush();

                for(History temp:history){
                    if(temp.getDate().equals(userSkill.getDataEvaluare())){
                        temp.setEvaluare(eval);
                        session.update(temp);
                        session.flush();
                        update = true;
                        break;
                    }
                }
                if(!update){
                    session.save(new History(userSkill.getId(), eval, dateFormat.format(Calendar.getInstance().getTime())));
                    session.flush();
                }

            }catch (NoResultException e){
                System.out.println("UserSkillDAOImpl.saveUserSkill = no result historyQuery");
            }

            userSkill.setDataEvaluare(dateFormat.format(Calendar.getInstance().getTime()));
            session.update(userSkill);
            session.flush();

        }finally {
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public List<UserSkill> getUserSkillByUser(UserExpleo userExpleo) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("SELECT us FROM UserSkill us JOIN FETCH us.skill JOIN FETCH us.user where us.user.id = :id");
        query.setParameter("id", userExpleo.getId());

        List<UserSkill> result = (List<UserSkill>) query.list();



        session.getTransaction().commit();

        session.close();

        return result;
    }

    @Override
    public void getUserByEvaluation(List<UserSkill> userSkills, int eval){

        List<UserSkill> userSkillsLast = new LinkedList<>();

        for(UserSkill temp: userSkills) {
            if (temp.getEvaluation() < eval) {
                userSkillsLast.add(temp);
            }
        }
        userSkills.removeAll(userSkillsLast);
        userSkillsLast.clear();
    }


    @Override
    public void removeUserSkill(int idUserExpleo, int idSkill) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        UserSkill userSkill = new UserSkill(session.load(Skill.class, idSkill),session.load(UserExpleo.class, idUserExpleo));

        session.clear();
        session.delete(userSkill);

        session.getTransaction().commit();
        session.close();

    }

    @Override
    public void getAdditionalAndProjectSkill(int userId, List<UserSkill> userAdditionalSkills, List<UserSkill> projectSkills) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query queryAllUserSkills = session.createQuery("SELECT us FROM UserSkill us JOIN FETCH us.skill JOIN FETCH us.user where us.user.id = :id");
        queryAllUserSkills.setParameter("id", userId);

        List<UserSkill> allUserSkillsList =  new LinkedList<UserSkill>(queryAllUserSkills.list());
        session.flush();

        Query queryUser =
                session.createQuery("select user from UserExpleo user JOIN FETCH user.proiecte where user.id = :userId");
        queryUser.setParameter("userId", userId);

        UserExpleo principalUser = (UserExpleo) queryUser.getSingleResult();
        List<Proiect> principalProjects = principalUser.getProiecte();

        session.flush();

        List<Skill> skillsFromProjects = new LinkedList<>();
        for(Proiect tempProject: principalProjects){
            List<ProiectSkill> proiectSkills = tempProject.getSkills();
            for(ProiectSkill tempProjectSkill : proiectSkills){
                skillsFromProjects.add(tempProjectSkill.getSkill());
            }
            session.flush();
        }

        for(UserSkill tempUserSkill:allUserSkillsList){
            if(skillsFromProjects.contains(tempUserSkill.getSkill())){
                projectSkills.add(tempUserSkill);
            }else{
                userAdditionalSkills.add(tempUserSkill);
            }
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<UserSkill> getUserSkillByProjectSkills(Integer projectId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query projectQuery =
                session.createQuery(
                        "select pr from Proiect pr JOIN FETCH pr.skills where pr.proiectId=:projectId");
        projectQuery.setParameter("projectId", projectId);
        Proiect project = (Proiect) projectQuery.getSingleResult();
        List<ProiectSkill> projectSkills = project.getSkills();
        session.flush();

        Query principalQuery = session.createQuery("from UserExpleo where email=:email");
        principalQuery.setParameter("email", Principal.getPrincipal());
        UserExpleo principal = (UserExpleo) principalQuery.getSingleResult();
        session.flush();

        Query principalUserSkills = session.createQuery("select us from UserSkill us JOIN FETCH us.skill where us.user.id=:userId");
        principalUserSkills.setParameter("userId", principal.getId());
        List<UserSkill> principalSkills = new LinkedList<UserSkill>(principalUserSkills.list());
        session.flush();

        List<UserSkill> userSkillFromProjectSkill = new LinkedList<>();
        for(UserSkill tempUserSkill:principalSkills){
            boolean isProjectSkill = false;
            for(ProiectSkill tempProjectSkill:projectSkills){
                if(tempProjectSkill.getSkill().equals(tempUserSkill.getSkill())){
                    isProjectSkill = true;
                    break;
                }
            }
            if(isProjectSkill){
                userSkillFromProjectSkill.add(tempUserSkill);
            }
        }

        session.getTransaction().commit();
        session.close();
        return userSkillFromProjectSkill;
    }
}
