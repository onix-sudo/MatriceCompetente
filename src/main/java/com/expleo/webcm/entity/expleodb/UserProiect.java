package com.expleo.webcm.entity.expleodb;

import com.expleo.webcm.dao.UserDAO;

import javax.persistence.*;

@Entity(name="UserProiect")
@Table(name="user_proiect", schema="expleodb")
public class UserProiect {
    @EmbeddedId
    private UserProiectId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_user", insertable = false, updatable = false)
    // @MapsId("userId")
    private UserExpleo user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_proiect", insertable = false, updatable = false)
    // @MapsId("skillId")
    private Proiect proiect;

    public UserProiect() {

    }

    public UserProiect(UserExpleo user, Proiect proiect) {
        this.user = user;
        this.proiect = proiect;
    }

    public UserProiectId getId() {
        return id;
    }

    public void setId(UserProiectId id) {
        this.id = id;
    }

    public UserExpleo getUser() {
        return user;
    }

    public void setUser(UserExpleo user) {
        this.user = user;
    }

    public Proiect getProiect() {
        return proiect;
    }

    public void setProiect(Proiect proiect) {
        this.proiect = proiect;
    }
}
