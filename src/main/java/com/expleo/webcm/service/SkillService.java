package com.expleo.webcm.service;

import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;

import java.util.List;

public interface SkillService {
    List<Skill> showSkillsforProject(int idProject);

    List<Skill> getSkills();

    void saveSkill(Skill theSkill);

    Skill getSkill(int theId);

    void deleteSkill(int theId);

//    List<Skill> showSkillsforUser(int idUser);

    void addUser(UserExpleo theUser, int id);
}
