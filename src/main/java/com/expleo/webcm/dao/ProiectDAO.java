package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.*;

import javax.sql.DataSource;
import java.util.List;
import java.util.Set;

public interface ProiectDAO {

    List<Proiect> findProjectByUser(UserExpleo user);

    void saveNewProject(Proiect proiect);

    List<Proiect> findManagerProjects(UserExpleo userExpleo);

    Proiect findProjectByCodProiect(String codProiect);

    void getProjectListsUsersSkills(String codProiect, List<UserExpleo> users, List<ProiectSkill> skills);

    void addUserToProject(String codProiect, Integer userId);

    void removeUserFromProject(String codProiect, Integer userId);

    void dropTheProject(String codProiect);

    List<Proiect> getFreeProjects();

    void addFreeProject(String codProiect, UserExpleo principal);

    void addSkillToProject(String codProiect, Integer skillId);

    void removeSkillFromProject(String codProiect, Integer skillId);

    List<ProiectSkill> findProjectSkillsByCodProiect(String codProiect);

    void setPondere(String codProiect, Integer skillId, Integer pondere);

    boolean foundCodProiectExpleo(String value);

    boolean hasPrincipalProject(String codProiect);
}
