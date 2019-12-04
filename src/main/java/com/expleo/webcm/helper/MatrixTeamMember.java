package com.expleo.webcm.helper;

import com.expleo.webcm.entity.expleodb.ProiectSkill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.UserSkill;

import java.util.LinkedList;
import java.util.List;

public class MatrixTeamMember {
    private UserExpleo user;
    private List<UserSkill> skills = new LinkedList<>();
    private List<ProiectSkill> proiectSkills = new LinkedList<>();
    private int score;

    public MatrixTeamMember() {
    }

    public MatrixTeamMember(UserExpleo user, UserSkill skills, ProiectSkill proiectSkills) {
        this.user = user;
        this.skills.add(skills);
        this.proiectSkills.add(proiectSkills);
        makeScore();
    }

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

    public void makeScore(){
        int sum = 0;

//        for(ProiectSkill tempProiectSkill: proiectSkills){
//            for(UserSkill tempUserSkill: skills){
//                if(tempProiectSkill.getSkill().getIdSkill()==tempUserSkill.getSkill().getIdSkill()){
//                    System.out.println("///////////////////////////////////");
//                    System.out.println(tempUserSkill.getUser().getPrenume());
//                    System.out.println(tempProiectSkill.getSkill().getIdSkill()+"--" +tempProiectSkill.getSkill().getNumeSkill());
//                    System.out.println(tempUserSkill.getSkill().getIdSkill()+"--" +tempUserSkill.getSkill().getNumeSkill());
//                    sum += tempUserSkill.getEvaluation()*tempProiectSkill.getPondere();
//                    System.out.println(tempUserSkill.getEvaluation() + "*" + tempProiectSkill.getPondere());
//                    System.out.println(sum);
//                    System.out.println("///////////////////////////////////");
//                }
//            }
//        }
        for(UserSkill userSkill : skills){
            for(ProiectSkill proiectSkill : proiectSkills){
                if(userSkill.getSkill().equals(proiectSkill.getSkill())){
                    System.out.println("///////////////////////////////////");
                    sum += userSkill.getEvaluation()* proiectSkill.getPondere();
                    System.out.println(sum);
                    System.out.println("///////////////////////////////////");
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

    @Override
    public String toString() {
        return "MatrixTeamMember{" +
                "user=" + user +
                ", skills=" + skills +
                ", proiectSkills=" + proiectSkills +
                '}';
    }
}
