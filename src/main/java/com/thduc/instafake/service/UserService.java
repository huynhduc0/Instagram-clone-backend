package com.thduc.instafake.service;

import com.thduc.instafake.constant.Constant;
import com.thduc.instafake.entity.Posts;
import com.thduc.instafake.entity.User;
import com.thduc.instafake.exception.BadRequestException;
import com.thduc.instafake.exception.DataNotFoundException;
import com.thduc.instafake.model.UserWithFollow;
import com.thduc.instafake.repository.FollowRepository;
import com.thduc.instafake.repository.RoleRepository;
import com.thduc.instafake.repository.UserRepository;
import com.thduc.instafake.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.*;

@Service
public class UserService implements UserServiceImpl{
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    FollowService followService;
    @Autowired
    FollowRepository followRepository;
    @Autowired
    RoleRepository roleRepository;


    public User getUserById(long id, long myId) {
      return userRepository.findById(id)
             .orElseThrow(()-> new DataNotFoundException("user","user",String.valueOf(id)));
    }
    public int checkFollowStatus(long id, long myid){
        if (id == myid) return -1;
        else
            return (followRepository.existsByFrom_IdAndTo_Id(myid,id)?1:0);
    }

    @Override
    public User addUser(User user) {
        user.setAvatar((user.getAvatar() == null)?null:FileUtils.saveFileToStorage("avatars",user.getUsername(),user.getAvatar(),true));
        user.setCover((user.getCover() == null)?null:FileUtils.saveFileToStorage("user",user.getUsername(),user.getCover(),false));
        HashSet s = new HashSet();
        s.add( roleRepository.findById(2).get());
        user.setRoles(s);
        User user1 = userRepository.save(user);
        followService.changeFollows(user1,user1);
        user1.setNumOfFollowers(1);
        user1.setNumOfFollowings(1);
        return user1;
    }

    @Override
    public Page findOtherUser(Long id, Pageable pageable) {
        return userRepository.findUsersByIdNot(id,pageable);
    }

    public List<UserRepository.UserWithFollow> findFollow(Long id, int page) {
        return userRepository.findUserWithFollowStatus(id, Constant.NUM_STEP_DATA,Constant.NUM_STEP_DATA * page);
    }

    public List<UserRepository.UserWithFollow> searchByNameFollow (Long id, int page, String username) {
        return userRepository.findUserWithFollowStatusByUsername(id,username, Constant.NUM_STEP_DATA,Constant.NUM_STEP_DATA * page);
    }

    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
    public User checkLogin(String u, String pass){
        User user = userRepository.findUserByUsername(u);
        if(user == null ) return null;
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
