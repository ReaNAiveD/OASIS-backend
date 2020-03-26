package com.nju.oasis.repository;

import com.nju.oasis.domain.Affiliation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/3/25
 * @description:
 */
public interface AffiliationRepository extends JpaRepository<Affiliation, Integer> {

    Optional<Affiliation> findByName(String name);

    Affiliation findById(int id);

    @Query(value = "select count(*) from author where author.affiliation_id=?", nativeQuery = true)
    Integer authorCount(int id);

    @Query(value = "select count(distinct document_id) from document_author where author_id in (select author.id from author where author.affiliation_id=?)", nativeQuery = true)
    Integer documentCount(int id);

    @Query(value = "select sum(total_citations) from document where document.id in " +
            "(select distinct document_id from document_author where author_id in " +
            "(select author.id from author where author.affiliation_id=?))", nativeQuery = true)
    Integer citationCount(int id);
}
