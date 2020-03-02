package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.Solution;

import java.util.List;

public interface ChatbotDAO {
    List<Solution> searchSolutions(String searchTerm);
}
