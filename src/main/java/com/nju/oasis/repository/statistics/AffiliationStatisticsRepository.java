package com.nju.oasis.repository.statistics;

import com.nju.oasis.domain.statistics.AffiliationStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/3/28
 * @description:
 */
public interface AffiliationStatisticsRepository extends JpaRepository<AffiliationStatistics, Integer> {
}
