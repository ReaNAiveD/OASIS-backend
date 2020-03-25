package com.nju.oasis.repository;

import com.nju.oasis.domain.Affiliation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/3/25
 * @description:
 */
public interface AffiliationRepository extends JpaRepository<Affiliation, Integer> {

    Optional<Affiliation> findByName(String name);
}
