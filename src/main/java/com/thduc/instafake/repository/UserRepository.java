package com.thduc.instafake.repository;

import com.thduc.instafake.entity.User;
import com.thduc.instafake.model.UserWithFollow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long> {
    User findUserByUsername(String username);
    Page findUsersByIdNot(Long id, Pageable pageable);
    boolean existsByUsername(String username);
//    boolean existsByUsernameAndord(String username, String password);

    @Query(value = "SELECT user.id,user.avatar,user.username, CASE WHEN t.id  then 1 ELSE 0 END as following from (SELECT * from follows WHERE follows.from_id = :idParam GROUP BY to_id) as t JOIN user ON t.to_id = `user`.id", nativeQuery = true)
    Set<UserWithFollow> findUserWithFollowStatus(@Param("idParam") Long id);

    @Query(value = "SELECT u.id,u.avatar,u.username, CASE WHEN t.id  then true ELSE false END as following from (SELECT * from follows WHERE follows.from_id =  :idParam GROUP BY to_id) as t  JOIN user u WHERE t.to_id = u.id", nativeQuery = true)
    Page<UserWithFollow> findUserWithFollowStatusPagination(@Param("idParam") Long id, Pageable pageable);

}
