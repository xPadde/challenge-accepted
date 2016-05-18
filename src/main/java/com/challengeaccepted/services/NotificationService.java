package com.challengeaccepted.services;

import com.challengeaccepted.models.NotificationModel;
import com.challengeaccepted.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void saveNotificationToDatabase(NotificationModel notificationModel) {
        notificationRepository.saveAndFlush(notificationModel);
    }

    public List<NotificationModel> getAllNotificationsFromDatabase() {
        return notificationRepository.findAll();
    }

}