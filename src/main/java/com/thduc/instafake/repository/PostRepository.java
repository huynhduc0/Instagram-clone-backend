package com.thduc.instafake.repository;

import com.thduc.instafake.entity.HashTags;
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
    Set<Posts> findAllByHashtagsAndDeactiveIsFalse(String hashtag);
    Page<Posts> findAllByUserInAndDeactiveIsFalse(List<User> users, Pageable pageable);
    Page<Posts> findAllByUser_IdAndDeactiveIsFalse(long id, Pageable pageable);
    Page<Posts> findAllByUserInOrIdInAndDeactiveIsFalse(List<User> users,List<Long> id, Pageable pageable);
    Page<Posts> findAllByIdInAndDeactiveIsFalse(List<Long> id, Pageable pageable);
    Set<Posts> findAllByIdIn(List<Long> id);
    Set<Posts> findAllByHashtagsIn(List<HashTags> hashTags);
//    Set<Posts> findPostsByAuthorIdIn(String authorId);
}
