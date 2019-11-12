package com.expleo.webcm.entity.expleodb;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserSkillId implements Serializable {
    @Column(name="ID_user")
    private int userId;

    @Column(name="ID_skill")
    private int skillId;

    public UserSkillId() {
    }

    public UserSkillId(int userId, int skillId) {
        this.userId = userId;
        this.skillId = skillId;
    }

    public int getUserId() {
        return userId;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSkillId that = (UserSkillId) o;
        return userId == that.userId &&
                skillId == that.skillId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, skillId);
    }
}
