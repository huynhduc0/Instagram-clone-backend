package com.thduc.instafake.controller;

import com.thduc.instafake.entity.Recommendation;
import com.thduc.instafake.service.RecommendationService;
import com.thduc.instafake.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller()
public class RecommendationController {
    @Autowired
    RecommendationService recommendationService;
    @RequestMapping(method = RequestMethod.GET, value = "/train")
    public ResponseEntity loadDataForTrain(){
        return new ResponseEntity(recommendationService.getAllLikeUid(), HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/ptrain")
    public ResponseEntity loadPost(){
        return new ResponseEntity(recommendationService.getPosts(), HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.POST, value = "/addrec")
    public ResponseEntity addRec(@RequestBody Recommendation recommendation){
        recommendationService.saveRecommend(recommendation);
        return Helper.Successfully("saved done");
    }
}
