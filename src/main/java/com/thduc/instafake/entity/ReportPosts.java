package com.thduc.instafake.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
public class ReportPosts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Posts posts;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<ReportDetails> reportDetails;

    @Column(columnDefinition = "boolean default true")
    private boolean isClosed;

    @CreationTimestamp
    private Timestamp created;

    @UpdateTimestamp
    private Timestamp updated;

    public ReportPosts() {
    }

    public ReportPosts(long id, Posts posts, Set<ReportDetails> reportDetails, boolean isClosed, Timestamp created, Timestamp updated) {
        this.id = id;
        this.posts = posts;
        this.reportDetails = reportDetails;
        this.isClosed = isClosed;
        this.created = created;
        this.updated = updated;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Posts getPosts() {
        return posts;
    }

    public void setPosts(Posts posts) {
        this.posts = posts;
    }

    public Set<ReportDetails> getReportDetails() {
        return reportDetails;
    }

    public void setReportDetails(Set<ReportDetails> reportDetails) {
        this.reportDetails = reportDetails;
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
