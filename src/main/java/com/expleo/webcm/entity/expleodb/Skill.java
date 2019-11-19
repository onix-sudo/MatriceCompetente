package com.expleo.webcm.entity.expleodb;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Parameter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


@AnalyzerDef(name = "ngramForSkill",
        tokenizer = @TokenizerDef(factory = WhitespaceTokenizerFactory.class),
        filters = {
//                @TokenFilterDef(factory = StandardFilterFactory.class),
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
//                @TokenFilterDef(factory = ASCIIFoldingFilterFactory.class)
//                @TokenFilterDef(factory = StopFilterFactory.class),
                @TokenFilterDef(factory = EdgeNGramFilterFactory.class,
                        params = {
                                @org.hibernate.search.annotations.Parameter(name = "minGramSize", value = "3"),
                                @Parameter(name = "maxGramSize", value = "6") } )
        }
)
@Entity
@Table(name = "skill", schema = "expleodb")
@Indexed
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_skill")
    private int idSkill;

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES, analyzer = @Analyzer(definition = "ngramForSkill"))
    @Column(name="Nume_skill")
    private String numeSkill;

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES, analyzer = @Analyzer(definition = "ngramForSkill"))
    @Column(name="Categorie")
    private String categorie;

    @OneToMany(mappedBy = "skill")
    private List<ProiectSkill> proiecte;

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

    public List<ProiectSkill> getProiecte() {
        return proiecte;
    }

    public void setProiecte(List<ProiectSkill> proiecte) {
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

    public void addProiect(ProiectSkill proiectSkill){
        if(proiecte == null){
            proiecte = new ArrayList<>();
        }
        proiecte.add(proiectSkill);
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
