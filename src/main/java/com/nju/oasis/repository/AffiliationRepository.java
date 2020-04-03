package com.nju.oasis.repository;

import com.nju.oasis.domain.Affiliation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
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

    @Query(value = "select field, field_id, count(document.id) as docCount from document left join field on document.field_id = field.id " +
            "where document.id in " +
            "(select distinct document_id from document_author where author_id in " +
            "(select author.id from author where author.affiliation_id=?)) group by field_id order by docCount desc", nativeQuery = true)
    List<Map<String, String>> fieldStatistic(int id);

    @Query(value = "select a.name, a.id, count(document_id) as docCount from document_author left join author a on document_author.author_id = a.id " +
            "where author_id in (select author.id from author where author.affiliation_id=?) group by a.id order by docCount desc", nativeQuery = true)
    List<Map<String, String>> authorStatistic(int id);

    @Query(value = "select a.name, a.id, count(document_id) as docCount, sum(docAct.docActivation) as activation from document_author left join author a on document_author.author_id = a.id " +
            "left join (select id, (document.total_citations+5)/(2025-document.publication_year) as docActivation from document) docAct on document_id=docAct.id " +
            "where author_id in (select author.id from author where author.affiliation_id=?) group by a.id order by activation desc", nativeQuery = true)
    List<Map<String, String>> authorActivationStatistic(int id);

    @Query(value = "select affiliation_id, affiliation, sum(docAct) as activation\n" +
            "from (select distinct document_id, affiliation_id, a2.name as affiliation, (d.total_citations+5)/(2025-d.publication_year) as docAct\n" +
            "    from document_author left join document d on document_author.document_id = d.id left join author a on document_author.author_id = a.id\n" +
            "    left join affiliation a2 on a.affiliation_id = a2.id where d.field_id=? and affiliation_id<>0) aff_doc\n" +
            "group by affiliation_id, affiliation order by activation desc", nativeQuery = true)
    List<Map<String, String>> fieldActivationStatistic(int id);
}
