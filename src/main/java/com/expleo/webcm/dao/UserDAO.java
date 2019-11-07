package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.UserExpleo;

public interface UserDAO {
    public void saveNewUser(UserExpleo newUser);
    public void saveNewUserSecurityDb(UserExpleo newUser);
    public UserExpleo getUserExpleo(String search);
    public void searchUser(String text);
}
