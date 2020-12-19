package com.thduc.instafake.repository;

import com.thduc.instafake.entity.Comments;
import com.thduc.instafake.entity.ReportComments;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportCommentRepository extends PagingAndSortingRepository<ReportComments,Long> {
    boolean existsByComments(Comments comments);
    ReportComments findReportCommentsByComments(Comments comments);
}
