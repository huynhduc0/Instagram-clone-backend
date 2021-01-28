package com.thduc.instafake.service;

import com.thduc.instafake.entity.HashTags;
import com.thduc.instafake.entity.Posts;
import com.thduc.instafake.repository.HashTagRepository;
import com.thduc.instafake.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class HashTagService implements HashTagServiceImpl {
    @Autowired
    HashTagRepository hashTagRepository;
    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Override
    public Set<Posts> loadPostByTagName(String tagname) {
        List<HashTags> hashTags = hashTagRepository.findAllByTagNameLike(tagname);
//        return hashTagRepository.findHashTagsByTagName(tagname);
        return postRepository.findAllByHashtagsIn(hashTags);
    }

}
