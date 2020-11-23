package com.thduc.instafake.service;

import com.thduc.instafake.entity.Follows;
import com.thduc.instafake.entity.User;
import com.thduc.instafake.exception.DataNotFoundException;
import com.thduc.instafake.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;

@Service
public class FollowService implements FollowServiceImpl {
    @Autowired
    FollowRepository followRepository;

    @Override
    public boolean changeFollows(User from, User to) {
        try{
            Follows follows = followRepository.findByFromAndTo(from,to);
            if (follows!=null)
                followRepository.delete(follows);
            else followRepository.save(new Follows(from,to));
            return true;
        }catch(Exception e){
            throw new DataNotFoundException("user","user you find",String.valueOf(to.getId()));
        }

    }
}
