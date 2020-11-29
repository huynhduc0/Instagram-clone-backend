package com.thduc.instafake.service;

import com.thduc.instafake.entity.Stories;
import com.thduc.instafake.entity.User;
import com.thduc.instafake.exception.DataNotFoundException;
import com.thduc.instafake.repository.FollowRepository;
import com.thduc.instafake.repository.StoryRepository;
import com.thduc.instafake.utils.FileUtils;
import com.thduc.instafake.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

@Service
public class StoryService implements StoryServiceImpl {
    @Autowired
    StoryRepository storyRepository;
    @Autowired
    FollowRepository followRepository;

    @Override
    public Stories createStories(Stories stories) {
        stories.setUrl(FileUtils.saveFileToStorage(String.valueOf(stories.getAuthor().getId()), Helper.currentTime("story"),stories.getUrl(),false));
        return storyRepository.save(stories);
    }

    @Override
    public Stories viewStories(Long id, User viewer){
        Stories story = storyRepository.findById(id).orElseThrow(()->new DataNotFoundException("stories","stories",String.valueOf(id)));
        if (story.getViewer().contains(viewer))
            return story;
        Set<User> newViewerList =  story.getViewer();
        newViewerList.add(viewer);
        story.setViewer(newViewerList);
        return storyRepository.save(story);
    }

    @Override
    public Page loadStoriesList(User user, Pageable pageable) {
        List<User> users = followRepository.LoadFollowing(user.getId());
        users.add(user);
        return storyRepository.loaddStories(users,user.getId(),pageable);
    }
}
