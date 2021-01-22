package com.thduc.instafake.repository;

import com.thduc.instafake.entity.Likes;
import com.thduc.instafake.entity.Posts;
import com.thduc.instafake.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends PagingAndSortingRepository<Likes, Long> {
    boolean existsByAuthorAndPost_Id(User author, long id);
    Likes findDistinctFirstByAuthorAndAndPost_Id(User author, long id);
    int countByPost_Id(long id);
    @Query(value = "SELECT user.id,user.avatar,user.username,user.fullname CASE WHEN t.id then 1 ELSE 0 END as following from (SELECT * from follows WHERE follows.from_id = :myIdParam GROUP BY to_id) as t RIGHT JOIN user ON t.to_id = `user`.id " +
            " WHERE user.id in ( SELECT author_id FROM likes WHERE likes.post_id = :idParam  " +
            "GROUP BY author_id ) "
//            + "LIMIT :offset , :limit"
            , nativeQuery = true)
    List<LikeRepository.UserWithFollow> loadListOfLike(@Param("idParam") Long id, @Param("myIdParam") Long myId );
    public static interface UserWithFollow {
        long getId();
        String getAvatar();
        String getUsername();
        String getFullname();
        int getFollowing();
    }

}
