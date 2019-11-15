package com.expleo.webcm.entity.expleodb;

import javax.persistence.*;

@Entity(name="ProiectSkill")
@Table(name = "proiect_skill")
//@Entity
//@Table(name = "expleodb", schema = "proiect_skill")
public class ProiectSkill {

    @EmbeddedId
    private ProiectSkillId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ID_Proiect")
    @JoinColumn(name="ID_Proiect")
    private Proiect proiect;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ID_skill")
    @JoinColumn(name = "ID_skill")
    // @MapsId("skillId")
    private Skill skill;

    @Column(name="Pondere")
    private int pondere;

    public ProiectSkill() {

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
