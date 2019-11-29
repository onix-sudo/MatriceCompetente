package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.ProiectSkill;
import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.UserSkill;
import com.expleo.webcm.service.UserSkillService;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Repository
public class SearchDAOImpl implements SearchDAO {

    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserSkillService userSkillService;

    @Override
    public List<UserExpleo> searchUser(String text) {
        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);

        Transaction tx = fullTextSession.beginTransaction();

        QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder()
                .forEntity(UserExpleo.class).get();

        org.apache.lucene.search.Query query = qb
                .keyword()
                .onFields("nume", "prenume", "email", "numarMatricol")
                .matching(text)
                .createQuery();

        org.hibernate.query.Query hibQuery =
                fullTextSession.createFullTextQuery(query, UserExpleo.class);

        List<UserExpleo> result = new LinkedList<UserExpleo>(hibQuery.list());

        for (UserExpleo temp : result) {
            Hibernate.initialize(temp.getProiecte());
        }

        tx.commit();
        session.close();
        return result;
    }

    @Override
    public List<Skill> searchSkill(String text) {
        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);

        Transaction tx = fullTextSession.beginTransaction();

        QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder()
                .forEntity(Skill.class).get();

        org.apache.lucene.search.Query query = qb
                .keyword()
                .onFields("numeSkill", "categorie")
                .matching(text)
                .createQuery();

        org.hibernate.query.Query hibQuery =
                fullTextSession.createFullTextQuery(query, Skill.class);

        List<Skill> result = new LinkedList<Skill>(hibQuery.list());

        for (Skill skill : result) {
            Hibernate.initialize(skill.getUsers());
        }

        tx.commit();
        session.close();
        return result;
    }

    @Override
    public List<UserExpleo> searchUsersNotInProject(String codProiect, String searchTerm) {

        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        Transaction tx = fullTextSession.beginTransaction();

        QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder()
                .forEntity(UserExpleo.class).get();

        org.apache.lucene.search.Query query = qb
                .keyword()
                .onFields("nume", "prenume", "email", "numarMatricol")
                .matching(searchTerm)
                .createQuery();

        org.hibernate.query.Query hibQuery =
                fullTextSession.createFullTextQuery(query, UserExpleo.class);

        List<UserExpleo> pickedUsers = new LinkedList<>();
        List<UserExpleo> foundUsers = new LinkedList<UserExpleo>(hibQuery.list()) ;

        if (!foundUsers.isEmpty()) {
            for (UserExpleo user : foundUsers) {
                for(Proiect tempProiect: user.getProiecte()) {
                    if (tempProiect.getCodProiect().equals(codProiect)) {
                        pickedUsers.add(user);
                    }
                }
            }
            foundUsers.removeAll(pickedUsers);
        }

        tx.commit();
        session.close();

        return foundUsers;
    }

    @Override
    public List<Skill> searchSkillsNotInProject(String codProiect, String searchTerm) {
        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);

        Transaction tx = fullTextSession.beginTransaction();

        QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder()
                .forEntity(Skill.class).get();

        org.apache.lucene.search.Query query = qb
                .keyword()
                .onFields("numeSkill", "categorie")
                .matching(searchTerm)
                .createQuery();

        org.hibernate.query.Query hibQuery =
                fullTextSession.createFullTextQuery(query, Skill.class);

        Query hibQueryProject = session.createQuery("from Proiect where codProiect = :codProiect");
        hibQueryProject.setParameter("codProiect", codProiect);

        Proiect proiect = (Proiect) hibQueryProject.getSingleResult();
        List<Skill> foundSkills = new LinkedList<Skill>(hibQuery.list());

        List<ProiectSkill> foundProjectSkill = proiect.getSkills();
        List<Skill> pickedSkill = new LinkedList<>();

        if(!foundSkills.isEmpty()) {
            for (ProiectSkill tempExistingSkill : foundProjectSkill) {
                for(Skill tempSearchedSkill : foundSkills) {
                    if (tempExistingSkill.getSkill().getNumeSkill().equals(tempSearchedSkill.getNumeSkill())) {
                        pickedSkill.add(tempSearchedSkill);
                    }
                }
            }
            foundSkills.removeAll(pickedSkill);
        }

        tx.commit();
        session.close();
        return foundSkills;
    }

    @Override
    public List<UserSkill> searchSkillWithEvaluation(String text, int eval) {
        Session session = sessionFactory.openSession();
        System.out.println("*************************************************************************");
        FullTextSession fullTextSession = Search.getFullTextSession(session);

        Transaction tx = fullTextSession.beginTransaction();

        QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder()
                .forEntity(Skill.class).get();

        org.apache.lucene.search.Query query = qb
                .keyword()
                .onFields("numeSkill", "categorie")
                .matching(text)
                .createQuery();

        org.hibernate.query.Query hibQuery =
                fullTextSession.createFullTextQuery(query, Skill.class);

        List<Skill> result = new LinkedList<Skill>(hibQuery.list());

        System.out.println("************************************************* " +result.size());

        List<UserSkill> userSkills = new LinkedList<>();
        System.out.println("ADD ALL-------------------------------------------------------------");
        for(Skill skill: result){
//            userSkills.addAll(userSkillService.getUserSkillBySkill(skill));
            userSkills.addAll(skill.getUserSkills());
        }
        userSkillService.getUserByEvaluation(userSkills,eval);
        System.out.println("ADD ALL-------------------------------------------------------------");

        for(UserSkill userSkill:userSkills){
            Hibernate.initialize(userSkill.getUser());
        }
        System.out.println("INIT-------------------------------------------------------------");


        System.out.println("***************************************************");
        tx.commit();
        session.close();

        return userSkills;
    }
}
