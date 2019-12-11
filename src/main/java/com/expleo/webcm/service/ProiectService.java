package com.expleo.webcm.service;

import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.ProiectSkill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.UserSkill;

import java.util.List;

public interface ProiectService {

    void saveNewProject(Proiect proiect);
    List<Proiect> getFreeProjects();
    List<Proiect> findManagerProjects(String userExpleoPrincipal);
    List<Proiect> findPrincipalProjects();
    Proiect getProjectListsUsersSkills(String codProiect, List<UserExpleo> users, List<ProiectSkill> skills);
    void findProjectUsersAndSkills(String codProiect, List<UserExpleo> foundUsers, List<ProiectSkill> foundSkills, List<UserSkill> foundUserSkills);
    void addUserToProject(String codProiect, Integer userId);
    void removeUserFromProject(String codProiect, Integer userId);
    void addFreeProject(String codProiect, String principal);
    void dropTheProject(String codProiect);
    void addSkillToProject(String codProiect, Integer skillId);
    void removeSkillFromProject(String codProiect, Integer skillId);
    void setPondere(String codProiect, Integer skillId, Integer pondere);
    void setTarget(String codProiect, Integer skillId, Integer target);
    boolean foundCodProiectExpleo(String value);
    boolean hasPrincipalProject(String codProiect);
}
