package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.Skill;

import java.util.List;

public interface SkillDAO {

    List<Skill> getSkills();
    void saveSkill(Skill theSkill);
    Skill getSkill(int theId);
}
