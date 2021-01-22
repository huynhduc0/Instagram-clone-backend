package com.thduc.instafake.service;

import com.thduc.instafake.constant.NotifcationType;
import com.thduc.instafake.entity.Notifications;
import com.thduc.instafake.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationServiceImpl {
    Page loadNoti(User user, Pageable pageable);
    Notifications addNotification(User from, User to, String message, NotifcationType notifcationType, long destinationId, String imag);
}
