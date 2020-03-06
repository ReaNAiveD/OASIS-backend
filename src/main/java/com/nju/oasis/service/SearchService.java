package com.nju.oasis.service;

import com.nju.oasis.controller.VO.DocumentVO;
import com.nju.oasis.controller.form.SearchForm;
import com.nju.oasis.domain.Document;
import com.nju.oasis.repository.AuthorRepository;
import com.nju.oasis.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/26
 * @description:
 */
@Service
public class SearchService {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    DocumentService documentService;

    public Page<DocumentVO> searchDocument(SearchForm searchForm){
        //搜索的document id列表
        List<Integer> resultIdList = documentRepository.selectAllId();

        //混合关键字搜索
        if(!StringUtils.isEmpty(searchForm.getCombined())){
            List<Integer> idList= searchCombined(searchForm.getCombined());
            resultIdList.retainAll(idList);
        }
        //作者关键字查询
        if(!StringUtils.isEmpty(searchForm.getAuthor())){
            List<Integer> idList = searchAuthor(searchForm.getAuthor());
            resultIdList.retainAll(idList);
        }
        //机构关键字查询
        if(!StringUtils.isEmpty(searchForm.getAffiliation())){
            List<Integer> idList = searchAffiliation(searchForm.getAffiliation());
            resultIdList.retainAll(idList);
        }
        //标题关键字查询
        if(!StringUtils.isEmpty(searchForm.getTitle())){
            List<Integer> idList = searchTitle(searchForm.getTitle());
            resultIdList.retainAll(idList);
        }
        //搜索摘要
        if(!StringUtils.isEmpty(searchForm.getMyAbstract())){
            List<Integer> idList = searchAbstract(searchForm.getMyAbstract());
            resultIdList.retainAll(idList);
        }
//        搜索时间
        if(searchForm.getYearFrom()!=0 && searchForm.getYearTo()!=0){
            List<Integer> idList = searchTime(searchForm.getYearFrom(), searchForm.getYearTo());
            resultIdList.retainAll(idList);
        }
        //根据会议筛选列表
        if(searchForm.getConference().equals("ase")){
            resultIdList = documentRepository.filterIdsByConference(resultIdList, "(ASE)");
        }
        else if(searchForm.getConference().equals("icse")){
            resultIdList = documentRepository.filterIdsByConference(resultIdList, "(ICSE)");
        }
        else if(searchForm.getConference().equals("others")){
            //others的筛选
            resultIdList = documentRepository.filterIdsByConference(resultIdList, "Engineering");
        }
        else{
            //pass
        }

        Pageable pageable;
        if(searchForm.getOrderBy().equals("early")){
            pageable = PageRequest.of(searchForm.getPage(), searchForm.getPageSize(),
                    Sort.Direction.ASC, "publicationYear");
        }
        else{
            pageable = PageRequest.of(searchForm.getPage(), searchForm.getPageSize(),
                    Sort.Direction.DESC, "publicationYear");
        }


        Page<Document> documentPage = documentRepository.findDocumentsByIdIn(resultIdList, pageable);
        Page<DocumentVO> resultPage = documentPage.map(target->
                documentService.getDocumentListItem(target.getId()));

        return resultPage;
    }

    private List<Integer> searchCombined(String combined){
        String[] searchItem;
        if(combined.contains("&")){
            searchItem = combined.split("&");
            for(int i=0;i<searchItem.length;i++){
                searchItem[i] = searchItem[i].trim();
            }
        }
        else{
            searchItem = combined.split("\\s+");
        }
        List<Integer> idList = searchAll(searchItem[0]);
        for(String item: searchItem){
            idList.retainAll(searchAll(item));
        }
        return idList;
    }

    private List<Integer> searchAll(String item){
        List<Integer> idList = new ArrayList<>();
        if(org.apache.commons.lang3.StringUtils.isNumeric(item)){
            int year = Integer.parseInt(item);
            idList.addAll(searchTime(year, year));
        }

        idList.addAll(searchTitle(item));
        idList.addAll(searchAbstract(item));
        idList.addAll(searchAuthor(item));
        idList.addAll(searchAffiliation(item));
        return idList;
    }

    private List<Integer> searchAuthor(String authorStr){
        List<Integer> authorList = authorRepository.selectIdsByNameLike(authorStr);
        return documentRepository.getDocumentsByAuthorIds(authorList);
    }

    private List<Integer> searchAffiliation(String affiliationStr){
        List<Integer> authorList = authorRepository.selectIdsByAffiliation(affiliationStr);
        return documentRepository.getDocumentsByAuthorIds(authorList);
    }

    private List<Integer> searchAbstract(String abstractStr){
        return documentRepository.selectIdsByAbstractLike(abstractStr);
    }

    private List<Integer> searchTitle(String title){
        return documentRepository.selectIdsByTitleLike(title);
    }

    private List<Integer> searchTime(int yearFrom, int yearTo){
        return documentRepository.selectIdsByTime(yearFrom, yearTo);
    }
}
