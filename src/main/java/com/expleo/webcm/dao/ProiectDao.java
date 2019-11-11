package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.Proiect;

import javax.sql.DataSource;
import java.util.List;

public interface ProiectDao {

    public List<Proiect> findUserIdByEmail(String username);
}
