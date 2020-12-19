package com.thduc.instafake.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
public class ReportComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Comments comments;

    @OneToMany(cascade=CascadeType.ALL)
    private Set<ReportDetails> reportDetails;

    @CreationTimestamp
    private Timestamp created;

    @UpdateTimestamp
    private Timestamp updated;

    public ReportComments() {
    }

    public ReportComments(long id, Comments comments, Set<ReportDetails> reportDetails, Timestamp created, Timestamp updated) {
        this.id = id;
        this.comments = comments;
        this.reportDetails = reportDetails;
        this.created = created;
        this.updated = updated;
    }
    public ReportComments(Comments comments, Set<ReportDetails> reportDetails) {
        this.comments = comments;
        this.reportDetails = reportDetails;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
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
