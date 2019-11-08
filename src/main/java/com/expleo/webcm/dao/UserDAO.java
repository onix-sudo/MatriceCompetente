package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.securitydb.LoginUser;

import java.util.List;

public interface UserDAO {
    public void saveNewUser(UserExpleo newUser);
    public void saveNewUserSecurityDb(UserExpleo newUser);
    public UserExpleo getUserExpleoById(int id);
    public LoginUser getLoginUserById(int id);


}
