package com.expleo.webcm.service;

import com.expleo.webcm.dao.ProiectDAO;
import com.expleo.webcm.dao.SkillDAO;
import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProiectServiceImpl implements ProiectService {

    @Autowired
    ProiectDAO proiectDao;

    @Autowired
    SkillDAO skillDAO;

    @Override
    @Transactional("transactionExpleoDBManager")
    public List<Proiect> findProjectByEmail(UserExpleo user) {
        return proiectDao.findProjectByEmail(user);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public void saveNewProject(Proiect proiect) {
        proiectDao.saveNewProject(proiect);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public List<Skill> showSkillsforProject(int idProject) {
        return skillDAO.showSkillsforProject(idProject);
    }
}
