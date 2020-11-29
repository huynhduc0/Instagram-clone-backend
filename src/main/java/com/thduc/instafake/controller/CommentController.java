package com.thduc.instafake.controller;

import com.thduc.instafake.entity.Comments;
import com.thduc.instafake.entity.UserPrinciple;
import com.thduc.instafake.security.ActiveUser;
import com.thduc.instafake.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.events.Comment;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping(value = "/add")
    public Comments addComment(@RequestBody Comments comments, @ActiveUser UserPrinciple userPrinciple){
        comments.setAuthor(userPrinciple.getUser());
       return commentService.addComment(comments);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity delete(@RequestBody Comments comments, @ActiveUser UserPrinciple userPrinciple){
        commentService.delete(comments);
        return new ResponseEntity(HttpStatus.OK);
    }

}
