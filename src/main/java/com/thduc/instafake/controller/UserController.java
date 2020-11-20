package com.thduc.instafake.controller;


import com.thduc.instafake.entity.User;
import com.thduc.instafake.exception.JWTException;
import com.thduc.instafake.repository.UserRepository;
import com.thduc.instafake.service.JWTService;
import com.thduc.instafake.service.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTService jwtService;
     @GetMapping(value = "/user")
    public String findUserById(@PathVariable Long id){
         return String.valueOf(id);
     }
     @PostMapping (value = "/register")
    public User register(@RequestBody User user ){
        return userService.addUser(user);
     }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @CrossOrigin
    @Transactional
    public ResponseEntity<String> login(@RequestBody User user) {
        String result = "";
        HttpStatus httpStatus = null;
        String role = "";
        JSONObject json  = new JSONObject();
        if (user.getPassword()==null||  user.getUsername()==null)
            return new ResponseEntity<String>("SOME DATA IS MISSING", HttpStatus.BAD_REQUEST);
        if (userService.checkLogin(user.getUsername(),user.getPassword())) {
            long userId = userService.findByUsername(user.getUsername()).getId();
            result = jwtService.generateTokenLogin(user);
            role = jwtService.getRole(result);
            json.put("token", result);
            json.put("role", role);
            json.put("username", user.getUsername());
            httpStatus = HttpStatus.OK;
        } else throw new JWTException(HttpStatus.UNAUTHORIZED, "WRONG_USERNAME_PASSWORD");

        return new ResponseEntity<String>(json.toJSONString(), httpStatus);
    }
}
