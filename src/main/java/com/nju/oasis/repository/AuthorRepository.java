package com.nju.oasis.repository;

import com.nju.oasis.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/20
 * @description:
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    //根据name查找作者
    Optional<Author> findAuthorByName(String name);

    Author findById(int authorId);

    //根据id修改作者的作者关键字
    @Modifying
    @Transactional
    @Query("UPDATE Author SET authorKeywords=?2 where id=?1")
    void updateAuthorKeywordsById(int id, String newAuthorKeywords);

    //danger,删表！！！
    @Modifying
    @Transactional
    void deleteAll();

}
