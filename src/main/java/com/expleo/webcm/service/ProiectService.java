package com.expleo.webcm.service;

import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.ProiectSkill;
import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.UserSkill;

import java.util.List;
import java.util.Set;

public interface ProiectService {

    List<Proiect> findProjectByUser(UserExpleo user);

    void saveNewProject(Proiect proiect);

    List<ProiectSkill> showSkillsforProject(int idProject);

    List<Proiect> findManagerProjects(String userExpleoPrincipal);

    List<UserSkill> showEvalForUserSkills(List<ProiectSkill> skills, UserExpleo userExpleo);

    Proiect findProjectByCodProiect(String codProiect);

    Proiect getProjectListsUsersSkills(String codProiect, List<UserExpleo> users, List<ProiectSkill> skills);

    void addUserToProject(String codProiect, Integer userId);

    void removeUserFromProject(String codProiect, Integer userId);

    void dropTheProject(String codProiect);

    List<Proiect> getFreeProjects();

    void addFreeProject(String codProiect, String principal);

    void addSkillToProject(String codProiect, Integer skillId);

    void removeSkillFromProject(String codProiect, Integer skillId);

    List<ProiectSkill> findProjectSkillsByCodProiect(String codProiect);

    void setPondere(String codProiect, Integer skillId, Integer pondere);

    boolean foundCodProiectExpleo(String value);

    boolean hasPrincipalProject(String codProiect);
}
