package com.thduc.instafake.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
public class ReportDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private User reportUser;

    @OneToOne
    ReportCriterias reportCriterias;

    @CreationTimestamp
    private Timestamp created;

    @UpdateTimestamp
    private Timestamp updated;

    public ReportDetails() {
    }

    public ReportDetails(long id, User reportUser, ReportCriterias reportCriterias, Timestamp created, Timestamp updated) {
        this.id = id;
        this.reportUser = reportUser;
        this.reportCriterias = reportCriterias;
        this.created = created;
        this.updated = updated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getReportUser() {
        return reportUser;
    }

    public void setReportUser(User reportUser) {
        this.reportUser = reportUser;
    }

    public ReportCriterias getReportCriterias() {
        return reportCriterias;
    }

    public void setReportCriterias(ReportCriterias reportCriterias) {
        this.reportCriterias = reportCriterias;
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
