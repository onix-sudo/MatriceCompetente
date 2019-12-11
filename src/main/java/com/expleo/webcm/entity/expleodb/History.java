package com.expleo.webcm.entity.expleodb;

import javax.persistence.*;

@Entity(name = "History")
@Table(name = "history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="id_user")
    private Integer idUser;

    @Column(name="id_skill")
    private Integer idSkill;

    @Column(name="evaluare")
    private int evaluare;

    @Column(name="data_evaluare")
    private String date;

    public History() {
    }

    public History(Integer idUser, Integer idSkill, int evaluare, String date) {
        this.idUser = idUser;
        this.idSkill = idSkill;
        this.evaluare = evaluare;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdSkill() {
        return idSkill;
    }

    public void setIdSkill(Integer idSkill) {
        this.idSkill = idSkill;
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
