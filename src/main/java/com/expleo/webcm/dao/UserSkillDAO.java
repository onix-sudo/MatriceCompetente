package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.UserSkill;

import java.util.List;

public interface UserSkillDAO {

    void saveUserSkill(int idUser, int idSkill);
    void updateUserSkill(int idUser, int idSkill, int eval);
    void removeUserSkill(int idUserExpleo, int idSkill);
    void getAdditionalAndProjectSkill(int userId, List<UserSkill> userAdditionalSkills, List<UserSkill> projectSkills);
    List<UserSkill> getUserSkillByProjectSkills(Integer projectId);
}
