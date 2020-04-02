package com.nju.oasis.service;

import com.nju.oasis.controller.VO.AuthorVO;
import com.nju.oasis.controller.VO.CoworkerLinkVO;
import com.nju.oasis.controller.VO.ResultVO;
import com.nju.oasis.domain.Author;
import com.nju.oasis.domain.statistics.AuthorStatistics;
import com.nju.oasis.domain.Document;
import com.nju.oasis.repository.AuthorRepository;
import com.nju.oasis.repository.statistics.AuthorStatisticsRepository;
import com.nju.oasis.repository.DocumentRepository;
import com.nju.oasis.repository.FieldRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/23
 * @description:
 */
@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    AuthorStatisticsRepository authorStatisticsRepository;
    @Autowired
    FieldRepository fieldRepository;

    public AuthorVO getAuthorDetail(int id){
        //获取基本信息
        Author author = authorRepository.findById(id);
        //获取论文信息
        List<Integer> documentIds = documentRepository.getDocumentsByAuthorId(id);
        List<Document> documentList = documentRepository.findAllById(documentIds);
        //获取合作者信息
        List<Author> authorList = getCoworkers(id);
        AuthorVO authorVO = new AuthorVO();
        BeanUtils.copyProperties(author, authorVO);//复制信息
        authorVO.setDocumentCount(documentList.size());
        authorVO.setDocuments(documentList);
        authorVO.setCoworkers(authorList);

        List<Map<String, String>> fieldActivationCount = fieldRepository.findFieldAndActivationByAuthor(id);
        //按照活跃度降序
        Collections.reverse(fieldActivationCount);
        authorVO.setFieldList(fieldActivationCount);

        return authorVO;
    }

    List<Author> getCoworkers(int id){
        List<Integer> documents = documentRepository.getDocumentsByAuthorId(id);
        List<Integer> coworkers = new ArrayList<>();
        for(int documentId: documents){
            List<Integer> authorIds = documentRepository.getAuthorsIdByDocumentId(documentId);
            for(int authorId: authorIds){
                if(!coworkers.contains(authorId) && authorId!=id){
                    coworkers.add(authorId);
                }
            }
        }
        List<Author> authorList = authorRepository.findAllById(coworkers);
        return authorList;
    }

    public List<AuthorStatistics> getAuthorsMaxDocumentCount(int num){

        Pageable pageable = PageRequest.of(0, num, Sort.Direction.DESC, "documentCount");
        Page<AuthorStatistics> firstPage = authorStatisticsRepository.findAll(pageable);
        List<AuthorStatistics> authorList = firstPage.toList();
        return authorList;
    }

    public ResultVO getAuthorDocumentCountByConference(int id){
        return ResultVO.SUCCESS(documentRepository.summaryGroupByConferenceByAuthor(id));
    }

    public ResultVO getAuthorDocumentCountByDetailConference(int id){
        return ResultVO.SUCCESS(documentRepository.detailSummaryGroupByConferenceByAuthor(id));
    }

    public ResultVO getFieldDocumentAndActivation(int id){
        List<Map<String, String>> fieldActivationCount = fieldRepository.findFieldAndActivationByAuthor(id);
        List<Map<String, String>> document = documentRepository.findDocWithFieldByAuthor(id);
        Map<String, Object> result = new HashMap<>();
        result.put("fields", fieldActivationCount);
        result.put("documents", document);
        return ResultVO.SUCCESS(result);
    }

    public List<CoworkerLinkVO> getAuthorCoworkerLink(int id){
        return coworkerLinkTranslate(authorRepository.getCoworkerLinks(id));
    }

    public List<CoworkerLinkVO> getComplexAuthorCoworkerLink(int id){
        return coworkerLinkTranslate(authorRepository.getComplexCoworkerLinks(id));
    }

    private List<CoworkerLinkVO> coworkerLinkTranslate(List<AuthorRepository.CoworkerLinks> coworkerLinks){
        HashMap<Integer, HashMap<Integer, CoworkerLinkVO>> temp = new HashMap<>();
        ArrayList<CoworkerLinkVO> result = new ArrayList<>();
        for (AuthorRepository.CoworkerLinks coworkerLink:
                coworkerLinks ) {
            HashMap<Integer, CoworkerLinkVO> tempLinks = temp.computeIfAbsent(coworkerLink.getFromId(), k -> new HashMap<>());
            if (tempLinks.get(coworkerLink.getToId()) == null) {
                CoworkerLinkVO.SimpleAuthor from = new CoworkerLinkVO.SimpleAuthor(coworkerLink.getFromId(),
                        coworkerLink.getFromAuthor(), coworkerLink.getFromAffiliationId(), coworkerLink.getFromAffiliation(), coworkerLink.getFromDocCount(), coworkerLink.getFromActivation());
                CoworkerLinkVO.SimpleAuthor to = new CoworkerLinkVO.SimpleAuthor(coworkerLink.getToId(),
                        coworkerLink.getToAuthor(), coworkerLink.getToAffiliationId(), coworkerLink.getToAffiliation(), coworkerLink.getToDocCount(), coworkerLink.getToActivation());
                CoworkerLinkVO coworkerLinkVO = new CoworkerLinkVO(from, to);
                result.add(coworkerLinkVO);
                tempLinks.put(coworkerLink.getToId(), coworkerLinkVO);
            }
            CoworkerLinkVO coworkerLinkVO = tempLinks.get(coworkerLink.getToId());
            CoworkerLinkVO.SimpleDocument doc = new CoworkerLinkVO.SimpleDocument(coworkerLink.getDocId(), coworkerLink.getDoc());
            coworkerLinkVO.getDocs().add(doc);
        }
        return result;
    }

}
