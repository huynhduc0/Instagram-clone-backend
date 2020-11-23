package com.thduc.instafake.service;

import com.thduc.instafake.entity.User;

public interface FollowServiceImpl {
    boolean changeFollows(User from, User to);
}
