package com.thduc.instafake.service;

import com.thduc.instafake.entity.*;
import com.thduc.instafake.exception.DataNotFoundException;
import com.thduc.instafake.repository.CommentRepository;
import com.thduc.instafake.repository.PostRepository;
import com.thduc.instafake.repository.ReportCommentRepository;
import com.thduc.instafake.repository.ReportCriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLDataException;
import java.util.HashSet;
import java.util.Set;

@Service
public class CommentService implements CommentServiceImpl {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ReportCommentRepository reportCommentRepository;

    @Autowired
    ReportCriteriaRepository reportCriteriaRepository;

    @Autowired
    PostRepository postRepository;
    @Override
    public Comments addComment(Comments comments) {
        Posts posts = postRepository.findById(comments.getPost().getId())
                .orElseThrow(()->new DataNotFoundException("post","post",String.valueOf(comments.getPost().getId())));
        Posts filtered = new Posts();
        filtered.setId(posts.getId());
        filtered.setUser(posts.getUser());
        comments.setPost(filtered);
        Comments newComment = commentRepository.save(comments);
        posts.setNumOfComments(posts.getNumOfComments()+1);
        postRepository.save(posts);
        return newComment;
    }
    @Override
    public Page loadCommentByPostId(long id, Pageable pageable){
        return commentRepository.findByPost_Id(id,pageable);
    }

    @Override
    public boolean delete(Comments comments) {
         try {
             Posts posts = postRepository.findById(comments.getPost().getId())
                     .orElseThrow(()->new DataNotFoundException("post","post",String.valueOf(comments.getPost().getId())));
             commentRepository.delete(comments);
             posts.setNumOfComments(posts.getNumOfComments()-1);
             postRepository.save(posts);
         }catch (Exception ex){
             return false;
         }
         return true;
    }

    @Override
    public boolean addReport(ReportDetails reportDetails, long commentId) {
        Comments comments = commentRepository.findById(commentId)
                .orElseThrow(()-> new DataNotFoundException("comment", "comment",String.valueOf(commentId)));
        ReportComments reportComments = reportCommentRepository.findReportCommentsByComments(comments);
        if (!reportCriteriaRepository.existsByCriteriaName(reportDetails.getReportCriterias().getCriteriaName()))
            reportCriteriaRepository.save(reportDetails.getReportCriterias());
        if (reportComments != null){
            Set<ReportDetails> reportDetailsSet = new HashSet<>(reportComments.getReportDetails());
            reportComments.setReportDetails(reportDetailsSet);
            reportCommentRepository.save(reportComments);
        }else {
            Set<ReportDetails> reportDetailsSet = new HashSet<>();
            reportDetailsSet.add(reportDetails);
            ReportComments reportComments1 = new ReportComments();
            reportComments1.setComments(comments);
            reportComments1.setReportDetails(reportDetailsSet);
            reportCommentRepository.save(reportComments1);
        }

        return true;
    }


}
