package com.thduc.instafake.repository;

import com.thduc.instafake.entity.Posts;
import com.thduc.instafake.entity.ReportPosts;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReportPostRepository extends PagingAndSortingRepository<ReportPosts,Long> {
   ReportPosts findReportPostsByPosts(Posts posts);
   ReportPosts findReportPostsByPosts_Id(long id);
}
