package com.expleo.webcm.entity.expleodb;

import javax.persistence.*;

@Entity(name="ProiectSkill")
@Table(name = "proiect_skill")
public class ProiectSkill {
    @EmbeddedId
    private ProiectSkillId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_Proiect", insertable = false, updatable = false)
    private Proiect proiect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_skill", insertable = false, updatable = false)
    // @MapsId("skillId")
    private Skill skill;

    @Column(name="Pondere")
    private int pondere;

    public ProiectSkill() {

    }

    public ProiectSkill(Proiect proiect, Skill skill, int pondere) {
        this.proiect = proiect;
        this.skill = skill;
        this.pondere = pondere;
    }

    public ProiectSkillId getId() {
        return id;
    }

    public void setId(ProiectSkillId id) {
        this.id = id;
    }

    public Proiect getProiect() {
        return proiect;
    }

    public void setProiect(Proiect proiect) {
        this.proiect = proiect;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public int getPondere() {
        return pondere;
    }

    public void setPondere(int pondere) {
        this.pondere = pondere;
    }
}
