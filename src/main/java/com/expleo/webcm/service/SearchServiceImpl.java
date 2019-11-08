package com.expleo.webcm.service;

import com.expleo.webcm.dao.SearchDAO;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    SearchDAO searchDAO;

    @Override
    @Transactional("transactionExpleoDBManager")
    public List<UserExpleo> searchUser(String text) {
        return searchDAO.searchUser(text);
    }
}
