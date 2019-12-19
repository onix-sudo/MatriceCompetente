package com.expleo.webcm.helper;

import com.expleo.webcm.entity.expleodb.ProiectSkill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.UserSkill;

import java.util.LinkedList;
import java.util.List;

/**
 * A helper class which is allows to make a proper list for MatrixTeam
 * */

public class MatrixTeamMember {
    private String name;
    private UserExpleo user;
    private List<UserSkill> skills = new LinkedList<>();
    private List<ProiectSkill> proiectSkills = new LinkedList<>();
    private int score;

    public UserExpleo getUser() {
        return user;
    }

    public void setUser(UserExpleo user) {
        this.user = user;
    }

    public List<UserSkill> getSkills() {
        return skills;
    }

    public void setSkills(List<UserSkill> skills) {
        this.skills = skills;
    }

    public List<ProiectSkill> getProiectSkills() {
        return proiectSkills;
    }

    public void setProiectSkills(List<ProiectSkill> proiectSkills) {
        this.proiectSkills = proiectSkills;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //calculating the score
    public void makeScore(){
        int sum = 0;

        for(UserSkill userSkill : skills){
            for(ProiectSkill proiectSkill : proiectSkills){
                if(userSkill.getSkill().equals(proiectSkill.getSkill())){
                    sum += userSkill.getEvaluation()* proiectSkill.getPondere();
                    break;
                }
            }
        }

        this.score = sum;
    }

    public void addUserSkill(UserSkill temp){
        skills.add(temp);
    }

    public void addProiectSkill(ProiectSkill temp){
        proiectSkills.add(temp);
    }

    public void makeName(){
        this.name = user.getNume()+ " " + user.getPrenume();
    }

    @Override
    public String toString() {
        return "MatrixTeamMember{" +
                "user=" + user +
                ", skills=" + skills +
                ", proiectSkills=" + proiectSkills +
                '}';
    }
}
