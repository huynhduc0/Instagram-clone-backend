package com.thduc.instafake.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thduc.instafake.constant.Constant;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@JsonFilter(Constant.TBL_POSTS_FILTER)
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonProperty("author")
    @ManyToOne
    private User user;

    private String description;

    @JsonProperty("numoflikes")
    private int numOfLikes;

    @JsonProperty("numofcomments")
    private int numOfComments;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Medias.class)
    private Set<Medias> medias;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonProperty(Constant.POSTS_HASHTAGS)
    private Set<HashTags> hashtags;


    public Posts() {
    }

    public Posts(long id, User user, String description, int numOfLikes, int numOfComments, Set<Medias> medias, Set<HashTags> hashtags) {
        this.id = id;
        this.user = user;
        this.description = description;
        this.numOfLikes = numOfLikes;
        this.numOfComments = numOfComments;
        this.medias = medias;
        this.hashtags = hashtags;
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
}