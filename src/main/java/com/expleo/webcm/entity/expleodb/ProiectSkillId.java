package com.expleo.webcm.entity.expleodb;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProiectSkillId implements Serializable {

    @Column(name="ID_proiect")
    private Integer proiectId;

    @Column(name="ID_skill")
    private Integer skillId;

    public ProiectSkillId() {

    }

    public ProiectSkillId(Integer proiectId, Integer skillId) {
        this.proiectId = proiectId;
        this.skillId = skillId;
    }

    public Integer getProiectId() {
        return proiectId;
    }

    public void setProiectId(Integer proiectId) {
        this.proiectId = proiectId;
    }

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProiectSkillId that = (ProiectSkillId) o;
        return proiectId.equals(that.proiectId) &&
                skillId.equals(that.skillId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(proiectId, skillId);
    }
}
