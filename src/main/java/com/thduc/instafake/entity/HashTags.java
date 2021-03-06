package com.thduc.instafake.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Set;

@Entity
//@JsonFilter(Constant.TBL_HASHTAGS_FILTER)
public class HashTags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("tagname")
    @NotBlank(message = "Tag name can't be null")
    private String tagName;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "hashtags")
    private Set<Posts> posts;

    @CreationTimestamp
    private Timestamp created;

    @UpdateTimestamp
    private Timestamp updated;

    public HashTags() {
    }

    public HashTags(Long id, @NotBlank(message = "Tag name can't be null") String tagName, Set<Posts> posts) {
        this.id = id;
        this.tagName = tagName;
        this.posts = posts;
    }

    public HashTags(Long id, @NotBlank(message = "Tag name can't be null") String tagName, Set<Posts> posts, Timestamp created, Timestamp updated) {
        this.id = id;
        this.tagName = tagName;
        this.posts = posts;
        this.created = created;
        this.updated = updated;
    }

    public HashTags(String tag) {
        this.tagName = tag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Set<Posts> getPosts() {
        return posts;
    }

    public void setPosts(Set<Posts> posts) {
        this.posts = posts;
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
}
