package com.thduc.instafake.service;

import com.thduc.instafake.entity.Stories;
import com.thduc.instafake.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Set;

public interface StoryServiceImpl {
    Stories createStories(Stories stories);
    Stories viewStories(Long id, User viewer);
    Page loadStoriesList(User user, Pageable pageable);
}
