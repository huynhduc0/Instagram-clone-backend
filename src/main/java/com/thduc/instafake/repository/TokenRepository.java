package com.thduc.instafake.repository;

import com.thduc.instafake.entity.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TokenRepository extends JpaRepository< Tokens,Long> {
    Tokens findByDeviceId(String deviceId);
}
