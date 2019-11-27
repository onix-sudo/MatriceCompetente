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
    boolean foundEmailExpleo(String email);
    boolean foundNumarMatricolExpleo(Integer numarMatricol);
    LoginUser getLoginUserById(int id);
    boolean foundResetToken(String resetToken);

    void removeManagerRole(int theId);
    void addManagerRole(int theId);


    boolean checkIfValidOldPassowrd(String oldPassword);

    void changePassword(String newPassword, Integer id);

    String createResetPasswordDetails(int id);

    LoginUser getLoginUserByToken(String token);
}
