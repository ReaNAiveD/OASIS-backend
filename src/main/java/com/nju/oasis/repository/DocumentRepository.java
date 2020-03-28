package com.nju.oasis.repository;

import com.nju.oasis.domain.Author;
import com.nju.oasis.domain.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/20
 * @description:
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {


    /*
    插入文章-作者关系
     */
    @Modifying
    @Transactional
    @Query(value = "insert into document_author (document_id, author_id) values (?1, ?2)", nativeQuery = true)
    void insertDocuAuthorRel(int docuId, int authorId);

    /*
    根据作者id获得论文id
     */
    @Query(value = "select document_id from document_author where author_id=?1", nativeQuery = true)
    List<Integer> getDocumentsByAuthorId(int authorId);

    @Query(value = "select document_id from document_author where author_id in ?1", nativeQuery = true)
    List<Integer> getDocumentsByAuthorIds(List<Integer> authorList);

    /*
    根据论文id获得作者id
     */
    @Query(value = "select author_id from document_author where document_id=?1", nativeQuery = true)
    List<Integer> getAuthorsIdByDocumentId(int documentId);

    /*
    根据id获得论文
     */
    Document findById(int documentId);

    /*
    测试方法
     */
    @Query(value = "select title from Document where id=?1")
    String findTitleById(int id);

    /*
    danger!!!删表
     */
    @Modifying
    @Transactional
    void deleteAll();

    /*
    danger!!!删表
     */
    @Modifying
    @Transactional
    @Query(value = "delete from document_author", nativeQuery = true)
    void deleteTableDocumentAuthor();

    /*
    分页方式，根据id列表获得论文列表
     */
    Page<Document> findDocumentsByIdIn(List<Integer> idList, Pageable pageable);

    /*
    获取所有id
     */
    @Query("select id from Document")
    List<Integer> selectAllId();

    @Query("select id from Document where title like %?1%")
    List<Integer> selectIdsByTitleLike(String title);

    @Query("select id from Document where docuAbstract like %?1%")
    List<Integer> selectIdsByAbstractLike(String abstractStr);

    @Query("select id from Document where publicationYear>=?1 and publicationYear<=?2")
    List<Integer> selectIdsByTime(int yearFrom, int yearTo);

    /*
    按照会议筛选
     */
    @Query("select id from Document where id in ?1 and publicationTitle like %?2")
    List<Integer> filterIdsByConference(List<Integer> targetList, String conference);

    @Query(value = "select substring_index(substring_index(publication_title, ')', 1), '(', -1) as conference, count(*) as docCount from " +
            "(select document_id from document_author where author_id=?) docs left join document on document_id=document.id " +
            "group by substring_index(substring_index(publication_title, ')', 1), '(', -1) order by docCount;", nativeQuery = true)
    List<Map<String, String>> summaryGroupByConferenceByAuthor(int authorId);

    @Query(value = "select publication_title as conference, count(*) as docCount from " +
            "(select document_id from document_author where author_id=?) docs left join document on document_id=document.id " +
            "group by publication_title order by docCount;", nativeQuery = true)
    List<Map<String, String>> detailSummaryGroupByConferenceByAuthor(int authorId);


    @Query(value = "select id, title, field_id, total_citations, (document.total_citations+5)/(2025-document.publication_year) as docActivation from document where id in " +
            "(select document_id from document_author where author_id = ?);", nativeQuery = true)
    List<Map<String, String>> findDocWithFieldByAuthor(int authorId);

    @Query(value = "select publication_year as year, count(id) from document where field_id=? group by year order by year;", nativeQuery = true)
    List<Map<String, String>> findDocCountYearSummaryByField(int fieldId);
}
