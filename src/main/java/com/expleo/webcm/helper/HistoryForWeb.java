package com.expleo.webcm.helper;

import com.expleo.webcm.entity.expleodb.Skill;

import java.util.LinkedList;
import java.util.List;

public class HistoryForWeb {
    private Skill skill;
    private List<Integer> evaluations = new LinkedList<>();
    private List<String> date = new LinkedList<>();

    public HistoryForWeb(Skill skill) {
        this.skill = skill;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public List<Integer> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Integer> evaluations) {
        this.evaluations = evaluations;
    }

    public void addEvaluations(Integer evaluation) {
        this.evaluations.add(evaluation);
    }

    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }

    public void addDate(String date) {
        this.date.add(date);
    }
}
