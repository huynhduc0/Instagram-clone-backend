package com.thduc.instafake.repository;

import com.thduc.instafake.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation,Long> {
    boolean existsByUserIdAndPostId(long uid, long pid);
    @Query("select p.postId from Recommendation p where p.userId = :from_id")
    List<Long> getAllIds(@Param("from_id") Long from_id);
    @Query(value = "SELECT author_id as uid, post_id as pid, num_of_likes as num\n" +
            "FROM likes JOIN posts on posts.id = likes.post_id",nativeQuery = true)
    List<LikeUid> getAllLikeAno();
    public static interface LikeUid {
        long getUid();
        long getPid();
        long getNum();
    }
    @Query(value = "SELECT id as pid FROM posts ",nativeQuery = true)
    List<PostIDOnly> getPostIDOnly();
    public static interface PostIDOnly {
        long getPid();
    }
}
