package com.expleo.webcm.entity.expleodb;

import com.expleo.webcm.helper.UniqueCodProiect;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @NotNull(message = "Numele trebuie completat")
    @NotEmpty(message = "Numele trebuie completat")
    @Column(name="Nume_proiect")
    private String numeProiect;

    @UniqueCodProiect
    @NotNull(message = "Emailul trebuie completat")
    @NotEmpty(message = "Numele trebuie completat")
    @Column(name="Cod")
    private String codProiect;

    @Column(name = "Manager_email")
    private String manager;

    @OneToMany(mappedBy = "proiect")
    private List<ProiectSkill> skills;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "user_proiect",
            joinColumns = { @JoinColumn(name = "ID_proiect")},
            inverseJoinColumns = { @JoinColumn(name = "ID_user")}
    )
    private List<UserExpleo> users;

    public Proiect() {

    }

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

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
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
                ", manager='" + manager + '\'' +
                '}';
    }
}
