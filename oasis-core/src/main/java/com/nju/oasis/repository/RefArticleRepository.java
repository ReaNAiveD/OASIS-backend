package com.nju.oasis.repository;

import com.nju.oasis.domain.RefArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/21
 * @description:
 */
@Repository
public interface RefArticleRepository extends JpaRepository<RefArticle, Integer> {

    List<RefArticle> findAllByDocumentId(int documentId);

    /*
    删除表
     */
    @Modifying
    @Transactional
    void deleteAll();
}
