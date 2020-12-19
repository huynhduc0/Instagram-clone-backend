package com.thduc.instafake.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ReportCriterias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String criteriaName;

    public ReportCriterias(long id, String criteriaName) {
        this.id = id;
        this.criteriaName = criteriaName;
    }

    public ReportCriterias() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCriteriaName() {
        return criteriaName;
    }

    public void setCriteriaName(String criteriaName) {
        this.criteriaName = criteriaName;
    }
}
