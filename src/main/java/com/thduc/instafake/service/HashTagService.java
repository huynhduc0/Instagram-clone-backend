package com.thduc.instafake.service;

import com.thduc.instafake.entity.HashTags;
import com.thduc.instafake.entity.Posts;
import com.thduc.instafake.repository.HashTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class HashTagService implements HashTagServiceImpl {
    @Autowired
    HashTagRepository hashTagRepository;
    @Autowired
    PostService postService;

    @Override
    public HashTags loadPostByTagName(String tagname) {
        return hashTagRepository.findHashTagsByTagName(tagname);
    }
}
