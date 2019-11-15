package com.expleo.webcm.entity.expleodb;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    @OneToMany(mappedBy = "skill",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSkill> users = new ArrayList<>();

    public List<UserSkill> getUsers() {
        return users;
    }

    public void setUsers(List<UserSkill> users) {
        this.users = users;
    }

    public void addUser(UserExpleo user){
        UserSkill userSkill = new UserSkill(this,user);
        users.add(userSkill);
        user.getSkills().add(userSkill);

    }

    public void removeUser(UserExpleo user){
        for (Iterator<UserSkill> iterator = users.iterator(); iterator.hasNext(); ){
            UserSkill userSkill = iterator.next();

            if (userSkill.getUser().equals(user) && userSkill.getSkill().equals(this)){
                iterator.remove();
                userSkill.getSkill().getUsers().remove(userSkill);
                userSkill.setUser(null);
                userSkill.setSkill(null);
            }
        }
    }

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

    @Override
    public String toString() {
        return "Skill{" +
                "idSkill=" + idSkill +
                ", numeSkill='" + numeSkill + '\'' +
                ", categorie='" + categorie + '\'' +
                '}';
    }
}
