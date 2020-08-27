package com.nju.oasis.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/20
 * @description:
 */
@Data
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String publicationTitle;
    private int publicationYear;
    private int volume;
    private String startPage;
    private String endPage;
    @Column(name="abstract")
    private String docuAbstract;
    private String doi;
    private String pdfLink;
    private String authorKeywords;
    private String keywords;
    private int referenceCount;
    private String publisher;
    private String documentIdentifier;
    private int totalDownload;

    /*
    领域id
     */
    private int fieldId;
    /*
    总引用量
     */
    private int totalCitations;

    public double getActivation(){
        //(document.total_citations+5)/(2025-document.publication_year)
        double activation = ((double)(this.totalCitations+5))/(2025-this.publicationYear);
        return activation;
    }
}
