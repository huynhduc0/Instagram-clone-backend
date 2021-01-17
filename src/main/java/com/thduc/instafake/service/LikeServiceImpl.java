package com.thduc.instafake.service;

import com.thduc.instafake.entity.Likes;
import com.thduc.instafake.repository.LikeRepository;

import java.util.List;

public interface LikeServiceImpl {
    int changeLike(Likes likes);

    List<LikeRepository.UserWithFollow> loadListOfLike(long id, long uid);
}
