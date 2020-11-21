package com.thduc.instafake.repository;

import com.thduc.instafake.entity.HashTags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagRepository extends JpaRepository<HashTags,Long> {
    HashTags findHashTagsByTagName(String tagName);
    boolean existsByTagName(String tagName);
}
