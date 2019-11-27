package com.expleo.webcm.service;

import com.expleo.webcm.dao.UserDAO;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.securitydb.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional("transactionExpleoDBManager")
    public void saveNewUser(UserExpleo newUser) {
        userDAO.saveNewUser(newUser);
    }

    @Override
    @Transactional("transactionSecurityManager")
    public void saveNewUserSecurityDb(UserExpleo newUser) {
        userDAO.saveNewUserSecurityDb(newUser);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public void updateUserExpleo(UserExpleo userExpleo) {
        userDAO.updateUserExpleo(userExpleo);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public UserExpleo getUserExpleoById(int id) {
        return userDAO.getUserExpleoById(id);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public UserExpleo getUserExpleoByEmail(String email) {
        return userDAO.getUserExpleoByEmail(email);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public UserExpleo getUserExpleoPrincipal() {
        return userDAO.getUserExpleoPrincipal();
    }

    @Override
    @Transactional("transactionSecurityManager")
    public LoginUser getLoginUserById(int id) {
        return userDAO.getLoginUserById(id);
    }

    @Override
    @Transactional("transactionSecurityManager")
    public void removeManagerRole(int theId) {
        userDAO.removeManagerRole(theId);
    }

    @Override
    @Transactional("transactionSecurityManager")
    public void addManagerRole(int theId) {
        userDAO.addManagerRole(theId);
    }

    @Override
    @Transactional("transactionSecurityManager")
    public boolean checkIfValidOldPassowrd(String oldPassword) {
        return userDAO.checkIfValidOldPassowrd(oldPassword);
    }

    @Override
    @Transactional("transactionSecurityManager")
    public void changePassword(String newPassword, Integer id) {
        userDAO.changePassword(newPassword, id);
    }

    @Override
    @Transactional("transactionSecurityManager")
    public String createResetPasswordDetails(int id) {
         return userDAO.createResetPasswordDetails(id);
    }

    @Override
    @Transactional("transactionSecurityManager")
    public LoginUser getLoginUserByToken(String token) {
        return userDAO.getLoginUserByToken(token);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public boolean foundEmailExpleo(String email) {
        return userDAO.foundEmailExpleo(email);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public boolean foundNumarMatricolExpleo(Integer numarMatricol) {
        return userDAO.foundNumarMatricolExpleo(numarMatricol);
    }

    @Override
    @Transactional("transactionSecurityManager")
    public boolean foundResetToken(String resetToken) {
        return userDAO.foundResetToken(resetToken);
    }
}
