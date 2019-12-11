package com.expleo.webcm.service;

import com.expleo.webcm.dao.SkillDAO;
import com.expleo.webcm.entity.expleodb.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillDAO skillDAO;


    @Override
    @Transactional("transactionExpleoDBManager")
    public List<Skill> getSkills() {
        return skillDAO.getSkills();
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public void saveSkill(Skill theSkill) {
        skillDAO.saveSkill(theSkill);
    }

    @Override
    @Transactional("transactionExpleoDBManager")
    public Skill getSkill(int theId) {
        return skillDAO.getSkill(theId);
    }

}
