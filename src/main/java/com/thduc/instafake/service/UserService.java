package com.thduc.instafake.service;

import com.thduc.instafake.entity.User;
import com.thduc.instafake.exception.BadRequestException;
import com.thduc.instafake.exception.DataNotFoundException;
import com.thduc.instafake.model.UserWithFollow;
import com.thduc.instafake.repository.UserRepository;
import com.thduc.instafake.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserServiceImpl{
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserById(Long id) {
     return userRepository.findById(id)
             .orElseThrow(()-> new DataNotFoundException("user","user",String.valueOf(id)));
    }

    @Override
    public User addUser(User user) {
        user.setAvatar(FileUtils.saveFileToStorage(String.valueOf(user.getId()),user.getUsername(),user.getAvatar()));
        return userRepository.save(user);
    }

    @Override
    public Page findOtherUser(Long id, Pageable pageable) {
        return userRepository.findUsersByIdNot(id,pageable);
    }

    public Set<UserWithFollow> findFollow(Long id, Pageable pageable) {
        return userRepository.findUserWithFollowStatus(id);
//        return userRepository.findUserWithFollowStatus(id,pageable);
    }


    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
    public User checkLogin(String u, String pass){
        User user = userRepository.findUserByUsername(u);
         return passwordEncoder.matches(pass,user.getPassword())? user: null;
    }


    public List<SimpleGrantedAuthority> getAuthority(User user) {
        User user1 = userRepository.findUserByUsername(user.getUsername());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user1.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRolename()));
        });
        return authorities;
    }

    public User updateUserById(Long id, User user1) {
        if(user1.getUsername().length() < 25) {
            User user = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("user", "id", String.valueOf(id)));
            if(user1.getUsername() == null || user1.getUsername() == "") user1.setUsername(user.getUsername());
            if(user1.getPassword() == null || user1.getPassword()== "") user1.setPassword(user.getPassword());
            user = user1;
            user.setId(id);
            try {
                userRepository.save(user);
            } catch (DataIntegrityViolationException ex) {
                throw new BadRequestException("EXISTED_USERNAME");
            }
            return user;
        }
        else throw new BadRequestException("INVALID_USER_FORM");
    }

    boolean validateUserform(User user){
        int lenUsername = user.getUsername().length();
        int lenPassword = user.getPassword().length();
        if(lenUsername > 25 || lenPassword < 6){
            return false;
        }
        return true;
    }

}
