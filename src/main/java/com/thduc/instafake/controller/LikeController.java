package com.thduc.instafake.controller;


import com.thduc.instafake.entity.Comments;
import com.thduc.instafake.entity.Likes;
import com.thduc.instafake.entity.UserPrinciple;
import com.thduc.instafake.security.ActiveUser;
import com.thduc.instafake.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/like")
public class LikeController {
    @Autowired
    LikeService likeService;

    @GetMapping(value = "/")
    public Likes changeLike(@RequestBody Likes like, @ActiveUser UserPrinciple userPrinciple){
        like.setAuthor(userPrinciple.getUser());
        return likeService.changeLike(like);
    }
//    @PostMapping(value = "/add")
//    public Likes addComment(@RequestBody Likes likes, @ActiveUser UserPrinciple userPrinciple){
//        likes.setAuthor(userPrinciple.getUser());
//        return likeService.changeLike(likes);
//    }
}
