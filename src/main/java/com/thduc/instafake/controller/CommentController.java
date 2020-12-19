package com.thduc.instafake.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.thduc.instafake.constant.Constant;
import com.thduc.instafake.constant.NotifcationType;
import com.thduc.instafake.entity.*;
import com.thduc.instafake.security.ActiveUser;
import com.thduc.instafake.service.CommentService;
import com.thduc.instafake.service.NotificationService;
import com.thduc.instafake.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.events.Comment;

@RestController
@RequestMapping(value = "/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    NotificationService notificationService;

    @PostMapping(value = "/add")
    public Comments addComment(@RequestBody Comments comments, @ActiveUser UserPrinciple userPrinciple){
        comments.setAuthor(userPrinciple.getUser());
       Comments cmt = commentService.addComment(comments);
       if(cmt.getAuthor().getId() != cmt.getPost().getUser().getId())
           notificationService.addNotification(userPrinciple.getUser(),
                   cmt.getPost().getUser(), Constant.COMMENT_NOTI_MESSAGE, NotifcationType.COMMENT, cmt.getPost().getId());
        return cmt;
    }

    @PostMapping(value = "/delete")
    public ResponseEntity delete(@RequestBody Comments comments, @ActiveUser UserPrinciple userPrinciple){
        commentService.delete(comments);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping(value = "/report/{id}")
    public ResponseEntity addReport(@PathVariable long id,@RequestBody ReportDetails reportDetails, @ActiveUser UserPrinciple userPrinciple){
        reportDetails.setReportUser(userPrinciple.getUser());
//        return new ResponseEntity(reportDetails,HttpStatus.OK);
        commentService.addReport(reportDetails,id);
        return Helper.Successfully("report");
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity loadComments(@PathVariable long id,
                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "size", defaultValue = "10") int size,
                                       @RequestParam(value = "sortBy", defaultValue = "created") String[] sortBy,
                                       @RequestParam(value = "sortOrder", defaultValue = "") String sortOrder,
                                       @ActiveUser UserPrinciple userPrinciple){
        return  (sortOrder.equals("desc"))?
                new ResponseEntity(filterPostsBasic(commentService.loadCommentByPostId(id,  PageRequest.of(page,size, Sort.by(sortBy).descending()))),HttpStatus.OK)
                :new ResponseEntity(filterPostsBasic(commentService.loadCommentByPostId(id,  PageRequest.of(page,size, Sort.by(sortBy).descending()))),HttpStatus.OK);
    }
    private MappingJacksonValue filterPostsBasic(Object oject) {
        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
        simpleFilterProvider.addFilter(Constant.TBL_POSTS_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(Constant.POSTS_HASHTAGS));
        MappingJacksonValue wrapper = new MappingJacksonValue(oject);
        wrapper.setFilters(simpleFilterProvider);
        return wrapper;
    }

}
