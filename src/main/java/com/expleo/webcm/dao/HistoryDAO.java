package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.History;
import com.expleo.webcm.entity.expleodb.Skill;

import java.util.List;

public interface HistoryDAO {

    List<History> getHistoryByUserId(int id);
}
