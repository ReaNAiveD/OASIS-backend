package com.nju.oasis.service;

import com.nju.oasis.domain.Author;
import com.nju.oasis.domain.statistics.AuthorStatistics;
import com.nju.oasis.model.map.*;
import com.nju.oasis.repository.AuthorRepository;
import com.nju.oasis.repository.statistics.AuthorStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/3/28
 * @description:构建图
 */
@Service
public class MapService {

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    AuthorStatisticsRepository authorStatisticsRepository;
//    @Autowired
//    DocumentRepository documentRepository;

    /*
    构建作者中心的合作者关系图
     */
    public MyMap coworkerRelationship(int authorId){

        List<Integer> allAuthors = new ArrayList<>();//存放图上所有作者的列表。
        allAuthors.add(authorId);
        //查找一层合作者
        List<Integer> coworkerIds = authorRepository.getCoworkersById(authorId);
        allAuthors.addAll(coworkerIds);
        /*
        查找二层合作者，可扩充
         */

        List<Author> authorList = authorRepository.findAllById(allAuthors);
        List<AuthorStatistics> authorStatisticsList = authorStatisticsRepository.findAllByAuthorIdIn(allAuthors);
        /*
        制作点
         */
        List<Vertex> vertexList = new ArrayList<>();
        List<Integer> affIdLog = new ArrayList<>();
        for(int i=0;i<allAuthors.size();i++){
            int authorId1 = allAuthors.get(i);
            Vertex vertex = new Vertex();
            /*
            填充内容
             */
            AuthorOfVertex authorOfVertex = new AuthorOfVertex();
            Author author = authorList.get(i);
            authorOfVertex.setId(author.getId());
            authorOfVertex.setName(author.getName());
            authorOfVertex.setAffiliation(author.getAffiliation());
            authorOfVertex.setAffiliationId(author.getAffiliationId());
            AuthorStatistics authorStatistics = authorStatisticsList.get(i);
            authorOfVertex.setDocumentCount(authorStatistics.getDocumentCount());
            //计算活跃度
            authorOfVertex.setActivation(authorStatistics.getActivation());
            /*
            填充点
             */
            vertex.setId(i);
            if(authorId1==authorId){
                vertex.setCore(true);
            }
            else{
                vertex.setCore(false);
            }
            vertex.setContent(authorOfVertex);
            //点颜色类别
            if(affIdLog.contains(authorOfVertex.getAffiliationId())){
                vertex.setCategory(affIdLog.indexOf(authorOfVertex.getAffiliationId()));
            }
            else {
                affIdLog.add(authorOfVertex.getAffiliationId());
                vertex.setCategory(affIdLog.size()-1);
            }

            vertexList.add(vertex);
        }
        /*
        制作边
         */
        List<int[]> worksNum = authorRepository.getWorksNumBetweenAuthors(allAuthors);
        List<Edge> edgeList = new ArrayList<>();
        for(int i=0;i<allAuthors.size();i++){
            for(int j=i+1;j<allAuthors.size();j++){
                //计算共同作品数量
                int workNum = 0;
                for(int[] line: worksNum){
                    if(line[0]==allAuthors.get(i) && line[1]==allAuthors.get(j)){
                        workNum = line[2];
                        break;
                    }
                }
                if(workNum!=0){
                    CoworkerOfEdge coworkerOfEdge = new CoworkerOfEdge();
                    coworkerOfEdge.setCommonWorks(workNum);
                    String name1 = authorList.get(i).getName();
                    String name2 = authorList.get(j).getName();
                    coworkerOfEdge.setWorksUrl(CoworkerOfEdge.urlHead+name1+" "+name2);
                    Edge edge = new Edge();
                    edge.setV1(i);
                    edge.setV2(j);
                    edge.setContent(coworkerOfEdge);
                    edge.setWeight(workNum);
                    edgeList.add(edge);
                }
            }
        }

        //生成图
        MyMap myMap = MyMap.builder().vertices(vertexList).edges(edgeList).build();

        return myMap;
    }
}
