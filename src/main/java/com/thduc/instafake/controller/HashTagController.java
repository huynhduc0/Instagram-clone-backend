package com.thduc.instafake.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.thduc.instafake.constant.Constant;
import com.thduc.instafake.entity.HashTags;
import com.thduc.instafake.service.HashTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class HashTagController {
    @Autowired
    HashTagService hashTagService;
    @GetMapping(value = "/tags/{tagname}")
    public ResponseEntity findPostByTag(@PathVariable String tagname){
        return new ResponseEntity(filterPostsBasic(hashTagService.loadPostByTagName(tagname)), HttpStatus.OK);
    }
    private MappingJacksonValue filterPostsBasic(Object oject) {
        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
        simpleFilterProvider.addFilter(Constant.TBL_POSTS_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(Constant.POSTS_HASHTAGS));
        MappingJacksonValue wrapper = new MappingJacksonValue(oject);
        wrapper.setFilters(simpleFilterProvider);
        return wrapper;
    }

}
