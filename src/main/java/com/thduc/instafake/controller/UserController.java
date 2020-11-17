package com.thduc.instafake.controller;


import com.thduc.instafake.repository.UserRepostitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserRepostitory userRepostitory;
     @RequestMapping(value = "/")
    public String main(){
         return "Hello worlds";
     }
}
