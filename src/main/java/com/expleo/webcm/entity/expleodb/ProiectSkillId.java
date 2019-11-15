package com.expleo.webcm.entity.expleodb;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ProiectSkillId implements Serializable {

    @Column(name="ID_proiect")
    private Integer proiectId;

    @Column(name="ID_skill")
    private Integer skillId;

    public ProiectSkillId() {

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
}
