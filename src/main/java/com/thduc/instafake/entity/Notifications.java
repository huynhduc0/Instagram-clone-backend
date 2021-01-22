package com.thduc.instafake.entity;


import com.thduc.instafake.constant.NotifcationType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


@Entity
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private User from;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private User to;

    private String message;

    @Enumerated(EnumType.ORDINAL)
    private NotifcationType notifcationType;

    private long destinationId;
    private String imageUrl;
    @CreationTimestamp
    private Timestamp created;

    @UpdateTimestamp
    private Timestamp updated;


    public Notifications() {
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Notifications(long id, User from, User to, String message, NotifcationType notifcationType, long destinationId, String imageUrl, Timestamp created, Timestamp updated) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.message = message;
        this.notifcationType = notifcationType;
        this.destinationId = destinationId;
        this.imageUrl = imageUrl;
        this.created = created;
        this.updated = updated;
    }

    public Notifications(long id, User from, User to, String message, NotifcationType notifcationType, long destinationId, String imageUrl) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.message = message;
        this.notifcationType = notifcationType;
        this.destinationId = destinationId;
        this.imageUrl = imageUrl;
    }


    public Notifications(User from, User to, String message, NotifcationType notifcationType, long destinationId) {
        this.from = from;
        this.to = to;
        this.message = this.from.getUsername() + message;
        this.notifcationType = notifcationType;
        this.destinationId = destinationId;
    }
    public Notifications(User from, User to, String message, NotifcationType notifcationType, long destinationId, String imageUrl) {
        this.from = from;
        this.to = to;
        this.message = this.from.getUsername() + message;
        this.notifcationType = notifcationType;
        this.imageUrl = imageUrl;
        this.destinationId = destinationId;
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

    public Notifications(long id, User from, User to, String message, NotifcationType notifcationType, long destinationId, Timestamp created, Timestamp updated) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.message = message;
        this.notifcationType = notifcationType;
        this.destinationId = destinationId;
        this.created = created;
        this.updated = updated;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotifcationType getNotifcationType() {
        return notifcationType;
    }

    public void setNotifcationType(NotifcationType notifcationType) {
        this.notifcationType = notifcationType;
    }

    public long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(long destinationId) {
        this.destinationId = destinationId;
    }

    public Notifications(long id, User from, User to, String message, NotifcationType notifcationType, long destinationId) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.message = message;
        this.notifcationType = notifcationType;
        this.destinationId = destinationId;
    }
}
