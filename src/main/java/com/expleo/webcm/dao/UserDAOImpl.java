package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.securitydb.LoginRoles;
import com.expleo.webcm.entity.securitydb.LoginUser;
import com.expleo.webcm.helper.BcryptPasswordGenerator;
import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {


    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    @Qualifier("sessionSecurityFactory")
    @Autowired
    private SessionFactory sessionSecurityFactory;

    @Override
    public void saveNewUser(UserExpleo newUser) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(newUser);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveNewUserSecurityDb(UserExpleo newUser) {

        Session session = sessionSecurityFactory.openSession();
        session.beginTransaction();

//        ====== SET ROLE_EMPLOYEE BY DEFAULT ======

        int rolesId = 1;
        LoginUser userLogin = new LoginUser();
        LoginRoles userRoles = session.get(LoginRoles.class, rolesId);

//        ====== SET DETAILS ======
        userLogin.setUserName(newUser.getEmail());
        userLogin.setPassword(new BcryptPasswordGenerator()
                .makePassword(newUser.getNume(), newUser.getNumarMatricol()));
        userLogin.setEnabled(1);
        userLogin.setResetToken(null);

        userLogin.addRoles(userRoles);

        session.save(userLogin);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateUserExpleo(UserExpleo userExpleo) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(userExpleo);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public UserExpleo getUserExpleoById(int id) {
        Session session = sessionFactory.openSession();

        UserExpleo result = session.get(UserExpleo.class, id);

        session.close();
        return result;
    }

    @Override
    public LoginUser getLoginUserById(int id) {
        Session session = sessionSecurityFactory.openSession();

        session.beginTransaction();
        LoginUser result = session.get(LoginUser.class, id);

        session.getTransaction().commit();
//        session.close();
//        sessionSecurityFactory.close();
        return result;
    }

    @Override
    public void removeManagerRole(int theId) {
        Session session = sessionSecurityFactory.openSession();
        int manager = 2;

        session.beginTransaction();
        LoginUser loginUser = session.get(LoginUser.class, theId);
        LoginRoles role = session.get(LoginRoles.class, manager);

        loginUser.removeRoles(role);
        session.getTransaction().commit();

        session.close();

        UserExpleo userExpleo = getUserExpleoById(theId);
        userExpleo.setFunctie("Colaborator");
        updateUserExpleo(userExpleo);
    }

    @Override
    public void addManagerRole(int theId) {
        Session session = sessionSecurityFactory.openSession();
        int manager = 2;

        session.beginTransaction();
        LoginUser loginUser = session.get(LoginUser.class, theId);
        LoginRoles role = session.get(LoginRoles.class, manager);

        loginUser.addRoles(role);
        session.getTransaction().commit();

        session.close();

        UserExpleo userExpleo = getUserExpleoById(theId);
        userExpleo.setFunctie("Manager");
        updateUserExpleo(userExpleo);
    }
}
