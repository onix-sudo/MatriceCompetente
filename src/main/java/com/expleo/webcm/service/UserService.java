package com.expleo.webcm.service;

import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.securitydb.LoginUser;


public interface UserService {
    void saveNewUser(UserExpleo newUser);
    void saveNewUserSecurityDb(UserExpleo newUser);
    void updateUserExpleo(UserExpleo userExpleo);

    UserExpleo getUserExpleoById(int id);
    UserExpleo getUserExpleoByEmail(String email);
    UserExpleo getUserExpleoPrincipal();
    LoginUser getLoginUserById(int id);

    void removeManagerRole(int theId);
    void addManagerRole(int theId);


    boolean checkIfValidOldPassowrd(String oldPassword);

    void changePassword(String newPassword, Integer id);

    void createResetPasswordDetails(int id);

    LoginUser getLoginUserByToken(String token);
}
