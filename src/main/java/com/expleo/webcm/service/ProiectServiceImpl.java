package com.expleo.webcm.service;

import com.expleo.webcm.dao.ProiectDAO;
import com.expleo.webcm.dao.SkillDAO;
import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.ProiectSkill;
import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.UserSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

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
    public List<ProiectSkill> showSkillsforProject(int idProject) {
        return skillDAO.showSkillsforProject(idProject);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public List<Proiect> findManagerProjects(UserExpleo userExpleo) {
        return proiectDao.findManagerProjects(userExpleo);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public List<UserSkill> showEvalForUserSkills(List<ProiectSkill> skills, UserExpleo userExpleo) {
        return skillDAO.showEvalForUserSkills(skills, userExpleo);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public Proiect findProjectByCodProiect(String codProiect) {
        return proiectDao.findProjectByCodProiect(codProiect);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public void addUserToProject(String codProiect, Integer userId) {
        proiectDao.addUserToProject(codProiect, userId);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public void removeUserFromProject(Integer IDcodProiect, Integer userId) {
        proiectDao.removeUserFromProject(IDcodProiect, userId);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public void dropTheProject(String codProiect) {
        proiectDao.dropTheProject(codProiect);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public List<Proiect> getFreeProjects() {
        return proiectDao.getFreeProjects();
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public void addFreeProject(String codProiect, UserExpleo principal) {
        proiectDao.addFreeProject(codProiect, principal);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public void addSkillToProject(String codProiect, Integer skillId) {
        proiectDao.addSkillToProject(codProiect, skillId);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public void removeSkillFromProject(String codProiect, Integer skillId) {
        proiectDao.removeSkillFromProject(codProiect, skillId);
    }
}
