package com.thduc.instafake.service;

import com.thduc.instafake.entity.User;
import com.thduc.instafake.repository.FollowRepository;

import java.util.List;

public interface FollowServiceImpl {
    boolean changeFollows(User from, User to);

    List<FollowRepository.UserWithFollow> getFollowers(long id, long myid, int limit, int offset);
}
