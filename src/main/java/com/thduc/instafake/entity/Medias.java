package com.thduc.instafake.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
public class Medias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;

    private String media_type;

    private String media_url;

    @CreationTimestamp
    private Timestamp created;

    @UpdateTimestamp
    private Timestamp updated;

    public Medias() {
    }

    public Medias(long id, String description, String media_type, String media_url) {
        this.id = id;
        this.description = description;
        this.media_type = media_type;
        this.media_url = media_url;
    }
    public Medias(String media_type, String media_url) {
        this.media_type = media_type;
        this.media_url = media_url;
    }

    public Medias(long id, String description, String media_type, String media_url, Timestamp created, Timestamp updated) {
        this.id = id;
        this.description = description;
        this.media_type = media_type;
        this.media_url = media_url;
        this.created = created;
        this.updated = updated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
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
