package com.expleo.webcm.service;

import com.expleo.webcm.entity.expleodb.History;
import com.expleo.webcm.entity.expleodb.Skill;

import java.util.List;

public interface HistoryService {

    List<History> getHistoryByUserId(int principalId);

}
