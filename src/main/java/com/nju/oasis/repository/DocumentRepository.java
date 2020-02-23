package com.nju.oasis.repository;

import com.nju.oasis.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    /*
    根据论文id获得作者id
     */
    @Query(value = "select author_id from document_author where document_id=?1", nativeQuery = true)
    List<Integer> getAuthorsByDocumentId(int documentId);

    /*
    根据id获得论文
     */
    Document findById(int documentId);

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


}
