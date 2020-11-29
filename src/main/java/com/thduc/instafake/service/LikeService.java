package com.thduc.instafake.service;

import com.thduc.instafake.entity.Likes;
import com.thduc.instafake.entity.User;
import com.thduc.instafake.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService implements LikeServiceImpl {
    @Autowired
    LikeRepository likeRepository;
    @Override
    public Likes changeLike(Likes likes) {
        if(likeRepository.existsByAuthorAndPost(likes.getAuthor(),likes.getPost())) {
            likeRepository.delete(likes);
            return null;
        }
        else
            return likeRepository.save(likes);
    }
}
