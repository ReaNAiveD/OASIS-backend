package com.nju.oasis.controller.VO;

import com.nju.oasis.domain.Author;
import com.nju.oasis.domain.RefArticle;
import lombok.Data;

import javax.persistence.Column;
import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/23
 * @description:
 */
@Data
public class DocumentVO {
    //基本信息
    private int id;
    private String title;
    private String publicationTitle;
    private int publicationYear;
    private int volume;
    private String startPage;
    private String endPage;
    private String docuAbstract;
    private String doi;
    private String pdfLink;
    private String authorKeywords;
    private String keywords;
    private int referenceCount;
    private String publisher;
    private String documentIdentifier;
    private int totalDownload;
    //作者
    private List<Author> authors;
    //引用
    private List<RefArticle> ref;
}
