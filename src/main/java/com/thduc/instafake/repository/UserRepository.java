package com.thduc.instafake.repository;

import com.thduc.instafake.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByUsernameAndPassword(String username, String password);
}
