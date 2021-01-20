package com.thduc.instafake.service;

import com.thduc.instafake.entity.Follows;
import com.thduc.instafake.entity.Posts;
import com.thduc.instafake.entity.ReportDetails;
import com.thduc.instafake.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface PostServiceImpl {
    Posts uploadPost(Posts posts,Long uid);

    Page loadNewsFedd(Long id, User user, Pageable pageable);

    Page loadPopular(Long id, User user, Pageable pageable);

    Page<Posts> loadPostByUid(long id, Pageable pageable);

    boolean addReport(ReportDetails reportDetails, long postId);
    Page loadAllReport(Pageable pageable);
}
