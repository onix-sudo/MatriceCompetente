package com.expleo.webcm.entity.expleodb;

import javax.persistence.*;

@Entity(name = "Solution")
@Table(name = "solution")
public class Solution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_solution")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_record")
    private Record record;

    @Column(name="Solutie")
    private String solutie;

    @Column(name="Autor")
    private String autor;

    @Column(name="Data")
    private String date;

    public Solution() {
    }

    public Solution(Record record, String solutie, String autor, String date) {
        this.record = record;
        this.solutie = solutie;
        this.autor = autor;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public String getSolutie() {
        return solutie;
    }

    public void setSolutie(String solutie) {
        this.solutie = solutie;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
