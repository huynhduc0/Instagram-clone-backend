package com.thduc.instafake.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.thduc.instafake.constant.Constant;
import com.thduc.instafake.entity.*;
import com.thduc.instafake.exception.BadRequestException;
import com.thduc.instafake.exception.DataNotFoundException;
import com.thduc.instafake.exception.PermissionDenyException;
import com.thduc.instafake.model.PostWithLikes;
import com.thduc.instafake.repository.FollowRepository;
import com.thduc.instafake.repository.PostRepository;
import com.thduc.instafake.security.ActiveUser;
import com.thduc.instafake.service.LikeService;
import com.thduc.instafake.service.PostService;
import com.thduc.instafake.utils.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@CrossOrigin(origins = "*")
public class PostController {
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostService postService;
    @Autowired
    FollowRepository followRepository;

    @Autowired
    LikeService likeService;
    Logger logger = LoggerFactory.getLogger(PostController.class);

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addPost(@RequestBody Posts posts, @ActiveUser UserPrinciple userPrinciple){
        if(posts.getMedias() == null || posts.getMedias().size() == 0)
            return new ResponseEntity(new BadRequestException("Post need at least a media"),HttpStatus.BAD_REQUEST);
        return new ResponseEntity(uploadPostFilter(postService.uploadPost(posts,userPrinciple.getId())), HttpStatus.OK);
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    public ResponseEntity loadPost(@PathVariable Long id, @ActiveUser UserPrinciple userPrinciple){
        Posts posts = postRepository.findById(id).orElseThrow(()-> new DataNotFoundException("post","post",String.valueOf(id)));
        if (posts.isDeactive() && userPrinciple.getAuthorities().size() < 2) throw new PermissionDenyException("This post has expired or not share with you");
        return new ResponseEntity(filterPostsBasic(new PostWithLikes(posts,likeService.existLike(id, userPrinciple.getUser()))), HttpStatus.OK);
    }

    @RequestMapping(value = "/feed", method = RequestMethod.GET)
    public ResponseEntity loadFeed(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "") String[] sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "") String sortOrder,
            @ActiveUser UserPrinciple userPrinciple){
        return (sortOrder.equals("desc"))?
                new ResponseEntity(filterPostsBasic(postService.loadNewsFedd(userPrinciple.getId(), userPrinciple.getUser(),PageRequest.of(page,size, Sort.by(sortBy).descending()))),HttpStatus.OK)
                :new ResponseEntity(filterPostsBasic(postService.loadNewsFedd(userPrinciple.getId(),userPrinciple.getUser(), PageRequest.of(page,size, Sort.by(sortBy)))),HttpStatus.OK);
    }
    @RequestMapping(value = "/postRecommend", method = RequestMethod.GET)
    public ResponseEntity loadMoreRecommend(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "") String[] sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "") String sortOrder,
            @ActiveUser UserPrinciple userPrinciple){
        return (sortOrder.equals("desc"))?
                new ResponseEntity(filterPostsBasic(postService.loadRecommend(userPrinciple.getId(), userPrinciple.getUser(),PageRequest.of(page,size, Sort.by(sortBy).descending()))),HttpStatus.OK)
                :new ResponseEntity(filterPostsBasic(postService.loadRecommend(userPrinciple.getId(),userPrinciple.getUser(), PageRequest.of(page,size, Sort.by(sortBy)))),HttpStatus.OK);
    }
    @PostMapping(value = "/post/report/{id}")
    public ResponseEntity addReport(@PathVariable long id, @RequestBody ReportDetails reportDetails, @ActiveUser UserPrinciple userPrinciple){
        reportDetails.setReportUser(userPrinciple.getUser());
        postService.addReport(reportDetails,id);
        return Helper.Successfully("report");
    }
    @RequestMapping(value = "/popular", method = RequestMethod.GET)
    public ResponseEntity loadPopular(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "24") int size,
            @RequestParam(value = "sortBy", defaultValue = "numOfLikes") String[] sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "desc") String sortOrder,
            @ActiveUser UserPrinciple userPrinciple){
        return (sortOrder.equals("desc"))?
                new ResponseEntity(uploadPostFilter(postService.loadPopular(userPrinciple.getId(), userPrinciple.getUser(),PageRequest.of(page,size, Sort.by(sortBy).descending()))),HttpStatus.OK)
                :new ResponseEntity(uploadPostFilter(postService.loadPopular(userPrinciple.getId(),userPrinciple.getUser(), PageRequest.of(page,size, Sort.by(sortBy)))),HttpStatus.OK);
    }

    private MappingJacksonValue filterPostsBasic(Object oject) {
        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
        simpleFilterProvider.addFilter(Constant.TBL_POSTS_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(Constant.POSTS_HASHTAGS));
        simpleFilterProvider.addFilter(Constant.TBL_POSTS_FILTER, SimpleBeanPropertyFilter.filterOutAllExcept("tagname"));
        MappingJacksonValue wrapper = new MappingJacksonValue(oject);
        wrapper.setFilters(simpleFilterProvider);
        return wrapper;
    }

    private MappingJacksonValue uploadPostFilter(Object oject) {
        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
        simpleFilterProvider.addFilter(Constant.TBL_POSTS_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(Constant.POSTS_HASHTAGS));
        MappingJacksonValue wrapper = new MappingJacksonValue(oject);
        wrapper.setFilters(simpleFilterProvider);
        return wrapper;
    }

}
