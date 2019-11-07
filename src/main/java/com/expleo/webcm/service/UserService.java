package com.expleo.webcm.service;

import com.expleo.webcm.entity.expleodb.UserExpleo;

public interface UserService {
    public void saveNewUser(UserExpleo newUser);
    public void saveNewUserSecurityDb(UserExpleo newUser);
    public UserExpleo getUserExpleo(String search);
    public void searchUser(String text);
}
