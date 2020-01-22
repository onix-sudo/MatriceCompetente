package com.expleo.webcm.entity.expleodb;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;


@AnalyzerDef(name = "keywordTokenizer",
        tokenizer = @TokenizerDef(factory = WhitespaceTokenizerFactory.class),
        filters = {
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                @TokenFilterDef(factory = ASCIIFoldingFilterFactory.class)
        })

@Entity
@Table(name = "record", schema = "expleodb")
@Indexed
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_record")
    private Integer id;

    @Column(name = "id_autor")
    private Long id_autor;

    @Field(name = "categorie", index = Index.YES)
    @Field(name = "categorieKw", index = Index.YES,analyze = Analyze.YES,store = Store.NO,analyzer = @Analyzer(definition = "keywordTokenizer"))
    @Column(name = "Categorie")
    private String categorie;

    @Field(name = "titlu", index = Index.YES)
    @Field(name = "titluKw", index = Index.YES,analyze = Analyze.YES,store = Store.NO,analyzer = @Analyzer(definition = "keywordTokenizer"))
    @Column(name = "Titlu")
    private String titlu;

    @Field(name = "descriere", index = Index.YES)
    @Field(name = "descriereKw", index = Index.YES,analyze = Analyze.YES,store = Store.NO,analyzer = @Analyzer(definition = "keywordTokenizer"))
    @Column(name = "Descriere")
    private String descriere;

    @Column(name = "Autor")
    private String autor;

    @OneToMany(mappedBy = "record")
    private List<Solution> solutions;

    public Record() {
    }

    public Record(Long id_autor, String categorie, String titlu, String descriere, String autor) {
        this.id_autor = id_autor;
        this.categorie = categorie;
        this.titlu = titlu;
        this.descriere = descriere;
        this.autor = autor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getId_autor() {
        return id_autor;
    }

    public void setId_autor(Long id_autor) {
        this.id_autor = id_autor;
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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public List<Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<Solution> solutions) {
        this.solutions = solutions;
    }

    public String getLastDate() throws ParseException {

        List<Date> dates = new ArrayList<Date>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        for(Solution solution:solutions){
            dates.add(solution.getDate1());
        }

        Date latest = Collections.max(dates);

        String ldate = dateFormat.format(latest);

        return ldate;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", id_autor=" + id_autor +
                ", categorie='" + categorie + '\'' +
                ", titlu='" + titlu + '\'' +
                ", descriere='" + descriere + '\'' +
                ", autor='" + autor + '\'' +
                '}';
    }
}
