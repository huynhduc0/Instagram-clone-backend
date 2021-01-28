package com.thduc.instafake.service;

import com.thduc.instafake.entity.Posts;

import java.util.Set;

public interface HashTagServiceImpl {
    public Set<Posts> loadPostByTagName(String tagname);
}
