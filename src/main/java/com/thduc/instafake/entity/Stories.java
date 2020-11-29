package com.thduc.instafake.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
public class Stories {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String url;

    private String type;

    @CreationTimestamp
    private Timestamp created;

    @ManyToOne
    private User author;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<User> viewer;


    public Stories() {
    }

    public Stories(long id, String url, String type, Timestamp created, User author, Set<User> viewer) {
        this.id = id;
        this.url = url;
        this.type = type;
        this.created = created;
        this.author = author;
        this.viewer = viewer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<User> getViewer() {
        return viewer;
    }

    public void setViewer(Set<User> viewer) {
        this.viewer = viewer;
    }
}
