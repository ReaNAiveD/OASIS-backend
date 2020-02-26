package com.nju.oasis.repository;

import com.nju.oasis.domain.AuthorStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/26
 * @description:
 */
@Repository
public interface AuthorStatisticsRepository extends JpaRepository<AuthorStatistics, Integer> {
}
