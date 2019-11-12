package com.expleo.webcm.service;

import com.expleo.webcm.dao.UserDAO;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.securitydb.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;

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


}
