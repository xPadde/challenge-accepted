package com.challengeaccepted.controllers;

import com.challengeaccepted.models.NotificationModel;
import com.challengeaccepted.models.UserModel;
import com.challengeaccepted.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @CrossOrigin
    @RequestMapping(value = "/notifications/", method = RequestMethod.GET)
    public ResponseEntity<List<NotificationModel>> readAllNotifications() {
        return new ResponseEntity<List<NotificationModel>>(notificationService.getAllNotificationsFromDatabase(), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/notification/", method = RequestMethod.POST)
    public ResponseEntity createNotification(@RequestBody NotificationModel notificationModel) {
        if (notificationModel != null) {
            notificationService.saveNotificationToDatabase(notificationModel);
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/user/{id}/notifications/", method = RequestMethod.GET)
    public ResponseEntity<List<NotificationModel>> readPersonalNotifications(@PathVariable Long id){
        List<NotificationModel> notifications = new ArrayList<NotificationModel>();
        notifications = notificationService.getAllNotificationsFromChallengeCreator(id);

        return new ResponseEntity<List<NotificationModel>>(notifications, HttpStatus.OK);
    }
}