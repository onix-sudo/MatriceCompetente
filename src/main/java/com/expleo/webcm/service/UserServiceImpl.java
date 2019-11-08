package com.expleo.webcm.service;

import com.expleo.webcm.dao.UserDAO;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public UserExpleo getUserExpleo(String search) {
        return userDAO.getUserExpleo(search);
    }

}
