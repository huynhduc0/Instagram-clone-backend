package com.thduc.instafake.repository;

import com.thduc.instafake.entity.Follows;
import com.thduc.instafake.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follows, Long> {
    Follows findByFromAndTo(User from, User to);
//    <Set>Follows findAllByFrom(User from);
    @Query(value = "select fl.to from Follows fl where fl.from.id = :from_id")
    List<User> LoadFollowing(@Param("from_id") Long from_id);

}
