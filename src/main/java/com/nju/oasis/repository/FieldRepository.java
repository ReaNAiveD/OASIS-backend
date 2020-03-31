package com.nju.oasis.repository;

import com.nju.oasis.domain.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/3/25
 * @description:
 */
public interface FieldRepository extends JpaRepository<Field, Integer> {

    @Query(value = "select field_id, field.field as field_name, sum(docActivation) as activation from " +
            "(select id, field_id, (document.total_citations+5)/(2025-document.publication_year) as docActivation from document where id in " +
            "(select document_id from document_author where author_id=?)) docAct " +
            "left join field on docAct.field_id=field.id group by field_id order by activation", nativeQuery = true)
    List<Map<String, String>> findFieldAndActivationByAuthor(int authorId);

    @Query(value = "select field, field_id as fieldId, sum((document.total_citations+5)/(2025-document.publication_year)) as fieldActivation, count(document.id) as docCount from document " +
            "left join field f on document.field_id = f.id group by field, field_id order by fieldActivation desc ", nativeQuery = true)
    List<HotFieldItem> findHotField();

    interface HotFieldItem{
        String getField();
        Long getFieldId();
        Double getFieldActivation();
        Integer getDocCount();
    }

}
