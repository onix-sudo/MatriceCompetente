package com.expleo.webcm.entity.expleodb;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "skill", schema = "expleodb")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_skill")
    private int idSkill;

    @Column(name="Nume_skill")
    private String numeSkill;

    @Column(name="Categorie")
    private String categorie;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH} )

    @JoinTable(
            name = "proiect_skill",
            joinColumns = { @JoinColumn(name = "ID_Proiect")},
            inverseJoinColumns = { @JoinColumn(name = "ID_skill")}
    )
    private List<Proiect> proiecte = new ArrayList<>();

    @ManyToMany(mappedBy = "skillsRequired")
    Set<UserExpleo> users;

    @OneToMany(mappedBy = "skill")
    Set<UserSkill> userSkills;

    public Skill() {

    }

    public int getIdSkill() {
        return idSkill;
    }

    public String getNumeSkill() {
        return numeSkill;
    }

    public void setNumeSkill(String numeSkill) {
        this.numeSkill = numeSkill;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setIdSkill(int idSkill) {
        this.idSkill = idSkill;
    }

    public List<Proiect> getProiecte() {
        return proiecte;
    }

    public void setProiecte(List<Proiect> proiecte) {
        this.proiecte = proiecte;
    }

    public Set<UserExpleo> getUsers() {
        return users;
    }

    public void setUsers(Set<UserExpleo> users) {
        this.users = users;
    }

    public Set<UserSkill> getUserSkills() {
        return userSkills;
    }

    public void setUserSkills(Set<UserSkill> userSkills) {
        this.userSkills = userSkills;
    }
}
