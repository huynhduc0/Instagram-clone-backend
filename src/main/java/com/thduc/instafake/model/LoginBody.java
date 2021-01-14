package com.thduc.instafake.model;

public class LoginBody {
    String username;
    String password;
    String accessToken;
    String deviceType;
    String deviceId;
    String pushToken;

    public LoginBody(String username, String password, String accessToken, String deviceType, String deviceId, String pushToken) {
        this.username = username;
        this.password = password;
        this.accessToken = accessToken;
        this.deviceType = deviceType;
        this.deviceId = deviceId;
        this.pushToken = pushToken;
    }

    public LoginBody() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
