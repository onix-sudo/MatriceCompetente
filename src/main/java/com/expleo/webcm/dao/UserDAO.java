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


    boolean checkIfValidOldPassowrd(String oldPassword);

    void changePassword(String newPassword, Integer id);

    String createResetPasswordDetails(int id);

    LoginUser getLoginUserByToken(String token);

    boolean foundEmailExpleo(String email);
    boolean foundNumarMatricolExpleo(Integer numarMatricol);
    boolean foundCodProiectExpleo(String codProiect);
    boolean foundResetToken(String resetToken);
}
