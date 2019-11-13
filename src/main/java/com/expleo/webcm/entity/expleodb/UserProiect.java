package com.expleo.webcm.entity.expleodb;

import com.expleo.webcm.dao.UserDAO;

import javax.persistence.*;

@Entity
@Table(name="user_proiect", schema="expleodb")
public class UserProiect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_user")
    private int idUser;

    @Column(name="ID_proiect")
    private int idProiect;


    public UserProiect() {

    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdProiect() {
        return idProiect;
    }

    public void setIdProiect(int idProiect) {
        this.idProiect = idProiect;
    }

    @Override
    public String toString() {
        return "UserProiect{" +
                "idUser=" + idUser +
                ", idProiect=" + idProiect +
                '}';
    }
}
