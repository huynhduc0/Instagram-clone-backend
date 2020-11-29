package com.thduc.instafake.service;

import com.thduc.instafake.entity.Comments;
import com.thduc.instafake.entity.Posts;
import com.thduc.instafake.entity.User;

public interface CommentServiceImpl {
    Comments addComment(Comments comments);
    boolean delete(Comments comments);
}
