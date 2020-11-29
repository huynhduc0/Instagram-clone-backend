package com.thduc.instafake.repository;

import com.thduc.instafake.entity.Likes;
import com.thduc.instafake.entity.Posts;
import com.thduc.instafake.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LikeRepository extends PagingAndSortingRepository<Likes, Long> {
    boolean existsByAuthorAndPost(User author, Posts posts);
}
