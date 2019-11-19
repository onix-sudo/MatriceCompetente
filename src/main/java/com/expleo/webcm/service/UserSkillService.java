package com.expleo.webcm.service;

import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.UserSkill;

import java.util.List;

public interface UserSkillService {

    public List<UserSkill> getUserSkill();

    public List<UserSkill> getUserSkillByUser(UserExpleo userExpleo);

    public void removeUserSkill(int idUserExpleo, int idSkill);

    public void saveUserSkill(int idUser, int idSkill);

    public void saveUserSkill(int idUser, int idSkill, int eval);

}
