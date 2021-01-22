package com.thduc.instafake.service;

import com.thduc.instafake.constant.Constant;
import com.thduc.instafake.constant.NotifcationType;
import com.thduc.instafake.entity.Likes;
import com.thduc.instafake.entity.Medias;
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
    public int changeLike(Likes likes) {
        Posts posts = postRepository.findById(likes.getPost().getId())
                .orElseThrow(()->new DataNotFoundException("post","post",String.valueOf(likes.getPost().getId())));
        if(likeRepository.existsByAuthorAndPost_Id(likes.getAuthor(),likes.getPost().getId())) {
            Likes likes1 = likeRepository.findDistinctFirstByAuthorAndAndPost_Id(likes.getAuthor(),likes.getPost().getId());
            likeRepository.delete(likes1);
            posts.setNumOfLikes(likeRepository.countByPost_Id(posts.getId()));
            postRepository.save(posts);
            return posts.getNumOfLikes();
        }
        else {
            likes.setPost(posts);
            long id = likes.getPost().getId();
            Likes like = likeRepository.save(likes);
            Medias medias = posts.getMedias().iterator().next();
            final String imageUrl = medias.getMedia_url();
            notificationService.addNotification(likes.getAuthor(), like.getPost().getUser(),
                    Constant.LIKE_NOTI_MESSAGE, NotifcationType.LIKE, likes.getPost().getId(), imageUrl);
            posts.setNumOfLikes(likeRepository.countByPost_Id(posts.getId()));
            postRepository.save(posts);
            return posts.getNumOfLikes();
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
