package com.expleo.webcm.service;

import com.expleo.webcm.entity.expleodb.Skill;

import java.util.List;

public interface SkillService {

    List<Skill> getSkills();
    void saveSkill(Skill theSkill);
    Skill getSkill(int theId);


}
