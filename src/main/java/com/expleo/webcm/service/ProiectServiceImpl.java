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
    private ProiectDAO proiectDao;

    @Autowired
    private SkillDAO skillDAO;

    @Override
    @Transactional("transactionExpleoDBManager")
    public List<Proiect> findProjectByUser(UserExpleo user) {
        return proiectDao.findProjectByUser(user);
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

    @Override
    @Transactional("transactionExpleoDBManager")
    public List<Proiect> findManagerProjects(UserExpleo userExpleo) {
        return proiectDao.findManagerProjects(userExpleo);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public Proiect findProjectByCodProiect(String codProiect) {
        return proiectDao.findProjectByCodProiect(codProiect);
    }
}
