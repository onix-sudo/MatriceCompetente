package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.securitydb.LoginUser;

import java.util.List;

public interface UserDAO {
    void saveNewUser(UserExpleo newUser);
    void saveNewUserSecurityDb(UserExpleo newUser);
    void updateUserExpleo(UserExpleo userExpleo);

    UserExpleo getUserExpleoById(int id);
    UserExpleo getUserExpleoByEmail(String email);
    UserExpleo getUserExpleoPrincipal();
    LoginUser getLoginUserById(int id);


    void removeManagerRole(int theId);
    void addManagerRole(int theId);



}
