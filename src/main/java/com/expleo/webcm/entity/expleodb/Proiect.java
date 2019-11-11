package com.expleo.webcm.entity.expleodb;

import javax.persistence.*;

@Entity
@Table(name = "proiect", schema = "expleodb")
public class Proiect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_proiect")
    private int proiectId;

    @Column(name="Nume_proiect")
    private String numeProiect;

    @Column(name="Cod")
    private String codProiect;

    public Proiect() {

    }

    public Proiect(int proiectId, String numeProiect, String codProiect) {
        this.proiectId = proiectId;
        this.numeProiect = numeProiect;
        this.codProiect = codProiect;
    }

    public int getProiectId() {
        return proiectId;
    }

    public void setProiectId(int proiectId) {
        this.proiectId = proiectId;
    }

    public String getNumeProiect() {
        return numeProiect;
    }

    public void setNumeProiect(String numeProiect) {
        this.numeProiect = numeProiect;
    }

    public String getCodProiect() {
        return codProiect;
    }

    public void setCodProiect(String codProiect) {
        this.codProiect = codProiect;
    }

    @Override
    public String toString() {
        return "Proiect{" +
                "proiectId=" + proiectId +
                ", numeProiect='" + numeProiect + '\'' +
                ", codProiect='" + codProiect + '\'' +
                '}';
    }
}
