package com.thduc.instafake.service;

import com.thduc.instafake.entity.Tokens;
import com.thduc.instafake.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TokenService {
    @Autowired
    TokenRepository tokenRepository;
    private Tokens refreshToken(String pushToken, String deviceType, String deviceId){
        Tokens oldToken = tokenRepository.findByDeviceId(deviceId);
        Tokens newToken;
        if (oldToken != null){
            oldToken.setPushToken(pushToken);
            newToken = tokenRepository.save(oldToken);
        }else {
            newToken =  tokenRepository.save(new Tokens(pushToken,deviceType,deviceId));
        }
        return  newToken;
    }
}
