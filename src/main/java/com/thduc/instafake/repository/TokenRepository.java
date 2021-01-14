package com.thduc.instafake.repository;

import com.thduc.instafake.entity.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TokenRepository extends JpaRepository< Tokens,Long> {
    Tokens findTopByDeviceId(String deviceId);
    void deleteAllByDeviceId(String deviceId);
}
