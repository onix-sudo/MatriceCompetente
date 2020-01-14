package com.expleo.webcm.entity.expleodb;

import javax.persistence.*;
import java.util.List;

@Entity(name="Record")
@Table(name = "record")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_record")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_user")
    private UserExpleo userExpleo;

    @Column(name = "Categorie")
    private String categorie;

    @Column(name = "Titlu")
    private String titlu;

    @Column(name="Descriere")
    private String descriere;

    @Column(name = "Data")
    private String date;

    @OneToMany(mappedBy = "record")
    private List<Solution> solutions;

    public Record() {
    }

    public Record(UserExpleo userExpleo, String categorie, String titlu, String descriere, String date) {
        this.userExpleo = userExpleo;
        this.categorie = categorie;
        this.titlu = titlu;
        this.descriere = descriere;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserExpleo getUserExpleo() {
        return userExpleo;
    }

    public void setUserExpleo(UserExpleo userExpleo) {
        this.userExpleo = userExpleo;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<Solution> solutions) {
        this.solutions = solutions;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", userExpleo=" + userExpleo +
                ", categorie='" + categorie + '\'' +
                ", titlu='" + titlu + '\'' +
                ", descriere='" + descriere + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
