package com.thduc.instafake.service;

import com.thduc.instafake.entity.Posts;
import com.thduc.instafake.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

public interface PostServiceImpl {
    Posts uploadPost(Posts posts,Long uid);
    Page loadNewsFedd(User user);

}
