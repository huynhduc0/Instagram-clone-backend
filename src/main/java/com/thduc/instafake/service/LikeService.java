package com.thduc.instafake.service;

import com.thduc.instafake.constant.Constant;
import com.thduc.instafake.constant.NotifcationType;
import com.thduc.instafake.entity.Likes;
import com.thduc.instafake.entity.Posts;
import com.thduc.instafake.entity.User;
import com.thduc.instafake.exception.DataNotFoundException;
import com.thduc.instafake.repository.LikeRepository;
import com.thduc.instafake.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService implements LikeServiceImpl {
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    NotificationService notificationService;
    @Autowired
    PostRepository postRepository;

    @Override
    public boolean changeLike(Likes likes) {
        Posts posts = postRepository.findById(likes.getPost().getId())
                .orElseThrow(()->new DataNotFoundException("post","post",String.valueOf(likes.getPost().getId())));
        if(likeRepository.existsByAuthorAndPost_Id(likes.getAuthor(),likes.getPost().getId())) {
            posts.setNumOfLikes(posts.getNumOfLikes() -1);
            Likes likes1 = likeRepository.findDistinctFirstByAuthorAndAndPost_Id(likes.getAuthor(),likes.getPost().getId());
            postRepository.save(posts);
            likeRepository.delete(likes1);
            return false;
        }
        else {
            likes.setPost(posts);
            Likes like = likeRepository.save(likes);
            notificationService.addNotification(likes.getAuthor(), like.getPost().getUser(),
                    Constant.LIKE_NOTI_MESSAGE, NotifcationType.LIKE, likes.getPost().getId());
            posts.setNumOfLikes(posts.getNumOfLikes() +1);
            postRepository.save(posts);
            return true;
        }
    }
    public boolean existLike(long id, User user){
        return likeRepository.existsByAuthorAndPost_Id(user,id);
    }
    @Override
    public List<LikeRepository.UserWithFollow> loadListOfLike(long id, long uid){
        postRepository.findById(id).orElseThrow(()->new DataNotFoundException("post","post",String.valueOf(id)));
        return likeRepository.loadListOfLike(id,uid);
    }
}
