package com.thduc.instafake.service;

import com.thduc.instafake.entity.Posts;
import org.springframework.data.domain.Page;

public interface HashTagServiceImpl {
    public Page<Posts> loadPostByTagName(String tagname);
}
