package com.expleo.webcm.entity.expleodb;

import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "proiect", schema = "expleodb")
public class Proiect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_Proiect")
    private int proiectId;

    @Column(name="Nume_proiect")
    private String numeProiect;

    @Column(name="Cod")
    private String codProiect;

    @Column(name = "Manager_nrMatricol")
    private Integer manager;

//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
//                    CascadeType.DETACH, CascadeType.REFRESH})
//    @JoinTable(
//            name = "proiect_skill",
//            joinColumns = { @JoinColumn(name = "ID_Proiect")},
//            inverseJoinColumns = { @JoinColumn(name = "ID_skill")}
//    )
    @OneToMany(mappedBy = "proiect")
    private List<ProiectSkill> skills;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "user_proiect",
            joinColumns = { @JoinColumn(name = "ID_proiect")},
            inverseJoinColumns = { @JoinColumn(name = "ID_user")}
    )
    private List<UserExpleo> users;

    public Proiect() {

    }

//    public Proiect(int proiectId, String numeProiect, String codProiect) {
//        this.proiectId = proiectId;
//        this.numeProiect = numeProiect;
//        this.codProiect = codProiect;
//    }

    public int getProiectId() {
        return proiectId;
    }

    public void setProiectId(int proiectId) {
        this.proiectId = proiectId;
    }

    public String getNumeProiect() {
        return numeProiect;
    }

    public void setNumeProiect(String numeProiect) {
        this.numeProiect = numeProiect;
    }

    public String getCodProiect() {
        return codProiect;
    }

    public void setCodProiect(String codProiect) {
        this.codProiect = codProiect;
    }

    public Integer getManager() {
        return manager;
    }

    public void setManager(Integer manager) {
        this.manager = manager;
    }

    public List<ProiectSkill> getSkills() {
        return skills;
    }

    public void setSkills(List<ProiectSkill> skills) {
        this.skills = skills;
    }

    public List<UserExpleo> getUsers() {
        return users;
    }

    public void setUsers(List<UserExpleo> users) {
        this.users = users;
    }

    public void addUsers(UserExpleo userExpleo){

        if(users == null){
            users = new ArrayList<>();
        }
        users.add(userExpleo);
    }

    public void addSkill(ProiectSkill proiectSkill){

        if(skills == null){
            skills = new ArrayList<>();
        }
        skills.add(proiectSkill);
    }

    @Override
    public String toString() {
        return "Proiect{" +
                "proiectId=" + proiectId +
                ", numeProiect='" + numeProiect + '\'' +
                ", codProiect='" + codProiect + '\'' +
                '}';
    }
}
