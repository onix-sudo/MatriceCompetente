package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.ProiectSkill;
import com.expleo.webcm.entity.expleodb.Skill;

import java.util.List;

public interface SkillDAO {
    public List<ProiectSkill> showSkillsforProject(int idProject);
}
