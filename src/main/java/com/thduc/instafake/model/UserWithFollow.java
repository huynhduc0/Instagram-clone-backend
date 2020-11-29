package com.thduc.instafake.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
public class UserWithFollow {
    private long id;
    private String avatar;
    private String username;
    private int following;

    public UserWithFollow() {

    }

    public UserWithFollow(long id, String avatar, String username, int following) {
        this.id = id;
        this.avatar = avatar;
        this.username = username;
        this.following = following;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }
}
