package com.expleo.webcm.service;

import com.expleo.webcm.entity.expleodb.UserExpleo;

import java.util.List;

public interface UserService {
    public void saveNewUser(UserExpleo newUser);
    public void saveNewUserSecurityDb(UserExpleo newUser);
    public UserExpleo getUserExpleo(String search);
}
