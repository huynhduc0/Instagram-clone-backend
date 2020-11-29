package com.thduc.instafake.service;

import com.thduc.instafake.entity.*;
import com.thduc.instafake.exception.DataNotFoundException;
import com.thduc.instafake.repository.FollowRepository;
import com.thduc.instafake.repository.HashTagRepository;
import com.thduc.instafake.repository.PostRepository;
import com.thduc.instafake.repository.UserRepository;
import com.thduc.instafake.utils.FileUtils;
import com.thduc.instafake.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PostService implements PostServiceImpl{
    @Autowired
    PostRepository postRepository;
    @Autowired
    HashTagRepository hashTagRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FollowRepository followRepository;

    @Override
    public Posts uploadPost(Posts posts,Long uid) {
        User user = userRepository.findById(uid).orElseThrow(()->new DataNotFoundException("user","user",String.valueOf(uid)));
        List<String> tagNameList = Helper.hashTagsStringFromString(posts.getDescription());
        final Set<HashTags> hashTags = (posts.getHashtags() != null)? posts.getHashtags():  new HashSet<HashTags>();
        tagNameList.forEach(tag->{
            HashTags hagtag = (hashTagRepository.existsByTagName(tag))
                    ?hashTagRepository.findHashTagsByTagName(tag)
                    :hashTagRepository.save(new HashTags(tag));
            hashTags.add(hagtag);
        });
        posts.getMedias().stream().map(medias -> {
            return new Medias(medias.getMedia_type(),FileUtils.saveFileToStorage(String.valueOf(uid),"",medias.getMedia_url(),false));
        });
        posts.setUser(user);
        posts.setHashtags(hashTags);
        posts = postRepository.save(posts);
        return posts;
    }

    @Override
    public Page loadNewsFedd(Long id, Pageable pageable) {
       List<User> users = followRepository.LoadFollowing(id);
        return postRepository.findAllByUserIn(users,pageable);
    }

    @Override
    public Follows changeFollows(User to, User from) {
        return null;
    }
}
