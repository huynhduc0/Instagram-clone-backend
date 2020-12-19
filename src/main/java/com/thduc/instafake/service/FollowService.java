package com.thduc.instafake.service;

import com.thduc.instafake.constant.Constant;
import com.thduc.instafake.constant.NotifcationType;
import com.thduc.instafake.entity.Follows;
import com.thduc.instafake.entity.User;
import com.thduc.instafake.exception.DataNotFoundException;
import com.thduc.instafake.repository.FollowRepository;
import com.thduc.instafake.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class FollowService implements FollowServiceImpl {
    @Autowired
    FollowRepository followRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationService notificationService;

    @Override
    public boolean changeFollows(User from, User to) {
        try{
            to = userRepository.findById(to.getId())
                    .orElseThrow(()-> new DataNotFoundException("user","user","to user"));
            Follows follows = followRepository.findByFromAndTo(from,to);
            if (follows!=null) {
                followRepository.delete(follows);
                from.setNumOfFollowings(from.getNumOfFollowings() -1);
                to.setNumOfFollowers(from.getNumOfFollowers() -1);
                userRepository.save(from);
                userRepository.save(to);
            }
            else{
                followRepository.save(new Follows(from,to));
                from.setNumOfFollowings(from.getNumOfFollowings() +1);
                to.setNumOfFollowers(from.getNumOfFollowers() +1);
                userRepository.save(from);
                userRepository.save(to);
                notificationService.addNotification(from, to, Constant.FOLLOW_NOTI_MESSAGE,
                        NotifcationType.FOLLOW, from.getId());
            }
            return true;
        }catch(Exception e){
            throw new DataNotFoundException("user","user you find",String.valueOf(to.getId())+e.getMessage());
        }

    }
    public List<FollowRepository.UserWithFollow> getFollowings(long id, long myid, int limit, int offset){
        return followRepository.viewFollowingsUser(id,myid);
    }

    @Override
    public List<FollowRepository.UserWithFollow> getFollowers(long id, long myid, int limit, int offset){
        return followRepository.viewFollowersUser(id,myid);
    }
}
