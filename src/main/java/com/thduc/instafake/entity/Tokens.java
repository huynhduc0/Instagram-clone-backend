package com.thduc.instafake.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tokens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String pushToken;
    private String jwtToken;
    private String deviceType;
    private String deviceId;

    public Tokens() {
    }

    public Tokens(long id, String pushToken, String jwtToken, String deviceType) {
        this.id = id;
        this.pushToken = pushToken;
        this.jwtToken = jwtToken;
        this.deviceType = deviceType;
    }

    public Tokens(String pushToken, String deviceType, String deviceId) {
        this.pushToken = pushToken;
        this.deviceType = deviceType;
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Tokens(long id, String pushToken, String jwtToken, String deviceType, String deviceId) {
        this.id = id;
        this.pushToken = pushToken;
        this.jwtToken = jwtToken;
        this.deviceType = deviceType;
        this.deviceId = deviceId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
