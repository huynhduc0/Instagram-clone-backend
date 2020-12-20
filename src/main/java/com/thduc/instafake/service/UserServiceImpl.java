package com.thduc.instafake.service;

import com.thduc.instafake.entity.Posts;
import com.thduc.instafake.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface UserServiceImpl {

//    Page<User> getAll(int page, int size, long id);

    User addUser(User user);
    <Set> Page findOtherUser(Long id, Pageable pageable);
//    void deleteUser(Long id);

}
