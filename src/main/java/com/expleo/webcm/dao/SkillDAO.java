package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.UserSkill;

import java.util.List;

public interface SkillDAO {
    public List<Skill> showSkillsforProject(int idProject);

    public List<UserSkill> showEvalForUserSkills(List<Skill> skills, UserExpleo userExpleo);
}
