package com.thduc.instafake.model;

public class AccessTokenBody {
    String accessToken;
    String deviceType;
    String deviceId;
    String pushToken;

    public AccessTokenBody() {
    }

    public AccessTokenBody(String accessToken, String deviceType, String deviceId, String pushToken) {
        this.accessToken = accessToken;
        this.deviceType = deviceType;
        this.deviceId = deviceId;
        this.pushToken = pushToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }
}
