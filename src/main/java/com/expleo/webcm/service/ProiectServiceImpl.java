package com.expleo.webcm.service;

import com.expleo.webcm.dao.ProiectDAO;
import com.expleo.webcm.dao.SkillDAO;
import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.ProiectSkill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.UserSkill;
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
    public void saveNewProject(Proiect proiect) {
        proiectDao.saveNewProject(proiect);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public List<Proiect> getFreeProjects() {
        return proiectDao.getFreeProjects();
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public List<Proiect> findManagerProjects(String userExpleo) {
        return proiectDao.findManagerProjects(userExpleo);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public List<Proiect> findPrincipalProjects() {
        return proiectDao.findPrincipalProjects();
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public Proiect getProjectListsUsersSkills(String codProiect, List<UserExpleo> users, List<ProiectSkill> skills) {
       return proiectDao.getProjectListsUsersSkills(codProiect, users, skills);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public void findProjectUsersAndSkills(String codProiect, List<UserExpleo> foundUsers, List<ProiectSkill> foundSkills, List<UserSkill> foundUserSkills) {
        proiectDao.findProjectUsersAndSkills(codProiect, foundUsers, foundSkills, foundUserSkills);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public void addUserToProject(String codProiect, Integer userId) {
        proiectDao.addUserToProject(codProiect, userId);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public void removeUserFromProject(String codProiect, Integer userId) {
        proiectDao.removeUserFromProject(codProiect, userId);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public void addFreeProject(String codProiect, String principal) {
        proiectDao.addFreeProject(codProiect, principal);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public void dropTheProject(String codProiect) {
        proiectDao.dropTheProject(codProiect);
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

    @Override
    @Transactional("transactionExpleoDBManager")
    public void setPondere(String codProiect, Integer skillId, Integer pondere) {
        proiectDao.setPondere(codProiect, skillId, pondere);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public void setTarget(String codProiect, Integer skillId, Integer target) {
        proiectDao.setTarget(codProiect, skillId, target);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public boolean foundCodProiectExpleo(String value) {
        return proiectDao.foundCodProiectExpleo(value);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public boolean hasPrincipalProject(String codProiect) {
        return proiectDao.hasPrincipalProject(codProiect);
    }
}
