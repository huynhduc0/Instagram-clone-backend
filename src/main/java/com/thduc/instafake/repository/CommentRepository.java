package com.thduc.instafake.repository;

import com.thduc.instafake.entity.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends PagingAndSortingRepository<Comments,Long> {
    Page findByPost_Id(long id, Pageable pageable);

}
