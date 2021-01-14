package com.thduc.instafake.service;

import com.google.api.client.util.Value;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.thduc.instafake.entity.Notifications;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@Service
public class FCMPushService {
//    @Value("${app.firebase-config}")
    private String firebaseConfig = "hihi-1541495393612-firebase-adminsdk-nb3b4-0f3ff37c00.json";
    Logger log = LoggerFactory.getLogger(FCMPushService.class);
    private FirebaseApp firebaseApp;

    @PostConstruct
    private void initialize() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfig).getInputStream())).build();

            if (FirebaseApp.getApps().isEmpty()) {
                this.firebaseApp = FirebaseApp.initializeApp(options);
            } else {
                this.firebaseApp = FirebaseApp.getInstance();
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Create FirebaseApp Error", e);
        }
    }

//    public void subscribeToTopic(SubscriptionRequestDto subscriptionRequestDto) {
//        try {
//            FirebaseMessaging.getInstance(firebaseApp).subscribeToTopic(subscriptionRequestDto.getTokens(),
//                    subscriptionRequestDto.getTopicName());
//        } catch (FirebaseMessagingException e) {
//            log.error("Firebase subscribe to topic fail", e);
//        }
//    }
//
//    public void unsubscribeFromTopic(SubscriptionRequestDto subscriptionRequestDto) {
//        try {
//            FirebaseMessaging.getInstance(firebaseApp).unsubscribeFromTopic(subscriptionRequestDto.getTokens(),
//                    subscriptionRequestDto.getTopicName());
//        } catch (FirebaseMessagingException e) {
//            log.error("Firebase unsubscribe from topic fail", e);
//        }
//    }

    public boolean sendPnsToDevice(Notifications notifications) {
        notifications.getTo().getTokens().forEach((tokens)->{
            Message message = Message.builder()
                    .setToken(tokens.getPushToken())
                    .setNotification(new Notification("INSTAFAKE", notifications.getMessage()))
                    .putData("notification_type", String.valueOf(notifications.getNotifcationType()))
                    .putData("destinationId", String.valueOf(notifications.getDestinationId()))
                    .build();
            String response = null;
            try {
                response = FirebaseMessaging.getInstance().send(message);
            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
            log.error("Fail to send firebase notification", e);
            }
        });
        return true;
    }

//    public String sendPnsToTopic(NotificationRequestDto notificationRequestDto) {
//        Message message = Message.builder()
//                .setTopic(notificationRequestDto.getTarget())
//                .setNotification(new Notification(notificationRequestDto.getTitle(), notificationRequestDto.getBody()))
//                .putData("content", notificationRequestDto.getTitle())
//                .putData("body", notificationRequestDto.getBody())
//                .build();
//
//        String response = null;
//        try {
//            FirebaseMessaging.getInstance().send(message);
//        } catch (FirebaseMessagingException e) {
//            log.error("Fail to send firebase notification", e);
//        }
//
//        return response;
//    }
}
