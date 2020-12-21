package com.thduc.instafake.controller;

import com.thduc.instafake.entity.UserPrinciple;
import com.thduc.instafake.security.ActiveUser;
import com.thduc.instafake.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class FollowController {
    @Autowired
    FollowService followService;

        @GetMapping(value = "/followings/{id}")
    public ResponseEntity getFollowingsByUserId(
            @PathVariable long id,
            @ActiveUser UserPrinciple userPrinciple,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size){
        return new ResponseEntity(followService.getFollowings(id, userPrinciple.getId(), page, page*size), HttpStatus.OK);
    }
    @GetMapping(value = "/followers/{id}")
    public ResponseEntity getFollowersByUserId(
            @PathVariable long id,
            @ActiveUser UserPrinciple userPrinciple,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size){
        return new ResponseEntity(followService.getFollowers(id, userPrinciple.getId(), page, page*size), HttpStatus.OK);
    }

}
