package com.nju.oasis.repository;

import com.nju.oasis.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/20
 * @description:
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {


    @Modifying
    @Transactional
    @Query(value = "insert into document_author (document_id, author_id) values (?1, ?2)", nativeQuery = true)
    public void insertDocuAuthorRel(int docuId, int authorId);
}
