package com.expleo.webcm.entity.expleodb;

import com.expleo.webcm.helper.UniqueEmail;
import com.expleo.webcm.helper.UniqueNumarMatricol;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.apache.lucene.analysis.ngram.*;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.bridge.builtin.IntegerBridge;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@AnalyzerDef(name = "ngram",
        tokenizer = @TokenizerDef(factory = WhitespaceTokenizerFactory.class),
        filters = {
//                @TokenFilterDef(factory = StandardFilterFactory.class),
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                @TokenFilterDef(factory = ASCIIFoldingFilterFactory.class),
//                @TokenFilterDef(factory = StopFilterFactory.class),
                @TokenFilterDef(factory = EdgeNGramFilterFactory.class,
                        params = {
                                @Parameter(name = "minGramSize", value = "3"),
                                @Parameter(name = "maxGramSize", value = "10") } )
        }
)
@Indexed
@Entity
@Table(name = "user", schema = "expleodb",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "Numar_matricol"),
            @UniqueConstraint(columnNames = "Email")})
public class UserExpleo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_user")
    private int id;

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES, analyzer = @Analyzer(definition = "ngram"))
    @NotEmpty(message = "Campul trebuie completat")
    @Column(name="Nume_user")
    private String nume;

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES, analyzer = @Analyzer(definition = "ngram"))
    @NotEmpty(message = "Campul trebuie completat")
    @Column(name="Prenume_user")
    private String prenume;

    @NotNull(message = "Campul trebuie completat")
    @Min(value = 1000, message = "Numar format din 4 cifre")
    @Max(value = 9999, message = "Numar format din 4 cifre")
    @UniqueNumarMatricol
    @Column(name="Numar_matricol")
    @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES)
    @FieldBridge(impl = IntegerBridge.class)
    private Integer numarMatricol;

    @NotEmpty(message = "Campul trebuie completat")
    @Email(message = "Nu este valid")
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
    @UniqueEmail
    @Column(name="Email")
    private String email;

    @NotNull(message = "Campul trebuie completat")
    @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "Format invalid.(aaaa-LL-zz)")
    @Column(name="Data_angajare")
    private String dataAngajare;

    @Column(name="Functie")
    private String functie;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "user_proiect",
            joinColumns = { @JoinColumn(name = "ID_user")},
            inverseJoinColumns = { @JoinColumn(name = "ID_proiect")}
    )
    private List<Proiect> proiecte;

    @ManyToMany
    @JoinTable(
            name = "user_skill",
            joinColumns = @JoinColumn(name = "ID_user"),
            inverseJoinColumns = @JoinColumn(name = "ID_skill"))
    private Set<Skill> skillsRequired;

    @OneToMany(mappedBy = "user")
    private Set<UserSkill> userSkills;

    @OneToMany(mappedBy = "user")
    private List<History> histories;

    public Set<Skill> getSkillsRequired() {
        return skillsRequired;
    }

    public void setSkillsRequired(Set<Skill> skillsRequired) {
        this.skillsRequired = skillsRequired;
    }

    public Set<UserSkill> getUserSkills() {
        return userSkills;
    }

    public void setUserSkills(Set<UserSkill> userSkills) {
        this.userSkills = userSkills;
    }

    public UserExpleo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public Integer getNumarMatricol() {
        return numarMatricol;
    }

    public void setNumarMatricol(Integer numarMatricol) {
        this.numarMatricol = numarMatricol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataAngajare() {
        return dataAngajare;
    }

    public void setDataAngajare(String dataAngajare) {

        this.dataAngajare = dataAngajare;
    }

    public String getFunctie() {
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    public List<Proiect> getProiecte() {
        return proiecte;
    }

    public void setProiecte(List<Proiect> proiecte) {
        this.proiecte = proiecte;
    }

    public void addProiecte(Proiect proiect){

        if(proiecte == null){
            proiecte = new ArrayList<>();
        }
        proiecte.add(proiect);
    }

    public void addSkill(UserSkill skill){
        if(userSkills == null){
            userSkills = new HashSet<>();
        }
        userSkills.add(skill);
    }

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }

    public void removeProiecte(Proiect proiect) {
        proiecte.remove(proiect);
    }

    @Override
    public String toString() {
        return "UserExpleo{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", numarMatricol=" + numarMatricol +
                ", email='" + email + '\'' +
                ", dataAngajare=" + dataAngajare +
                ", functie='" + functie + '\'' +
                '}';
    }


}
