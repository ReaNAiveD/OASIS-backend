package com.nju.oasis.repository;

import com.nju.oasis.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
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

    @Query("select id from Author where name like %?1%")
    List<Integer> selectIdsByNameLike(String name);

    @Query("select id from Author where affiliation like %?1%")
    List<Integer> selectIdsByAffiliation(String affiliation);

    @Query(value = "select id, name, first_name, last_name, author_affiliation, author_keywords, ieee_id, affiliation_id from author where author.id in (select author_id from document_author where document_id=?1)", nativeQuery = true)
    List<Author> getAuthorsByDocumentId(int documentId);

    @Query(value = "select author_id, name, sum((d.total_citations+5)/(2025-d.publication_year)) as activation from document_author left join document d on document_author.document_id = d.id left join author a on document_author.author_id = a.id " +
            "where d.field_id=? group by author_id, name order by activation desc", nativeQuery = true)
    List<Map<String, String>> getAuthorActivationByField(int fieldId);

    List<Author> findAllByAffiliationId(int affiliationId);

    /*
    获得某作者的合作者id
     */
    @Query(value = "select distinct da1.author_id from document_author da1 where da1.author_id<>?1 and da1.document_id in "+
            "(select da2.document_id from document_author da2 where da2.author_id=?1)", nativeQuery = true)
    List<Integer> getCoworkersById(int id);
    /*
    计算作者活跃度
     */
    @Query(value = "select sum((d.total_citations+5)/(2025-d.publication_year)) from document_author left join document d on document_author.document_id = d.id and document_author.author_id=?1", nativeQuery = true)
    double getActivationById(int id);
    /*
    两个作者共同的作品数量
     */
    @Query(value = "select da1.author_id as id1, da2.author_id as id2, count(*) as num from document_author da1,document_author da2" +
            " where da1.document_id=da2.document_id and da1.author_id<da2.author_id and da1.author_id in ?1 and da2.author_id in ?1" +
            " group by da1.author_id, da2.author_id", nativeQuery = true)
    List<int[]> getWorksNumBetweenAuthors(List<Integer> authorIds);

    /*
     * 获取某作者的所有直接合作者以及他们的合作作品
     * @param authorId 作者Id
     * @return 合作者及合作作品
     */
    @Query(value = "select fromId, froma.name as fromAuthor, froma.affiliation_id as fromAffiliationId, fromaff.name as fromAffiliation, fromas.document_count as fromDocCount, fromas.activation as fromActivation,\n" +
            "       document_author.author_id as toId, toa.name as toAuthor, toa.affiliation_id as toAffiliationId, toaff.name as toAffiliation, toas.document_count as toDocCount, toas.activation as toActivation, f.document_id as docId, d.title as doc\n" +
            "from (select author_id as fromId, document_id from document_author where author_id=?) f\n" +
            "    left join document_author on f.document_id=document_author.document_id\n" +
            "    left join author toa on document_author.author_id = toa.id left join author froma on f.fromId=froma.id\n" +
            "    left join document d on document_author.document_id = d.id\n" +
            "    left join affiliation toaff on toa.affiliation_id = toaff.id\n" +
            "    left join affiliation fromaff on froma.affiliation_id = fromaff.id\n" +
            "    left join author_statistics `fromas` on froma.id = `fromas`.author_id\n" +
            "    left join author_statistics `toas` on toa.id = `toas`.author_id\n" +
            "where fromId<>document_author.author_id", nativeQuery = true)
    List<CoworkerLinks> getCoworkerLinks(int authorId);

    @Query(value = "select fromId, froma.name as fromAuthor, froma.affiliation_id as fromAffiliationId, fromaff.name as fromAffiliation, fromas.document_count as fromDocCount, fromas.activation as fromActivation,\n" +
            "       document_author.author_id as toId, toa.name as toAuthor, toa.affiliation_id as toAffiliationId, toaff.name as toAffiliation, toas.document_count as toDocCount, toas.activation as toActivation, f.document_id as docId, d.title as doc\n" +
            "from (select author_id as fromId, document_id from document_author where author_id in (select author_id from document_author where document_id in (select document_id from document_author where author_id=?))) f\n" +
            "    left join document_author on f.document_id=document_author.document_id\n" +
            "    left join author toa on document_author.author_id = toa.id left join author froma on f.fromId=froma.id\n" +
            "    left join document d on document_author.document_id = d.id\n" +
            "    left join affiliation toaff on toa.affiliation_id = toaff.id\n" +
            "    left join affiliation fromaff on froma.affiliation_id = fromaff.id\n" +
            "    left join author_statistics `fromas` on froma.id = `fromas`.author_id\n" +
            "    left join author_statistics `toas` on toa.id = `toas`.author_id\n" +
            "where fromId<document_author.author_id", nativeQuery = true)
    List<CoworkerLinks> getComplexCoworkerLinks(int authorId);

    interface CoworkerLinks {
        int getFromId();
        String getFromAuthor();
        int getFromAffiliationId();
        String getFromAffiliation();
        int getFromDocCount();
        double getFromActivation();
        int getToId();
        String getToAuthor();
        int getToAffiliationId();
        String getToAffiliation();
        int getToDocCount();
        double getToActivation();
        int getDocId();
        String getDoc();
    }

}
