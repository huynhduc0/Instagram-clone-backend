package com.thduc.instafake.repository;

import com.thduc.instafake.entity.Follows;
import com.thduc.instafake.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follows, Long> {
    Follows findByFromAndTo(User from, User to);
//    <Set>Follows findAllByFrom(User from);
    @Query(value = "select fl.to from Follows fl where fl.from.id = :from_id")
    List<User> LoadFollowing(@Param("from_id") Long from_id);

    boolean existsByFrom_IdAndTo_Id(long myId, long id);

    @Query(value = "SELECT user.id,user.avatar,user.username, CASE WHEN t.id then 1 ELSE 0 END as following from (SELECT * from follows WHERE follows.from_id = :myIdParam GROUP BY to_id) as t RIGHT JOIN user ON t.to_id = `user`.id " +
            " WHERE user.id in ( SELECT to_id FROM follows WHERE follows.from_id = :idParam  GROUP BY to_id ) "
//            + "LIMIT :offset , :limit"
            , nativeQuery = true)
    List<UserWithFollow> viewFollowingsUser(@Param("idParam") Long id, @Param("myIdParam") Long myId );
//    , @Param("limit") int limit, @Param("offset") int offset);
    public static interface UserWithFollow {
        long getId();
        String getAvatar();
        String getUsername();
        int getFollowing();
    }

    @Query(value = "SELECT user.id,user.avatar,user.username, CASE WHEN t.id then 1 ELSE 0 END as following from (SELECT * from follows WHERE follows.from_id = :myIdParam GROUP BY to_id) as t RIGHT JOIN user ON t.to_id = `user`.id " +
            " WHERE user.id in ( SELECT from_id FROM follows WHERE follows.to_id = :idParam  GROUP BY to_id ) "
//            + "LIMIT :offset , :limit"
            , nativeQuery = true)
    List<UserWithFollow> viewFollowersUser(@Param("idParam") Long id, @Param("myIdParam") Long myId );

}
