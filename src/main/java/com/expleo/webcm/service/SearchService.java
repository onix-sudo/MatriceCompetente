package com.expleo.webcm.service;

import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;

import java.util.List;

public interface SearchService {
    public List<UserExpleo> searchUser(String text);
    public List<Skill> searchSkill(String text);
}
