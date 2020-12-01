package com.thduc.instafake.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thduc.instafake.constant.Constant;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
//@JsonFilter(Constant.TBL_FOLLOWS_FILTER)
public class Follows {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JsonProperty(Constant.FOLLOW_FROM)
    private User from;

    @ManyToOne
    @JsonProperty(Constant.FOLLOW_TO)
    private User to;

    @CreationTimestamp
    private Timestamp created;

    @UpdateTimestamp
    private Timestamp updated;

    public Follows() {
    }

    public Follows(long id, User from, User to) {
        this.id = id;
        this.from = from;
        this.to = to;
    }
    public Follows( User from, User to) {
        this.from = from;
        this.to = to;
    }

    public Follows(long id, User from, User to, Timestamp created, Timestamp updated) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.created = created;
        this.updated = updated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
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
