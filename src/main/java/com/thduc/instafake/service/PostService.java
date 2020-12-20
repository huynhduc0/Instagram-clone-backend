package com.thduc.instafake.service;

import com.thduc.instafake.entity.*;
import com.thduc.instafake.exception.DataNotFoundException;
import com.thduc.instafake.repository.*;
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
    @Autowired
    ReportPostRepository reportPostRepository;
    @Autowired
    ReportCriteriaRepository reportCriteriaRepository;

    @Override
    public Posts uploadPost(Posts posts,Long uid) {
        User user = userRepository.findById(uid).orElseThrow(()->new DataNotFoundException("user","user",String.valueOf(uid)));
        List<String> tagNameList = Helper.hashTagsStringFromString(posts.getDescription());
        final Set<HashTags> hashTags = (posts.getHashtags() != null)? posts.getHashtags(): new HashSet<>();
        tagNameList.forEach(tag->{
            HashTags hagtag = (hashTagRepository.existsByTagName(tag))
                    ?hashTagRepository.findHashTagsByTagName(tag)
                    :hashTagRepository.save(new HashTags(tag));
            hashTags.add(hagtag);
        });
        Set<Medias> mediasSet = new HashSet<>();
        posts.getMedias().forEach(medias -> {
            Medias medi =  new Medias(medias.getMedia_type(),FileUtils.saveFileToStorage(String.valueOf(uid),Helper.currentTime("post"),medias.getMedia_url(),false));
            mediasSet.add(medi);
        });
        posts.setMedias(mediasSet);
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
    public Page<Posts> loadPostByUid(long id, Pageable pageable){
        return postRepository.findAllByUser_Id(id,pageable);
    }

    @Override
    public boolean addReport(ReportDetails reportDetails, long postId) {
       Posts posts = postRepository.findById(postId)
                .orElseThrow(()-> new DataNotFoundException("post", "post",String.valueOf(postId)));
        ReportPosts reportPosts = reportPostRepository.findReportPostsByPosts(posts);
        if (!reportCriteriaRepository.existsByCriteriaName(reportDetails.getReportCriterias().getCriteriaName()))
            reportCriteriaRepository.save(reportDetails.getReportCriterias());
        if (reportPosts != null){
            Set<ReportDetails> reportDetailsSet = new HashSet<>(reportPosts.getReportDetails());
            reportPosts.setReportDetails(reportDetailsSet);
            reportPostRepository.save(reportPosts);
        }else {
            Set<ReportDetails> reportDetailsSet = new HashSet<>();
            reportDetailsSet.add(reportDetails);
            ReportPosts reportPosts1 = new ReportPosts();
            reportPosts1.setPosts(posts);
            reportPosts1.setReportDetails(reportDetailsSet);
            reportPostRepository.save(reportPosts1);
        }
        return true;
    }

    @Override
    public Page loadAllReport(Pageable pageable) {
        return null;
    }
}
