package com.thduc.instafake.model;


import com.thduc.instafake.entity.HashTags;
import com.thduc.instafake.entity.Medias;
import com.thduc.instafake.entity.Posts;
import com.thduc.instafake.entity.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Set;


public class PostWithLikes {
    private long id;


    private User user;

    private String description;
    private int numOfLikes;

    private int numOfComments;

    private Set<Medias> medias;

    private Set<HashTags> hashtags;

    @CreationTimestamp
    private Timestamp created;

    @UpdateTimestamp
    private Timestamp updated;
    private boolean isLike;

    public PostWithLikes(long id, User user, String description, int numOfLikes, int numOfComments, Set<Medias> medias, Set<HashTags> hashtags, Timestamp created, Timestamp updated, boolean isLike) {
        this.id = id;
        this.user = user;
        this.description = description;
        this.numOfLikes = numOfLikes;
        this.numOfComments = numOfComments;
        this.medias = medias;
        this.hashtags = hashtags;
        this.created = created;
        this.updated = updated;
        this.isLike = isLike;
    }
    public PostWithLikes(Posts posts, boolean isLike) {
        this.id = posts.getId();
        this.user = posts.getUser();
        this.description = posts.getDescription();
        this.numOfLikes = posts.getNumOfLikes();
        this.numOfComments = posts.getNumOfComments();
        this.medias = posts.getMedias();
//        this.hashtags = posts.getHashtags();
        this.created = posts.getCreated();
        this.updated = posts.getUpdated();
        this.isLike = isLike;
    }

    public PostWithLikes(long id, User user, String description, int numOfLikes, int numOfComments, Set<Medias> medias, Set<HashTags> hashtags, Timestamp created, Timestamp updated) {
        this.id = id;
        this.user = user;
        this.description = description;
        this.numOfLikes = numOfLikes;
        this.numOfComments = numOfComments;
        this.medias = medias;
        this.hashtags = hashtags;
        this.created = created;
        this.updated = updated;
    }

    public PostWithLikes() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumOfLikes() {
        return numOfLikes;
    }

    public void setNumOfLikes(int numOfLikes) {
        this.numOfLikes = numOfLikes;
    }

    public int getNumOfComments() {
        return numOfComments;
    }

    public void setNumOfComments(int numOfComments) {
        this.numOfComments = numOfComments;
    }

    public Set<Medias> getMedias() {
        return medias;
    }

    public void setMedias(Set<Medias> medias) {
        this.medias = medias;
    }

    public Set<HashTags> getHashtags() {
        return hashtags;
    }

    public void setHashtags(Set<HashTags> hashtags) {
        this.hashtags = hashtags;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
