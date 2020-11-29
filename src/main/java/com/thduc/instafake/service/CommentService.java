package com.thduc.instafake.service;

import com.thduc.instafake.entity.Comments;
import com.thduc.instafake.entity.Posts;
import com.thduc.instafake.entity.User;
import com.thduc.instafake.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLDataException;

@Service
public class CommentService implements CommentServiceImpl {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public Comments addComment(Comments comments) {
        return commentRepository.save(comments);
    }

    @Override
    public boolean delete(Comments comments) {
         try {
             commentRepository.delete(comments);
         }catch (Exception ex){
             return false;
         }
         return true;
    }
}
