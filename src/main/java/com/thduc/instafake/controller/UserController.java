package com.thduc.instafake.controller;


import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.thduc.instafake.constant.Constant;
import com.thduc.instafake.constant.NotifcationType;
import com.thduc.instafake.entity.Follows;
import com.thduc.instafake.entity.User;
import com.thduc.instafake.entity.UserPrinciple;
import com.thduc.instafake.exception.BadRequestException;
import com.thduc.instafake.exception.JWTException;
import com.thduc.instafake.model.AccessTokenBody;
import com.thduc.instafake.model.LoginBody;
import com.thduc.instafake.repository.UserRepository;
import com.thduc.instafake.security.ActiveUser;
import com.thduc.instafake.service.*;
import com.thduc.instafake.utils.Helper;
import net.minidev.json.JSONObject;
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

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private FollowService followService;

    @Autowired
    private PostService postService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    Logger logger = LoggerFactory.getLogger(PostController.class);

     @GetMapping(value = "/user/{id}")
    public ResponseEntity findUserById(@PathVariable Long id, @ActiveUser UserPrinciple userPrinciple){
         HashMap hashMap = new HashMap();
         hashMap.put("user",filterFollowingOnly(userService.getUserById(id,userPrinciple.getId())).getValue());
         hashMap.put("followStatus",userService.checkFollowStatus(id, userPrinciple.getId()));
         return new ResponseEntity(hashMap,HttpStatus.OK);
     }

    @GetMapping(value = "/user/{id}/post")
    public ResponseEntity findUserPostById(
            @PathVariable Long id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "9") int size,
            @RequestParam(value = "sortBy", defaultValue = "created") String[] sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "desc") String sortOrder){
        return (sortOrder.equals("desc"))?
                new ResponseEntity(filterPostsBasic(postService.loadPostByUid(id, PageRequest.of(page,size, Sort.by(sortBy).descending()))),HttpStatus.OK)
                :new ResponseEntity(filterPostsBasic(postService.loadPostByUid(id, PageRequest.of(page,size, Sort.by(sortBy)))),HttpStatus.OK);
    }

     @PostMapping (value = "/register")
    public User register(@RequestBody User user){
         user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.addUser(user);
     }
    @PostMapping (value = "/google")
    public ResponseEntity ggLogin(@RequestBody AccessTokenBody object) throws IOException, GeneralSecurityException {
         User user = userService.googleLogin(object);
         HashMap hashMap = new HashMap();
        String result = jwtService.generateTokenLogin(user);
        String role = jwtService.getRole(result);
        hashMap.put("token", result);
        hashMap.put("role", role);
        hashMap.put("user", user);
        return new ResponseEntity(hashMap, HttpStatus.OK);
    }

    @GetMapping(value = "/user")
     public ResponseEntity findOtherUser(@RequestParam int page,@ActiveUser UserPrinciple userPrinciple){
//        return new ResponseEntity(filterFollowingOnly(userService.findOtherUser(userPrinciple.getId(),PageRequest.of(0,2))),HttpStatus.OK);
        return new ResponseEntity(userService.findFollow(userPrinciple.getId(),page),HttpStatus.OK);
    }



    @GetMapping(value = "/user/search")
    public ResponseEntity searchOtherUserbyName(@RequestParam int page,@RequestParam String username,@ActiveUser UserPrinciple userPrinciple){
        return new ResponseEntity(userService.searchByNameFollow(userPrinciple.getId(),page,username),HttpStatus.OK);
    }
//    @GetMapping(value = "/user")
//    public ResponseEntity findOtherUser(@ActiveUser UserPrinciple userPrinciple){
////        return new ResponseEntity(filterFollowingOnly(userService.findOtherUser(userPrinciple.getId(),PageRequest.of(0,2))),HttpStatus.OK);
//        return new ResponseEntity(filterFollowingOnly(userService.findFollow(userPrinciple.getId(),PageRequest.of(0,2))),HttpStatus.OK);
//
//    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @CrossOrigin
    @Transactional
    public ResponseEntity<String> login(@RequestBody LoginBody loginBody) {
        HashMap hashMap = new HashMap();
        if (loginBody.getPassword()==null||  loginBody.getUsername()==null)
            return new ResponseEntity("SOME DATA IS MISSING", HttpStatus.BAD_REQUEST);
        User checkedUser = userService.checkLogin(loginBody.getUsername(), loginBody.getPassword());
        if(checkedUser != null){
           String result = jwtService.generateTokenLogin(checkedUser);
            String role = jwtService.getRole(result);
            if(loginBody.getPushToken()!=null)
                userService.updateToken(loginBody.getPushToken(),loginBody.getDeviceType(),loginBody.getDeviceId(),checkedUser);
            hashMap.put("token", result);
            hashMap.put("role", role);
            hashMap.put("user", checkedUser);
            return new ResponseEntity(hashMap, HttpStatus.OK);
        } else throw new BadRequestException("WRONG_USERNAME_PASSWORD");
    }

    @RequestMapping(value = "/cleartoken", method = RequestMethod.POST)
    public ResponseEntity logout(@RequestBody LoginBody loginBody, @ActiveUser UserPrinciple userPrinciple){
         userService.logout(userPrinciple.getUser(),loginBody.getDeviceId());
        return Helper.Successfully("log out");
    }


    @Transactional
    @PostMapping(value = "/follow")
    public ResponseEntity changeFollow(@RequestBody User toUser, @ActiveUser UserPrinciple userPrinciple){
         User fromUser = userPrinciple.getUser();
         followService.changeFollows(fromUser,toUser);
         return new ResponseEntity(HttpStatus.OK);
    }
    private MappingJacksonValue filterFollowingOnly(Object oject) {
        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
        simpleFilterProvider.addFilter(Constant.TBL_FOLLOWS_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(Constant.FOLLOW_FROM,Constant.FOLLOW_TO));
        MappingJacksonValue wrapper = new MappingJacksonValue(oject);
        wrapper.setFilters(simpleFilterProvider);
        return wrapper;
    }

    private MappingJacksonValue filterPostsBasic(Object oject) {
        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
        simpleFilterProvider.addFilter(Constant.TBL_POSTS_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(Constant.POSTS_HASHTAGS));
        MappingJacksonValue wrapper = new MappingJacksonValue(oject);
        wrapper.setFilters(simpleFilterProvider);
        return wrapper;
    }
}
