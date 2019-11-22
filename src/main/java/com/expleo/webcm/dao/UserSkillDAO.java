package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.UserSkill;

import java.util.List;

public interface UserSkillDAO {

    public List<UserSkill> getUserSkill();

    public List<UserSkill> getUserSkillByUser(UserExpleo userExpleo);


    public List<UserSkill> getUserSkillBySkill(Skill skill);

    public void removeUserSkill(int idUserExpleo, int idSkill);

    public void saveUserSkill(int idUser, int idSkill);

    public void saveUserSkill(int idUser, int idSkill, int eval);

}
