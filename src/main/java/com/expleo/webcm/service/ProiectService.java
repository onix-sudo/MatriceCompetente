package com.expleo.webcm.service;

import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.ProiectSkill;
import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;

import java.util.List;
import java.util.Set;

public interface ProiectService {

    List<Proiect> findProjectByUser(UserExpleo user);

    void saveNewProject(Proiect proiect);

    List<ProiectSkill> showSkillsforProject(int idProject);

    List<Proiect> findManagerProjects(UserExpleo userExpleoPrincipal);

    Proiect findProjectByCodProiect(String codProiect);

    void addUserToProject(String codProiect, Integer userId);

    void removeUserFromProject(Integer IDcodProiect, Integer userId);

    void dropTheProject(String codProiect);

    List<Proiect> getFreeProjects();

    void addFreeProject(String codProiect, UserExpleo principal);

    void addSkillToProject(String codProiect, Integer skillId);
}
