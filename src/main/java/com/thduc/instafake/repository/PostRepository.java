package com.thduc.instafake.repository;

import com.thduc.instafake.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Posts,Long> {
    Set<Posts> findAllByHashtags(String hashtag);
//    Set<Posts> findPostsByAuthorIdIn(String authorId);

}
