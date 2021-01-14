package com.thduc.instafake.service;

import com.thduc.instafake.entity.Tokens;
import com.thduc.instafake.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Autowired
    TokenRepository tokenRepository;
    public Tokens refreshToken(String pushToken, String deviceType, String deviceId){
        Tokens oldToken = tokenRepository.findTopByDeviceId(deviceId);
        Tokens newToken;
        if (oldToken != null){
            oldToken.setPushToken(pushToken);
            tokenRepository.save(oldToken);
            return null;
        }else {
            newToken =  new Tokens(pushToken,deviceType,deviceId);
        }
        return  newToken;
    }
    public void deleteAllToken(String deviceId){
        tokenRepository.deleteAllByDeviceId(deviceId);
    }
}
