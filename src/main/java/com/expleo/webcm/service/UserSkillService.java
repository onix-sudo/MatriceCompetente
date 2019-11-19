package com.expleo.webcm.service;

import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.UserSkill;

import java.util.List;

public interface UserSkillService {

    public List<UserSkill> getUserSkill();

    public void saveUserSkill(UserSkill theUserSkill);

    public List<UserSkill> getUserSkillByUser(UserExpleo userExpleo);

    public void removeUserSkill(UserSkill userSkill);

    public void saveUserSkill(int idUser, int idSkill, int eval);

}
