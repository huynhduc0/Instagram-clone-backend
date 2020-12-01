package com.thduc.instafake.controller;

import com.thduc.instafake.entity.Stories;
import com.thduc.instafake.entity.User;
import com.thduc.instafake.entity.UserPrinciple;
import com.thduc.instafake.security.ActiveUser;
import com.thduc.instafake.service.FollowService;
import com.thduc.instafake.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RequestMapping(value = "/story")
@RestController
public class StoryController {

    @Autowired
    StoryService storyService;
    @Autowired
    FollowService followService;

    @PostMapping(value = "/add")
    public Stories addStories(@RequestBody Stories story, @ActiveUser UserPrinciple userPrinciple){
        story.setAuthor(userPrinciple.getUser());
        return storyService.createStories(story);
    }
    @GetMapping(value = "/{id}")
    public Stories viewStories(@PathVariable(value = "id") long id, @ActiveUser UserPrinciple userPrinciple){
        return storyService.viewStories(id,userPrinciple.getUser());
    }
    @GetMapping(value = "/")
    public ResponseEntity viewStories(@RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "size", defaultValue = "10") int size,
                                      @RequestParam(value = "sortBy", defaultValue = "") String[] sortBy,
                                      @RequestParam(value = "sortOrder", defaultValue = "") String sortOrder,
                                      @ActiveUser UserPrinciple userPrinciple){
        return (sortOrder.equals("desc"))?
                new ResponseEntity(storyService.loadStoriesList(userPrinciple.getUser(), PageRequest.of(page,size, Sort.by(sortBy).descending())), HttpStatus.OK)
                :new ResponseEntity(storyService.loadStoriesList(userPrinciple.getUser(), PageRequest.of(page,size, Sort.by(sortBy))), HttpStatus.OK);
    }

}
