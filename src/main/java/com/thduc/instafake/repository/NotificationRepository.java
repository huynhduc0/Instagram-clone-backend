package com.thduc.instafake.repository;

import com.thduc.instafake.entity.Notifications;
import com.thduc.instafake.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends PagingAndSortingRepository<Notifications,Long> {
    Page findByTo(User user, Pageable pageable);
}
