package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.Record;
import java.util.List;

public interface RetexDAO {
    List<Record> searchRecords(String searchTerms, String searchCategory);
}
