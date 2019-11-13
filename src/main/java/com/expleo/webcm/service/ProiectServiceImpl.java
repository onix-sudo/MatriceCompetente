package com.expleo.webcm.service;

import com.expleo.webcm.dao.ProiectDao;
import com.expleo.webcm.entity.expleodb.Proiect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProiectServiceImpl implements ProiectService {

    @Autowired
    ProiectDao proiectDao;

    @Override
    public void saveNewProject(Proiect proiect) {
        proiectDao.saveNewProject(proiect);
    }
}
