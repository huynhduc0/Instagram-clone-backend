package com.thduc.instafake.service;

import com.thduc.instafake.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentServiceImpl {
    Comments addComment(Comments comments);
    boolean delete(Comments comments);

    boolean addReport(ReportDetails reportDetails, long postId);
    Page loadCommentByPostId(long id, Pageable pageable);
}
