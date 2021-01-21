package com.thduc.instafake.service;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.thduc.instafake.constant.UploadConstant;
import com.thduc.instafake.entity.*;
import com.thduc.instafake.exception.DataNotFoundException;
import com.thduc.instafake.model.PostWithLikes;
import com.thduc.instafake.repository.*;
import com.thduc.instafake.utils.FileUtils;
import com.thduc.instafake.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    @Autowired
    LikeService likeService;

    @Autowired
    RecommendationService recommendationService;

//    @Autowired
//    CloudVisionTemplate cloudVisionTemplate;
//    @Autowired private ResourceLoader resourceLoader;

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
        posts.getMedias().stream().map(medias -> new Medias(medias.getMedia_type(), FileUtils.saveFileToStorage(String.valueOf(uid), Helper.currentTime("post"), medias.getMedia_url(), false))).forEach(medi -> {
            mediasSet.add(medi);
//            AnnotateImageResponse response =
//                    this.cloudVisionTemplate.analyzeImage(
//                            this.resourceLoader.getResource("file:src/main/resources/static/uploads/"+medi.getMedia_url()), Feature.Type.LABEL_DETECTION);
////            AnnotateImageResponse response =
////                    this.cloudVisionTemplate.analyzeImage(
////                            new ByteArrayResource((UploadConstant.UPLOAD_PATH+medi.getMedia_url()).getBytes()), Feature.Type.LABEL_DETECTION);
//            Map<String, Float> imageLabels = response
//                    .getLabelAnnotationsList()
//                    .stream()
//                    .collect(Collectors.toMap(
//                            EntityAnnotation::getDescription,
//                            EntityAnnotation::getScore,
//                            (u, v) -> {
//                                throw new IllegalStateException(String.format("Duplicate key %s", u));
//                            },
//                            LinkedHashMap::new));
//            imageLabels.forEach((label, score) -> {
//                HashTags hagtag = (hashTagRepository.existsByTagName(label))
//                        ?hashTagRepository.findHashTagsByTagName(label)
//                        :hashTagRepository.save(new HashTags(label));
//                hashTags.add(hagtag);
//            });
        });

        posts.setMedias(mediasSet);
        posts.setUser(user);
        posts.setHashtags(hashTags);
        posts = postRepository.save(posts);
        return posts;
    }

    @Override
    public Page loadNewsFedd(Long id, User user, Pageable pageable) {
       List<User> users = followRepository.LoadFollowing(id);
       List<Long> ids = recommendationService.getAllReId(id);
        Page allPost = postRepository.findAllByUserInOrIdIn(users,ids,pageable);
        Page<PostWithLikes> map = allPost.map((Function<Posts, PostWithLikes>) posts -> new PostWithLikes(posts, likeService.existLike(posts.getId(),user)));
        return map;
    }

    @Override
    public Page loadRecommend(Long id, User user, Pageable pageable) {
        List<Long> ids = recommendationService.getAllReId(id);
        Page allPost = postRepository.findAllByIdIn(ids,pageable);
        Page<PostWithLikes> map = allPost.map((Function<Posts, PostWithLikes>) posts -> new PostWithLikes(posts, likeService.existLike(posts.getId(),user)));
        return map;
    }
    @Override
    public Page loadPopular(Long id, User user, Pageable pageable) {
        return postRepository.findAll(pageable);
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
