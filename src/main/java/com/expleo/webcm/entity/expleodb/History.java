package com.expleo.webcm.entity.expleodb;

import javax.persistence.*;

@Entity(name = "History")
@Table(name = "history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="id_user_skill")
    private Integer idUserSkill;

    @Column(name="evaluare")
    private int evaluare;

    @Column(name="data_evaluare")
    private String date;

    public History() {
    }

    public History(Integer idUserSkill, int evaluare, String date) {
        this.idUserSkill = idUserSkill;
        this.evaluare = evaluare;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUserSkill() {
        return idUserSkill;
    }

    public void setIdUserSkill(Integer idUserSkill) {
        this.idUserSkill = idUserSkill;
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
