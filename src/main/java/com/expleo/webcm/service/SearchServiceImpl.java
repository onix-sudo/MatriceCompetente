package com.expleo.webcm.service;

import com.expleo.webcm.dao.SearchDAO;
import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.UserSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDAO searchDAO;

    @Override
    @Transactional("transactionExpleoDBManager")
    public List<UserExpleo> searchUser(String text) {
        return searchDAO.searchUser(text);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public List<Skill> searchSkill(String text) {
        return searchDAO.searchSkill(text);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public List<UserExpleo> searchUsersNotInProject(String codProiect, String searchTerm) {
        return searchDAO.searchUsersNotInProject(codProiect, searchTerm);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public List<Skill> searchSkillsNotInProject(String codProiect, String searchTerm) {
        return searchDAO.searchSkillsNotInProject(codProiect, searchTerm);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public List<UserSkill> searchSkillWithEvaluation(String text, int eval) {
        return searchDAO.searchSkillWithEvaluation(text,eval);
    }
}
