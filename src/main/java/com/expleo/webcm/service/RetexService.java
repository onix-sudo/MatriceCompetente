package com.expleo.webcm.service;

import com.expleo.webcm.entity.expleodb.Record;

import java.util.List;

public interface RetexService {
    List<Record> searchRecords(String searchTerms, String searchCategory);
}
