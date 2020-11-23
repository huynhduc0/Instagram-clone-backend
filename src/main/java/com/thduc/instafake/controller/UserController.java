package com.thduc.instafake.controller;


import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.thduc.instafake.constant.Constant;
import com.thduc.instafake.entity.Follows;
import com.thduc.instafake.entity.User;
import com.thduc.instafake.entity.UserPrinciple;
import com.thduc.instafake.exception.JWTException;
import com.thduc.instafake.repository.UserRepository;
import com.thduc.instafake.security.ActiveUser;
import com.thduc.instafake.service.FollowService;
import com.thduc.instafake.service.JWTService;
import com.thduc.instafake.service.UserService;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private FollowService followService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    Logger logger = LoggerFactory.getLogger(PostController.class);

     @GetMapping(value = "/user/{id}")
    public ResponseEntity findUserById(@PathVariable Long id){
         return new ResponseEntity(filterFollowingOnly(userService.getUserById(id)),HttpStatus.OK);
     }

     @PostMapping (value = "/register")
    public User register(@RequestBody User user){
         user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.addUser(user);
     }

    @GetMapping(value = "/user")
     public ResponseEntity findOtherUser(@ActiveUser UserPrinciple userPrinciple){
//        return new ResponseEntity(filterFollowingOnly(userService.findOtherUser(userPrinciple.getId(),PageRequest.of(0,2))),HttpStatus.OK);
        return new ResponseEntity(userService.findFollow(userPrinciple.getId(),PageRequest.of(0,2)),HttpStatus.OK);
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
    public ResponseEntity<String> login(@RequestBody User user) {
        String result = "";
        HttpStatus httpStatus = null;
        String role = "";
        JSONObject json  = new JSONObject();
        if (user.getPassword()==null||  user.getUsername()==null)
            return new ResponseEntity("SOME DATA IS MISSING", HttpStatus.BAD_REQUEST);
        User checkedUser = userService.checkLogin(user.getUsername(), user.getPassword());
        if(checkedUser != null){
            long userId = userService.findByUsername(user.getUsername()).getId();
            result = jwtService.generateTokenLogin(user);
            role = jwtService.getRole(result);
            json.put("token", result);
            json.put("role", role);
            json.put("username", user.getUsername());
            httpStatus = HttpStatus.OK;
        } else throw new JWTException(HttpStatus.UNAUTHORIZED, "WRONG_USERNAME_PASSWORD");
        return new ResponseEntity(json.toJSONString(), httpStatus);
    }
    @PostMapping(value = "/follow")
    public ResponseEntity changeFollow(@RequestBody User toUser, @ActiveUser UserPrinciple userPrinciple){
         User fromUser = userService.findByUsername(userPrinciple.getUsername());
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
}
