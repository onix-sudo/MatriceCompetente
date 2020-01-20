package com.expleo.webcm.service;

import com.expleo.webcm.entity.expleodb.Record;
import com.expleo.webcm.entity.expleodb.Solution;

import java.util.List;

public interface RetexService {
    List<Record> searchRecords(String searchTerms, String searchCategory);
    Record getRecord(Integer recordId);
    void saveOrUpdateRecord(Record record);
    Solution getSolution(Integer solutionId);
    void saveOrUpdateSolution(Solution solution);
    List<Record> getLastTenRecords();
    List<Solution> getLastTenSolutions();
    List<Solution> getSolutions(Integer recordId);
}
