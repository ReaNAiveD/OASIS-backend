package com.nju.oasis.repository;

import com.nju.oasis.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/2/15
 * @description:
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByAccount(String account);
}
