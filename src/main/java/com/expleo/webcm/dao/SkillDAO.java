package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.ProiectSkill;
import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.UserSkill;
import com.expleo.webcm.entity.expleodb.UserExpleo;

import java.util.List;

public interface SkillDAO {
    List<ProiectSkill> showSkillsforProject(int idProject);

    List<UserSkill> showEvalForUserSkills(List<ProiectSkill> skills, UserExpleo userExpleo);

    List<Skill> getSkills();

    void saveSkill(Skill theSkill);

    Skill getSkill(int theId);

    void deleteSkill(int theId);

}
