package com.thduc.instafake.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thduc.instafake.constant.Constant;
import lombok.Data;

import javax.persistence.*;

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
}
