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
}
