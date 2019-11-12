package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.Skill;

import java.util.List;

public interface SkillDao {
    public List<Skill> showSkillsforProject(int idProject);
}
