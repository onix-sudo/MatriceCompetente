package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.UserExpleo;

import javax.sql.DataSource;
import java.util.List;

public interface ProiectDAO {

    List<Proiect> findProjectByUser(UserExpleo user);

    void saveNewProject(Proiect proiect);

    List<Proiect> findManagerProjects(UserExpleo userExpleo);
}
