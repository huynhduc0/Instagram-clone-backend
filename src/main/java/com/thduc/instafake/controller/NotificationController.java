package com.thduc.instafake.controller;

import com.thduc.instafake.entity.UserPrinciple;
import com.thduc.instafake.security.ActiveUser;
import com.thduc.instafake.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
    @Autowired
    NotificationService notificationService;
    @GetMapping(value = "/notification")
    public ResponseEntity loadNotifi(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "created") String[] sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "desc") String sortOrder,
            @ActiveUser UserPrinciple userPrinciple){
        return  (sortOrder.equals("desc"))?
                new ResponseEntity(notificationService.loadNoti(userPrinciple.getUser(),  PageRequest.of(page,size, Sort.by(sortBy).descending())),HttpStatus.OK)
                :new ResponseEntity(notificationService.loadNoti(userPrinciple.getUser(),  PageRequest.of(page,size, Sort.by(sortBy))),HttpStatus.OK);
    }

}
