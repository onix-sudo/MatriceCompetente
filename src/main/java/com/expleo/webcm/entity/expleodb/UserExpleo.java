package com.expleo.webcm.entity.expleodb;

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

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;


@AnalyzerDef(name = "ngram",
        tokenizer = @TokenizerDef(factory = WhitespaceTokenizerFactory.class),
        filters = {
//                @TokenFilterDef(factory = StandardFilterFactory.class),
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
//                @TokenFilterDef(factory = ASCIIFoldingFilterFactory.class)
//                @TokenFilterDef(factory = StopFilterFactory.class),
                @TokenFilterDef(factory = EdgeNGramFilterFactory.class,
                        params = {
                                @Parameter(name = "minGramSize", value = "3"),
                                @Parameter(name = "maxGramSize", value = "5") } )
        }
)
@Indexed
@Entity
@Table(name = "user", schema = "expleodb")
public class UserExpleo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_user")
    private int id;

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES, analyzer = @Analyzer(definition = "ngram"))
//    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
    @Column(name="Nume_user")
    private String nume;

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES, analyzer = @Analyzer(definition = "ngram"))
//    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
    @Column(name="Prenume_user")
    private String prenume;

    @NotNull(message = "is required")
    @Min(value = 1000, message = "Numar format din 4 cifre")
    @Max(value = 9999, message = "Numar format din 4 cifre")
    @Column(name="Numar_matricol")
    @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES)
    @FieldBridge(impl = IntegerBridge.class)
    private int numarMatricol;

    @NotEmpty(message = "is required")
    @Email(message = "Nu este valid")
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
    @Column(name="Email")
    private String email;

    @NotNull(message = "is required")
    @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "aaaa-LL-zz")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
    List<Proiect> proiecte = new ArrayList<>();

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

    public int getNumarMatricol() {
        return numarMatricol;
    }

    public void setNumarMatricol(int numarMatricol) {
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
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//        Date date = null;
//        try {
//            date = dateFormat.parse(dataAngajare);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
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
