package com.expleo.webcm.service;


import com.expleo.webcm.dao.ChatbotDAO;
import com.expleo.webcm.entity.expleodb.Solution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatbotServiceImpl implements ChatbotService {
    @Autowired
    private ChatbotDAO chatbotDAO;

    @Override
    @Transactional("transactionExpleoDBManager")
    public List<Solution> searchSolutions(String searchTerm) {
        return chatbotDAO.searchSolutions(searchTerm);
    }
}
