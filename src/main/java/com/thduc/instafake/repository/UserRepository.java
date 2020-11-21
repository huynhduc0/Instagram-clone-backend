package com.thduc.instafake.repository;

import com.thduc.instafake.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long> {
    User findUserByUsername(String username);
    Page findUsersByIdNot(Long id, Pageable pageable);
    boolean existsByUsername(String username);
    boolean existsByUsernameAndPassword(String username, String password);
}
