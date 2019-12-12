package com.expleo.webcm.entity.expleodb;

import javax.persistence.*;

@Entity(name = "History")
@Table(name = "history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_user")
    private UserExpleo user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_skill")
    private Skill skill;

    @Column(name="evaluare")
    private int evaluare;

    @Column(name="data_evaluare")
    private String date;

    public History() {
    }

    public History(UserExpleo user, Skill skill, int evaluare, String date) {
        this.user = user;
        this.skill = skill;
        this.evaluare = evaluare;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserExpleo getUser() {
        return user;
    }

    public void setUser(UserExpleo user) {
        this.user = user;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
