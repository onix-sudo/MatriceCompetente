package com.expleo.webcm.service;

import com.expleo.webcm.dao.RetexDAO;
import com.expleo.webcm.entity.expleodb.Record;
import com.expleo.webcm.entity.expleodb.Solution;
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

    @Override
    @Transactional("transactionExpleoDBManager")
    public Record getRecord(Integer recordId) {
        return retexDAO.getRecord(recordId);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public void saveOrUpdateRecord(Record record) {
        retexDAO.saveOrUpdateRecord(record);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public Solution getSolution(Integer solutionId) {
        return retexDAO.getSolution(solutionId);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public void saveOrUpdateSolution(Solution solution) {
        retexDAO.saveOrUpdateSolution(solution);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public List<Record> getLastTenRecords() {
        return retexDAO.getLastTenRecords();
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public List<Solution> getSolutions(Integer recordId) {
        return retexDAO.getSolutions(recordId);
    }
}
