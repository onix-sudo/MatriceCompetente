package com.expleo.webcm.service;

import com.expleo.webcm.dao.HistoryDAO;
import com.expleo.webcm.entity.expleodb.History;
import com.expleo.webcm.entity.expleodb.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServicesImpl implements HistoryService {

    @Autowired
    private HistoryDAO historyDAO;

    @Override
    public List<History> getHistoryByUserId(int principalId) {
        return historyDAO.getHistoryByUserId(principalId);
    }
}
