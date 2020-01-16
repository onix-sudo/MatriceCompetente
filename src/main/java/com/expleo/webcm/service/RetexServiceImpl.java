package com.expleo.webcm.service;

import com.expleo.webcm.dao.RetexDAO;
import com.expleo.webcm.entity.expleodb.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class RetexServiceImpl implements RetexService{

    @Autowired
    private RetexDAO retexDAO;

    @Override
    @Transactional("transactionExpleoDBManager")
    public List<Record> searchRecords(String searchTerms, String searchCategory) {
        return retexDAO.searchRecords(searchTerms, searchCategory.toLowerCase());
    }
}
