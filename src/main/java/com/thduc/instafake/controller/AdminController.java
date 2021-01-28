package com.thduc.instafake.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.thduc.instafake.constant.Constant;
import com.thduc.instafake.entity.Posts;
import com.thduc.instafake.entity.ReportPosts;
import com.thduc.instafake.exception.BadRequestException;
import com.thduc.instafake.exception.DataNotFoundException;
import com.thduc.instafake.repository.PostRepository;
import com.thduc.instafake.repository.ReportCommentRepository;
import com.thduc.instafake.repository.ReportPostRepository;
import com.thduc.instafake.repository.UserRepository;
import com.thduc.instafake.service.UserService;
import com.thduc.instafake.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "/admin")
@CrossOrigin(origins = "*")
@RestController
public class AdminController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ReportPostRepository reportPostRepository;
    @Autowired
    ReportCommentRepository reportCommentRepository;
    @Autowired
    PostRepository postRepository;

    @GetMapping(value = "user")
    public ResponseEntity getAllUser(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "") String[] sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "") String sortOrder
    ){
        return  (sortOrder.equals("desc"))?
                new ResponseEntity(userRepository.findAll(PageRequest.of(page,size, Sort.by(sortBy).descending())), HttpStatus.OK)
                : new ResponseEntity(userRepository.findAll(PageRequest.of(page,size, Sort.by(sortBy))), HttpStatus.OK);
    }
    @GetMapping(value = "/post")
    public ResponseEntity getAllport( @RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "size", defaultValue = "10") int size,
                                      @RequestParam(value = "sortBy", defaultValue = "") String[] sortBy,
                                      @RequestParam(value = "sortOrder", defaultValue = "") String sortOrder){
        return (sortOrder.equals("desc"))?
                new ResponseEntity(uploadPostFilter(postRepository.findAll(PageRequest.of(page,size, Sort.by(sortBy).descending()))), HttpStatus.OK)
                : new ResponseEntity(uploadPostFilter(postRepository.findAll(PageRequest.of(page,size, Sort.by(sortBy)))), HttpStatus.OK);
    }
    @PostMapping(value = "/post/handle")
    public ResponseEntity handPo(@RequestBody Posts posts){
        Posts posts1 = postRepository.findById(posts.getId()).orElseThrow(()-> new DataNotFoundException("posts","post", String.valueOf(posts.getId())));
        posts1.setDeactive(!posts1.isDeactive());
        return new ResponseEntity(uploadPostFilter( postRepository.save(posts1)),HttpStatus.OK);
    }
    @GetMapping(value = "report/post")
    public ResponseEntity getAllReport(@RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "size", defaultValue = "10") int size,
                                       @RequestParam(value = "sortBy", defaultValue = "") String[] sortBy,
                                       @RequestParam(value = "sortOrder", defaultValue = "") String sortOrder){
        return (sortOrder.equals("desc"))?
                new ResponseEntity(uploadPostFilter(reportPostRepository.findAll(PageRequest.of(page,size, Sort.by(sortBy).descending()))), HttpStatus.OK)
                :  new ResponseEntity(uploadPostFilter(reportPostRepository.findAll(PageRequest.of(page,size, Sort.by(sortBy)))), HttpStatus.OK);
    }

    @PostMapping(value = "report/post/handle")
    public ResponseEntity eactivePost(@RequestBody ReportPosts reportPosts){
        ReportPosts reportPosts1 = reportPostRepository.findById(reportPosts.getId()).orElseThrow(()->new DataNotFoundException("posts","post", String.valueOf(reportPosts.getId())));;
        Posts posts1 = reportPosts1.getPosts();
        posts1.setDeactive(!posts1.isDeactive());
        postRepository.save(posts1);
        reportPosts1.setClosed(true);
        return new ResponseEntity(uploadPostFilter(reportPostRepository.save(reportPosts1)),HttpStatus.OK);
    }
    private MappingJacksonValue uploadPostFilter(Object oject) {
        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
        simpleFilterProvider.addFilter(Constant.TBL_POSTS_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(Constant.POSTS_HASHTAGS));
        MappingJacksonValue wrapper = new MappingJacksonValue(oject);
        wrapper.setFilters(simpleFilterProvider);
        return wrapper;
    }
}
