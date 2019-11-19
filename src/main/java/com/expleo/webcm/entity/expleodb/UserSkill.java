package com.expleo.webcm.entity.expleodb;

import javax.persistence.*;
import java.util.Objects;

@Entity(name="UserSkill")
@Table(name = "user_skill")
public class UserSkill {
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UserSkillId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_user")//, insertable = false, updatable = false)
    @MapsId("userId")
    private UserExpleo user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_skill")//, insertable = false, updatable = false)
    @MapsId("skillId")
    private Skill skill;

    @Column(name="Evaluare")
    private int evaluation;

    public UserSkill() {
    }

    public UserSkill(Skill skill, UserExpleo user) {
        this.user = user;
        this.skill = skill;
        this.id = new UserSkillId(user.getId(), skill.getIdSkill());

        user.getUserSkills().add(this);
        skill.getUserSkills().add(this);
    }

    public UserSkillId getId() {
        return id;
    }

    public void setId(UserSkillId id) {
        this.id = id;
    }

    public UserExpleo getUser() {
        return user;
    }

    public void setUser(UserExpleo user) {
        this.user = user;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSkill userSkill = (UserSkill) o;
        return Objects.equals(user, userSkill.user) &&
                Objects.equals(skill, userSkill.skill);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, skill);
    }

    @Override
    public String toString() {
        return "UserSkill{" +
                "id=" + id +
                ", user=" + user +
                ", skill=" + skill +
                ", evaluation=" + evaluation +
                '}';
    }
}
