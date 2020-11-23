package com.thduc.instafake.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.thduc.instafake.constant.Constant;
import com.thduc.instafake.entity.Posts;
import com.thduc.instafake.entity.User;
import com.thduc.instafake.entity.UserPrinciple;
import com.thduc.instafake.repository.PostRepository;
import com.thduc.instafake.security.ActiveUser;
import com.thduc.instafake.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostService postService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    Logger logger = LoggerFactory.getLogger(PostController.class);
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addPost(@RequestBody Posts posts, @ActiveUser UserPrinciple userPrinciple){
        return new ResponseEntity(filterPostsBasic(postService.uploadPost(posts,userPrinciple.getId())), HttpStatus.OK);
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    public ResponseEntity addPost(@PathVariable Long id, @ActiveUser UserPrinciple userPrinciple){
        return new ResponseEntity(filterPostsBasic(postRepository.findById(id)), HttpStatus.OK);
    }

//    @RequestMapping(value = "/feed", method = RequestMethod.GET)
//    public ResponseEntity loadFeed(@ActiveUser UserPrinciple userPrinciple){
//
//    }

    private MappingJacksonValue filterPostsBasic(Object oject) {
        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
        simpleFilterProvider.addFilter(Constant.TBL_POSTS_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(Constant.POSTS_HASHTAGS));
//                .addFilter(Constant.TBL_USER_FILTER, SimpleBeanPropertyFilter.filterOutAllExcept(Constant.TBL_USER_ID, Constant.TBL_USER_FULLNAME))
//                .addFilter(Constant.TBL_POSITION_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(Constant.TBL_POSITION_INTERVIEWEES));
        MappingJacksonValue wrapper = new MappingJacksonValue(oject);
        wrapper.setFilters(simpleFilterProvider);
        return wrapper;
    }
}
