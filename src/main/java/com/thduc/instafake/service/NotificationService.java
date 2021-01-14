package com.thduc.instafake.service;

import com.thduc.instafake.constant.NotifcationType;
import com.thduc.instafake.entity.Notifications;
import com.thduc.instafake.entity.User;
import com.thduc.instafake.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements NotificationServiceImpl{

    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    private FCMPushService fcmPushService;

    @Override
    public Page loadNoti(User user, Pageable pageable) {
        return  notificationRepository.findByTo(user,pageable);
    }

    @Override
    public Notifications addNotification(User from, User to, String message, NotifcationType notifcationType,long destinationId) {
        Notifications notification = new Notifications(from,to,message,notifcationType,destinationId);
        Notifications notifications = notificationRepository.save(notification);
        fcmPushService.sendPnsToDevice(notifications);
        return notifications;
    }
}
