package com.thduc.instafake.service;

import com.thduc.instafake.entity.HashTags;
import com.thduc.instafake.entity.Posts;

import java.util.Set;

public interface HashTagServiceImpl {
    public HashTags loadPostByTagName(String tagname);
}
