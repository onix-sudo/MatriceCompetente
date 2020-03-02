package com.expleo.webcm.service;

import com.expleo.webcm.entity.expleodb.Solution;

import java.util.List;

public interface ChatbotService {
    List<Solution> searchSolutions(String searchTerm);
}
