package com.thduc.instafake.repository;

import com.thduc.instafake.entity.Posts;
import com.thduc.instafake.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Posts,Long> {
    Set<Posts> findAllByHashtags(String hashtag);
    Page<Posts> findAllByUserIn(List<User> users, Pageable pageable);
//    Set<Posts> findPostsByAuthorIdIn(String authorId);
}
