package com.challengeaccepted.controllers;

import com.challengeaccepted.models.NotificationModel;
import com.challengeaccepted.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    private List<NotificationModel> notifications;

    @CrossOrigin
    @RequestMapping(value = "/notifications/", method = RequestMethod.GET)
    public ResponseEntity<List<NotificationModel>> readAllNotifications() {
        return new ResponseEntity<>(notificationService.getAllNotificationsFromDatabase(), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/notifications/", method = RequestMethod.POST)
    public ResponseEntity createNotification(@RequestBody NotificationModel notificationModel) {
        return getCreateNotificationResponseEntity(notificationModel);
    }

    @CrossOrigin
    @RequestMapping(value = "/user/{id}/notifications/", method = RequestMethod.GET)
    public ResponseEntity<List<NotificationModel>> readPersonalNotifications(@PathVariable Long id) {
        notifications = getAllNotifications(id);
        notifications = removeDuplicateEntries(notifications);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    private ResponseEntity getCreateNotificationResponseEntity(@RequestBody NotificationModel notificationModel) {
        if (notificationModel != null) {
            notificationService.saveNotificationToDatabase(notificationModel);
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    private List<NotificationModel> getAllNotifications(@PathVariable Long id) {
        notifications = notificationService.getAllNotificationsFromChallengeCreator(id);
        notifications.addAll(notificationService.getAllNotificationsFromChallengeClaimer(id));
        notifications.addAll(notificationService.getAllNotificationsFromChallengeUpvotersId(id));
        return notifications;
    }

    @SuppressWarnings("unchecked")
    private List<NotificationModel> removeDuplicateEntries(List notifications) {
        Set<NotificationModel> notificationSet = new HashSet<>();
        notificationSet.addAll(notifications);
        notifications.clear();
        notifications.addAll(notificationSet);
        return notifications;
    }

}