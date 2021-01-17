package com.thduc.instafake.controller;


import com.thduc.instafake.entity.Comments;
import com.thduc.instafake.entity.Likes;
import com.thduc.instafake.entity.UserPrinciple;
import com.thduc.instafake.security.ActiveUser;
import com.thduc.instafake.service.LikeService;
import com.thduc.instafake.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/likes")
public class LikeController {
    @Autowired
    LikeService likeService;

    @PostMapping(value = "/")
    public ResponseEntity changeLike(@RequestBody Likes like, @ActiveUser UserPrinciple userPrinciple){
        like.setAuthor(userPrinciple.getUser());
        HashMap hashMap = new HashMap();
        hashMap.put("numOfLikes", likeService.changeLike(like));
        hashMap.put("message", "sucesss");
        return (new ResponseEntity(hashMap,HttpStatus.OK));
    }

    @GetMapping(value = "/islike/{idpost}")
    public ResponseEntity isLike(@PathVariable long idpost, @ActiveUser UserPrinciple userPrinciple){
        return new ResponseEntity(likeService.existLike(idpost,userPrinciple.getUser()),HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity listOfLike(@PathVariable long id, @ActiveUser UserPrinciple userPrinciple){
        return new ResponseEntity(likeService.loadListOfLike(id, userPrinciple.getId()),HttpStatus.OK);
    }

//    @PostMapping(value = "/add")
//    public Likes addComment(@RequestBody Likes likes, @ActiveUser UserPrinciple userPrinciple){
//        likes.setAuthor(userPrinciple.getUser());
//        return likeService.changeLike(likes);
//    }
}
