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

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long> {
    User findUserByUsername(String username);
    Page findUsersByIdNot(Long id, Pageable pageable);
    boolean existsByUsername(String username);
//    boolean existsByUsernameAndord(String username, String password);

    @Query(value = "SELECT user.id,user.avatar,user.username, CASE WHEN t.id  then 1 ELSE 0 END as following from (SELECT * from follows WHERE follows.from_id = :idParam GROUP BY to_id) as t RIGHT JOIN user ON t.to_id = `user`.id LIMIT :offset , :limit", nativeQuery = true)
    List<UserWithFollow> findUserWithFollowStatus(@Param("idParam") Long id, @Param("limit") int limit, @Param("offset") int offset);
    public static interface UserWithFollow {
         long getId();
         String getAvatar();
         String getUsername();
         int getFollowing();
    }

//    @Query(value = "SELECT user.id,user.avatar,user.username, CASE WHEN t.id  then 1 ELSE 0 END as following from (SELECT * from follows WHERE follows.from_id = :idParam GROUP BY to_id) as t RIGHT JOIN user ON t.to_id = `user`.id", nativeQuery = true)
//    Page<UserWithFollow> findUserWithFollowStatusPagination(@Param("idParam") Long id, Pageable pageable);
}
