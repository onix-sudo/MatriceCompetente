package com.expleo.webcm.service;

import com.expleo.webcm.entity.expleodb.ProiectSkill;
import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;

import java.util.List;

public interface SkillService {
    List<ProiectSkill> showSkillsforProject(int idProject);

    List<Skill> getSkills();

    void saveSkill(Skill theSkill);

    Skill getSkill(int theId);

    void deleteSkill(int theId);

}
