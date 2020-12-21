package com.thduc.instafake.repository;

import com.thduc.instafake.entity.Stories;
import com.thduc.instafake.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface StoryRepository extends PagingAndSortingRepository<Stories, Long> {
//    @Query(value = "SELECT u.id as uid, u.username, u.avatar, s.id FROM Stories s, User u  " +
//            "WHERE s.author MEMBER OF u.id AND u in :userlist AND s.created < current_date ")
    @Query(value = "SELECT user.id as uid,user.username,user.avatar, stories.id, " +
            "SUM(CASE WHEN stories.id = stories_viewer.stories_id and viewer_id = :userid THEN 1 ELSE 0 END) as numSeen," +
            "count(stories.id) as numStories " +
            "FROM stories JOIN user on stories.author_id = user.id " +
            "LEFT JOIN stories_viewer  on stories_viewer.stories_id = stories.id " +
            "where stories.created >= DATE_SUB(NOW(), INTERVAL 1 DAY) " +
            "AND stories.author_id in :userlist " +
            "GROUP BY user.id ",nativeQuery = true)
    Page<StoriesUser> loaddStories(@Param("userlist") List<User> userlist,long userid, Pageable pageable);
    public static interface StoriesUser {
        long getUid();
        String getUsername();
        String getAvatar();
        int getId();
        int getNumSeen();
        int getNumStories();
    }
}
