package com.expleo.webcm.service;

import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;

import java.util.List;

public interface ProiectService {

    List<Proiect> findProjectByEmail(UserExpleo user);

    void saveNewProject(Proiect proiect);

    public List<Skill> showSkillsforProject(int idProject);
}
