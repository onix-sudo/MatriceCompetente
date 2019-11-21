package com.expleo.webcm.service;

import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;

import java.util.List;

public interface SearchService {
    List<UserExpleo> searchUser(String text);

    List<Skill> searchSkill(String cauta);

    List<UserExpleo> searchUsersNotInProject(String codProiect, String searchTerm);

    List<Skill> searchSkillsNotInProject(String codProiect, String searchTerm);
}
