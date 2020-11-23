package com.thduc.instafake.repository;

import com.thduc.instafake.entity.Follows;
import com.thduc.instafake.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follows, Long> {
    Follows findByFromAndTo(User from, User to);
//    <Set>Follows findDistinctByFrom(User from);
}
