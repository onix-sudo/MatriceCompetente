package com.expleo.webcm.dao;

import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;

import java.util.List;

public interface SearchDAO {
    public List<UserExpleo> searchUser(String text);
    public List<Skill> searchSkill(String text);
}
