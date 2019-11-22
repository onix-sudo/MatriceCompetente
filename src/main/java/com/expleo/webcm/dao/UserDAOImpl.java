package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.securitydb.LoginRoles;
import com.expleo.webcm.entity.securitydb.LoginUser;
import com.expleo.webcm.helper.BcryptPasswordHelper;
import com.expleo.webcm.helper.Principal;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserDAOImpl implements UserDAO {


    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    @Qualifier("sessionSecurityFactory")
    @Autowired
    private SessionFactory sessionSecurityFactory;

    @Autowired
    BcryptPasswordHelper bcryptPasswordHelper;

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
        userLogin.setPassword(new BcryptPasswordHelper()
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
    public UserExpleo getUserExpleoByEmail(String email) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM UserExpleo WHERE email= :email");
        query.setParameter("email", email);

        UserExpleo user = (UserExpleo) query.getSingleResult();
        Hibernate.initialize(user.getProiecte());

        session.getTransaction().commit();
        session.close();

        return user;
    }

    @Override
    public UserExpleo getUserExpleoPrincipal() {
        return getUserExpleoByEmail(Principal.getPrincipal());
    }

    @Override
    public LoginUser getLoginUserById(int id) {
        Session session = sessionSecurityFactory.openSession();
        session.beginTransaction();

        LoginUser result = session.get(LoginUser.class, id);
        Hibernate.initialize(result.getRole());

        session.getTransaction().commit();
        session.close();

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

    @Override
    public boolean checkIfValidOldPassowrd(String oldPassword) {
        Session session = sessionSecurityFactory.openSession();
        LoginUser user = session.get(LoginUser.class, getUserExpleoPrincipal().getId());
        session.close();
        return bcryptPasswordHelper.checkIfValidOldPassowrd(oldPassword, user.getPassword());
    }

    @Override
    public void changePassword(String newPassword, Integer id) {
        Session session = sessionSecurityFactory.openSession();
        session.beginTransaction();

        LoginUser user = session.load(LoginUser.class, id);
        user.setPassword(bcryptPasswordHelper.generatePassword(newPassword));

        session.update(user);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void createResetPasswordDetails(int id) {
        Session session = sessionSecurityFactory.openSession();
        session.beginTransaction();

        LoginUser loginUser = session.get(LoginUser.class, id);

        //set token
        loginUser.setResetToken(UUID.randomUUID().toString());

        //set expiry date
        loginUser.setExpiryDate(30);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public LoginUser getLoginUserByToken(String token) {
        Session session = sessionSecurityFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from LoginUser where resetToken = :token");
        query.setParameter("token", token);

        LoginUser loginUser = (LoginUser) query.getSingleResult();

        session.getTransaction().commit();
        session.close();

        return loginUser;
    }
}
