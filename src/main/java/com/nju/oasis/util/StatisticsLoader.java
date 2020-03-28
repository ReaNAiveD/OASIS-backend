package com.nju.oasis.util;

import com.nju.oasis.domain.Affiliation;
import com.nju.oasis.domain.Author;
import com.nju.oasis.domain.Document;
import com.nju.oasis.domain.statistics.AffiliationStatistics;
import com.nju.oasis.repository.*;
import com.nju.oasis.repository.statistics.AffiliationStatisticsRepository;
import com.nju.oasis.repository.statistics.AuthorStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/3/28
 * @description:该类用于存储宏观统计数据，提高查询速度
 */
@Component
public class StatisticsLoader implements CommandLineRunner {

    AuthorRepository authorRepository;
    DocumentRepository documentRepository;
    RefArticleRepository refArticleRepository;
    AuthorStatisticsRepository authorStatisticsRepository;
    FieldRepository fieldRepository;
    AffiliationRepository affiliationRepository;
    AffiliationStatisticsRepository affiliationStatisticsRepository;

    @Autowired
    public StatisticsLoader(AuthorRepository authorRepository,
                            DocumentRepository documentRepository,
                            RefArticleRepository refArticleRepository,
                            AuthorStatisticsRepository authorStatisticsRepository,
                            FieldRepository fieldRepository,
                            AffiliationRepository affiliationRepository,
                            AffiliationStatisticsRepository affiliationStatisticsRepository) {
        this.authorRepository = authorRepository;
        this.documentRepository = documentRepository;
        this.refArticleRepository = refArticleRepository;
        this.authorStatisticsRepository = authorStatisticsRepository;
        this.fieldRepository = fieldRepository;
        this.affiliationRepository = affiliationRepository;
        this.affiliationStatisticsRepository = affiliationStatisticsRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //机构统计信息
//        new Thread(this::loadAffiliationStatistics).start();
    }

    private void loadAffiliationStatistics(){
        System.out.println("Read table affiliation...");
        List<Affiliation> affiliationList = affiliationRepository.findAll();
        System.out.println("Start computing...");
        int count = 0;
        for(Affiliation affiliation: affiliationList){
            System.out.println(++count + ": total " + affiliationList.size());
            AffiliationStatistics statistics = new AffiliationStatistics();
            statistics.setName(affiliation.getName());
            statistics.setAuthorCount(affiliationRepository.authorCount(affiliation.getId()));
            statistics.setDocCount(affiliationRepository.documentCount(affiliation.getId()));
            statistics.setCitationCount(affiliationRepository.citationCount(affiliation.getId()));

            double totalActivation = 0;

            /*
            把所有作者的所有文章活跃度加起来
             */
            List<Author> authorList = authorRepository.findAllByAffiliationId(affiliation.getId());
            for(Author author: authorList){
                List<Integer> documentIdList = documentRepository.getDocumentsByAuthorId(author.getId());
                for(int docId: documentIdList){
                    Document document = documentRepository.findById(docId);
                    totalActivation += document.getActivation();
                }
            }

            statistics.setActivation(totalActivation);

            affiliationStatisticsRepository.save(statistics);
        }
    }
}
