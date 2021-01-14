package com.thduc.instafake.model;

public class LoginBody {
    String username;
    String password;
    String deviceType;
    String deviceId;
    String pushToken;

    public LoginBody() {
    }

    public LoginBody(String username, String password, String deviceType, String deviceId, String pushToken) {
        this.username = username;
        this.password = password;
        this.deviceType = deviceType;
        this.deviceId = deviceId;
        this.pushToken = pushToken;
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
