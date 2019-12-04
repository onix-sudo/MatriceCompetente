package com.expleo.webcm.entity.expleodb;

import javax.persistence.*;
import java.util.Objects;

@Entity(name="ProiectSkill")
@Table(name = "proiect_skill")
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

    @Column(name="Target")
    private int target;

    public ProiectSkill() {

    }

    public ProiectSkill(Proiect proiect, Skill skill) {
        this.proiect = proiect;
        this.skill = skill;
        this.id = new ProiectSkillId(proiect.getProiectId(), skill.getIdSkill());
        this.pondere= 1;
        this.target= 1;

        proiect.getSkills().add(this);
        skill.getProiecte().add(this);
    }

    public ProiectSkill(Proiect proiect, Skill skill, int pondere, int target) {
        this.id = new ProiectSkillId(proiect.getProiectId(), skill.getIdSkill());
        this.proiect = proiect;
        this.skill = skill;
        this.pondere = pondere;
        this.target= target;

        proiect.getSkills().add(this);
        skill.getProiecte().add(this);
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

    public int getTarget() { return target; }

    public void setTarget(int target) { this.target = target; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProiectSkill that = (ProiectSkill) o;
        return pondere == that.pondere &&
                id.equals(that.id) &&
                proiect.equals(that.proiect) &&
                skill.equals(that.skill);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, proiect, skill, pondere);
    }
}
