package com.thduc.instafake.service;

import com.thduc.instafake.entity.Recommendation;
import com.thduc.instafake.repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {
    @Autowired
    RecommendationRepository recommendationRepository;
    public List<RecommendationRepository.LikeUid> getAllLikeUid(){
       return recommendationRepository.getAllLikeAno();
    }
    public List<RecommendationRepository.PostIDOnly> getPosts(){
        return recommendationRepository.getPostIDOnly();
    }
    public boolean saveRecommend(Recommendation recommendation){
        if(!recommendationRepository.existsByUserIdAndPostId(recommendation.getUserId(),recommendation.getPostId()))
            recommendationRepository.save(recommendation);
         return true;
    }
    public List<Long> getAllReId(long userId){
        return recommendationRepository.getAllIds(userId);
    }
}
