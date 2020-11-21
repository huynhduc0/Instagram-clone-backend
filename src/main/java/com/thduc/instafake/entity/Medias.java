package com.thduc.instafake.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Medias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;

    private String media_type;

    private String media_url;

    public Medias() {
    }

    public Medias(long id, String description, String media_type, String media_url) {
        this.id = id;
        this.description = description;
        this.media_type = media_type;
        this.media_url = media_url;
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
}
