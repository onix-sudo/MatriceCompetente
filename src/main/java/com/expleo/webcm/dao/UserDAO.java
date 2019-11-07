package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.UserExpleo;

import java.util.List;

public interface UserDAO {
    public void saveNewUser(UserExpleo newUser);
    public void saveNewUserSecurityDb(UserExpleo newUser);
    public UserExpleo getUserExpleo(String search);
    public List<UserExpleo> searchUser(String text);
}
