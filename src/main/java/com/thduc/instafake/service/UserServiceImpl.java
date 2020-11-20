package com.thduc.instafake.service;

import com.thduc.instafake.entity.User;

public interface UserServiceImpl {

    User getUserById(Long id);

//    Page<User> getAll(int page, int size, long id);

    User addUser(User user);

//    void deleteUser(Long id);

}
