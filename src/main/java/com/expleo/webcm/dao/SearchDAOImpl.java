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

import java.util.*;

/**
 * The class used for retrieving fields from the database by search terms
 */

@Repository
public class SearchDAOImpl implements SearchDAO {

    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserSkillService userSkillService;
    /**
     * The method used for searching the users by surname/first name .
     * This method uses Hibernate - Query Language to the returned list .
     * @param text the search term applied to the UserExpleo objects
     */
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
    /**
     * The method used for searching the skills from user_skill by skill name and user ID .
     * This method uses Hibernate - Query Language to the returned list .
     * @param text the search term applied to Skill objects
     * @param principalId filter the list by UserExpleo id
     */
    @Override
    public List<Skill> searchPrincipalSkill(String text, int principalId) {
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
        session.flush();

        Query hasSkill = session.createQuery("select us from UserSkill us JOIN FETCH us.skill where us.user.id=:userId");
        hasSkill.setParameter("userId", principalId);

        List<UserSkill> foundSkills = new LinkedList<UserSkill>(hasSkill.list());

        for(UserSkill tempUs:foundSkills){
            if(result.contains(tempUs.getSkill())){
                result.remove(tempUs.getSkill());
            }
        }

        tx.commit();
        session.close();
        return result;
    }
    /**
     * The method used for searching the users that are not in the current project by surname/first name .
     * This method uses Hibernate - Query Language to the returned list .
     * @param codProiect filter the list in order to retrieve the corresponding users
     * @param searchTerm the search term applied to UserExpleo objects
     */
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
        List<UserExpleo> foundUsers = new LinkedList<UserExpleo>(hibQuery.getResultList()) ;

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
    /**
     * The method used for searching the skills that are not in the current project by skill name .
     * This method uses Hibernate - Query Language to the returned list .
     * @param codProiect filter the list in order to retrieve the corresponding users
     * @param searchTerm the search term applied to Skill objects
     */
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

        Query<Proiect> hibQueryProject = session.createQuery("from Proiect where codProiect = :codProiect", Proiect.class);
        hibQueryProject.setParameter("codProiect", codProiect);
        Proiect proiect = hibQueryProject.getSingleResult();

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
    /**
     * The method used for searching the userSkills with minimum evaluation value by skill name .
     * This method uses Hibernate - Query Language to the returned list .
     * @param eval filter the list in order to retrieve the corresponding users
     * @param text the search term applied to Skill objects
     */
    @Override
    public List<UserSkill> searchSkillWithEvaluation(String text, int eval) {

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

        org.hibernate.query.Query<UserSkill> userSkillQuery = session.createQuery(
                "select us from UserSkill us JOIN FETCH " +
                        "us.user where us.evaluation >= :eval order by us.evaluation desc", UserSkill.class);
        userSkillQuery.setParameter("eval", eval);

        List result = hibQuery.list();
        List<UserSkill> userSkillsResult = userSkillQuery.list();
        List<UserSkill> userSkills = new LinkedList<>();

        for(UserSkill us: userSkillsResult){
            if(result.contains(us.getSkill())){
                userSkills.add(us);
            }
        }

        tx.commit();
        session.close();

        return userSkills;
    }
}
