package com.expleo.webcm.entity.expleodb;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ProiectSkillId implements Serializable {
    @Column(name="ID_proiect")
    private int proiectId;

    @Column(name="ID_skill")
    private int skillId;

    public ProiectSkillId() {

    }

    public int getProiectId() {
        return proiectId;
    }

    public void setProiectId(int proiectId) {
        this.proiectId = proiectId;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }


}
