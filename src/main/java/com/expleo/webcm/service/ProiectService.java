package com.expleo.webcm.service;

import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.UserSkill;

import java.util.List;

public interface ProiectService {

    List<Proiect> findProjectByUser(UserExpleo user);

    void saveNewProject(Proiect proiect);

    List<Skill> showSkillsforProject(int idProject);

    List<Proiect> findManagerProjects(UserExpleo userExpleoPrincipal);

    public List<UserSkill> showEvalForUserSkills(List<Skill> skills, UserExpleo userExpleo);
}
