package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.UserExpleo;

import javax.sql.DataSource;
import java.util.List;
import java.util.Set;

public interface ProiectDAO {

    List<Proiect> findProjectByUser(UserExpleo user);

    void saveNewProject(Proiect proiect);

    List<Proiect> findManagerProjects(UserExpleo userExpleo);

    Proiect findProjectByCodProiect(String codProiect);

    void addUserToProject(String codProiect, Integer userId);

    void removeUserFromProject(Integer IDcodProiect, Integer userId);

    void dropTheProject(String codProiect);

    List<Proiect> getFreeProjects();

    void addFreeProject(String codProiect, UserExpleo principal);

    void addSkillToProject(String codProiect, Integer skillId);

    void removeSkillFromProject(String codProiect, Integer skillId);
}
