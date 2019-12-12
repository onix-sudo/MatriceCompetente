package com.expleo.webcm.helper;

import com.expleo.webcm.entity.expleodb.Skill;

public class HistoryCluster {

    private String date;
    private Skill skill;
    private int evaluare;


    public HistoryCluster() {
    }

    public HistoryCluster(String date, Skill skill, int evaluare) {
        this.date = date;
        this.skill = skill;
        this.evaluare = evaluare;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public int getEvaluare() {
        return evaluare;
    }

    public void setEvaluare(int evaluare) {
        this.evaluare = evaluare;
    }

    @Override
    public String toString() {
        return "HistoryCluster{" +
                "date='" + date + '\'' +
                ", skill=" + skill +
                ", evaluare=" + evaluare +
                '}';
    }
}
