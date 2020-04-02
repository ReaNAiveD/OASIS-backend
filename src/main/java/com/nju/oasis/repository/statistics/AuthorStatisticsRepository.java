package com.nju.oasis.repository.statistics;

import com.nju.oasis.domain.statistics.AuthorStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/26
 * @description:
 */
@Repository
public interface AuthorStatisticsRepository extends JpaRepository<AuthorStatistics, Integer> {
    List<AuthorStatistics> findAllByAuthorIdIn(List<Integer> authorIdList);
}
