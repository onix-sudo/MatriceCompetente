package com.expleo.webcm.entity.expleodb;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "proiect_skill",
            joinColumns = { @JoinColumn(name = "ID_Proiect")},
            inverseJoinColumns = { @JoinColumn(name = "ID_skill")}
    )
    private List<Skill> skills = new ArrayList<>();

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

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
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
