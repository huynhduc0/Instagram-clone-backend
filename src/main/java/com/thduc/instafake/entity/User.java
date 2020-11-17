package com.thduc.instafake.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Document(collection = "Users")
public class User {

    @Id
    private Long id;

    @Field(value = "avatar")
    private String avatar;

    @Field(value = "username")
    private String username;

    @Field(value = "email")
    private String email;

    @Field(value = "num_of_followings")
    private int numOfFollowings;

    @Field(value = "num_of_followers")
    private int numOfFollowers;

    @Field(value = "cover")
    private String cover;

    @Field(value = "bio")
    private String bio;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumOfFollowings() {
        return numOfFollowings;
    }

    public void setNumOfFollowings(int numOfFollowings) {
        this.numOfFollowings = numOfFollowings;
    }

    public int getNumOfFollowers() {
        return numOfFollowers;
    }

    public void setNumOfFollowers(int numOfFollowers) {
        this.numOfFollowers = numOfFollowers;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
