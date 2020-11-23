package com.thduc.instafake.service;

import com.thduc.instafake.entity.Follows;
import com.thduc.instafake.entity.HashTags;
import com.thduc.instafake.entity.Posts;
import com.thduc.instafake.entity.User;
import com.thduc.instafake.exception.DataNotFoundException;
import com.thduc.instafake.repository.HashTagRepository;
import com.thduc.instafake.repository.PostRepository;
import com.thduc.instafake.repository.UserRepository;
import com.thduc.instafake.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Override
    public Posts uploadPost(Posts posts,Long uid) {
        User user = userRepository.findById(uid).orElseThrow(()->new DataNotFoundException("user","user",String.valueOf(uid)));
        List<String> tagNameList = Helper.hashTagsStringFromString(posts.getDescription());
        final Set<HashTags> hashTags = new HashSet<HashTags>();
        tagNameList.forEach(tag->{
            HashTags hagtag = (hashTagRepository.existsByTagName(tag))
                    ?hashTagRepository.findHashTagsByTagName(tag)
                    :hashTagRepository.save(new HashTags(tag));
            hashTags.add(hagtag);
        });
        posts.setUser(user);
        posts.setHashtags(hashTags);
        posts = postRepository.save(posts);
        return posts;
    }

    @Override
    public Page loadNewsFedd(User user) {
        return null;
    }

    @Override
    public Follows changeFollows(User to, User from) {
        return null;
    }
}
