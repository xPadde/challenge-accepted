package com.challengeaccepted.controllers;

import com.challengeaccepted.models.ChallengeModel;
import com.challengeaccepted.models.CommentModel;
import com.challengeaccepted.models.NotificationModel;
import com.challengeaccepted.models.UserModel;
import com.challengeaccepted.models.enums.Action;
import com.challengeaccepted.services.ChallengeService;
import com.challengeaccepted.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentController commentController;
    @Autowired
    private NotificationController notificationController;

    @CrossOrigin
    @RequestMapping(value = "/challenge/create/challenge-creator/{challengeCreatorId}", method = RequestMethod.POST)
    public ResponseEntity createChallenge(@RequestBody ChallengeModel challengeModel, @PathVariable Long challengeCreatorId) {

        if (challengeModel.getTopic().equals("") || challengeModel.getDescription().equals("")) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        UserModel challengeCreator = userService.getUserFromDatabase(challengeCreatorId);
        challengeModel.setChallengeCreator(challengeCreator);

        challengeService.saveChallengeToDatabase(challengeModel);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}", method = RequestMethod.GET)
    public ResponseEntity<ChallengeModel> readChallenge(@PathVariable Long id) {
        return new ResponseEntity<ChallengeModel>(challengeService.getChallengeFromDatabase(id), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenges/", method = RequestMethod.GET)
    public ResponseEntity<List<ChallengeModel>> readAllChallenges() {
        return new ResponseEntity<List<ChallengeModel>>(challengeService.getAllChallengesFromDatabase(), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenges/completed/", method = RequestMethod.GET)
    public ResponseEntity<List<ChallengeModel>> readAllCompletedChallenges() {
        return new ResponseEntity<List<ChallengeModel>>(challengeService.getAllCompletedChallengesFromDatabase(), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenges/unapproved/", method = RequestMethod.GET)
    public ResponseEntity<List<ChallengeModel>> readAllUnapprovedChallenges() {
        return new ResponseEntity<List<ChallengeModel>>(challengeService.getAllUnapprovedChallengesFromDatabase(), HttpStatus.OK);
    }


    @CrossOrigin
    @RequestMapping(value = "/challenge/", method = RequestMethod.PUT)
    public ResponseEntity<ChallengeModel> updateChallenge(@RequestBody ChallengeModel challengeModel) {
        challengeService.updateChallengeInDatabase(challengeModel);
        return new ResponseEntity<ChallengeModel>(challengeModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/update-challenge-claimer/", method = RequestMethod.PUT)
    public ResponseEntity<ChallengeModel> updateChallengeClaimer(@PathVariable Long id, @RequestBody UserModel userModel) {
        ChallengeModel challengeModel = challengeService.getChallengeFromDatabase(id);
        challengeModel.setChallengeClaimer(userModel);
        challengeModel.setChallengeClaimed(true);
        challengeService.updateChallengeInDatabase(challengeModel);
        return new ResponseEntity<ChallengeModel>(challengeModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/add-youtube-url/", method = RequestMethod.PUT)
    public ResponseEntity<ChallengeModel> addYoutubeUrlToChallenge(@PathVariable Long id, @RequestBody String youtubeUrl) {
        ChallengeModel challengeModel = challengeService.getChallengeFromDatabase(id);
        challengeModel.setYoutubeURL(youtubeUrl);
        challengeModel.setYoutubeUrlProvided(true);
        challengeService.updateChallengeInDatabase(challengeModel);
        return new ResponseEntity<ChallengeModel>(challengeModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/assign-points-to-user/", method = RequestMethod.PUT)
    public ResponseEntity<ChallengeModel> assignPointsToUser(@PathVariable Long id) {
        ChallengeModel challenge = challengeService.getChallengeFromDatabase(id);
        UserModel challengeCompleter = userService.getUserFromDatabase(challenge.getChallengeClaimer().getId());
        UserModel challengeCreator = userService.getUserFromDatabase(challenge.getChallengeCreator().getId());

        Long pointsToDistribute = (long) challenge.getChallengeUpvoters().size();

        challengeCreator.addCreatedChallengePoints(pointsToDistribute);
        challengeCompleter.addCompletedChallengePoints(pointsToDistribute);
        challenge.addPoints(pointsToDistribute);

        updateChallengeToCompleted(challenge);
        challenge.setChallengeUpvoters(new ArrayList<UserModel>());

        userService.updateUserInDatabase(challengeCompleter);
        userService.updateUserInDatabase(challengeCreator);
        challengeService.updateChallengeInDatabase(challenge);

        return new ResponseEntity<ChallengeModel>(challenge, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/disapprove-challenge/", method = RequestMethod.PUT)
    public ResponseEntity<ChallengeModel> disapproveChallenge(@PathVariable Long id) {
        ChallengeModel challengeModel = challengeService.getChallengeFromDatabase(id);

        challengeModel.setYoutubeURL(null);
        challengeModel.setChallengeClaimed(false);
        challengeModel.setChallengeClaimer(null);
        challengeModel.setYoutubeVideoUploaded(false);
        challengeModel.setYoutubeUrlProvided(false);
        challengeModel.setYoutubeVideoUploaded(true);
        challengeModel.setChallengeDisapproved(true);

        challengeService.updateChallengeInDatabase(challengeModel);
        return new ResponseEntity<ChallengeModel>(challengeModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/confirm-uploaded-youtube-url/", method = RequestMethod.PUT)
    public ResponseEntity<ChallengeModel> confirmUploadedYoutubeUrl(@PathVariable Long id) {
        ChallengeModel challenge = challengeService.getChallengeFromDatabase(id);
        if (challenge.getYoutubeVideoUploaded() == false) {
            challenge.setYoutubeVideoUploaded(true);
            challenge.setYoutubeUrlProvided(false);

            challengeService.updateChallengeInDatabase(challenge);
            return new ResponseEntity<ChallengeModel>(challenge, HttpStatus.OK);
        } else {
            // TODO lägga logger här.
            return new ResponseEntity<ChallengeModel>(challenge, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/add-or-remove-user-to-challenge-upvoters/", method = RequestMethod.PUT)
    public ResponseEntity addOrRemoveUserToChallengeUpvoters(@PathVariable Long id, @RequestBody UserModel loggedInUser) {
        UserModel user = userService.getUserFromDatabase(loggedInUser.getId());
        ChallengeModel challenge = challengeService.getChallengeFromDatabase(id);

        if (challenge.getChallengeUpvoters().contains(user.getId())) {
            challenge.removeUserModelFromChallengeUpvoters(user);
            challenge.removeUpvote();
        } else {
            challenge.addUpvote();
            challenge.addUserModelToChallengeUpvoters(user);

            createAndSaveNotification(user, challenge, Action.UPVOTE);
        }

        challengeService.updateChallengeInDatabase(challenge);
        return new ResponseEntity(HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/add-or-remove-point-to-completed-challenge/", method = RequestMethod.PUT)
    public ResponseEntity addOrRemovePointToCompletedChallenge(@PathVariable Long id, @RequestBody UserModel loggedInUser) {
        ChallengeModel challenge = challengeService.getChallengeFromDatabase(id);
        UserModel user = userService.getUserFromDatabase(loggedInUser.getId());

        if (challenge.getChallengeUpvoters().contains(user.getId())) {
            challenge.removeUserModelFromChallengeUpvoters(user);
            removePointsFromUsers(challenge);
        } else {
            challenge.addUserModelToChallengeUpvoters(user);
            addPointsToUsers(challenge);
        }

        challengeService.updateChallengeInDatabase(challenge);
        return new ResponseEntity(HttpStatus.OK);
    }

   /* @CrossOrigin
    @RequestMapping(value = "/challenge/{challengeId}/add-comment/", method = RequestMethod.PUT)
    public ResponseEntity addCommentToChallenge(@RequestBody CommentModel commentModel, @PathVariable Long challengeId) {
        *//*ChallengeModel challengeModel = challengeService.getChallengeFromDatabase(challengeId);
        challengeModel.addCommentToChallenge(commentModel);
        *//*

        commentController.addChallengeToComment(commentModel, challengeId);

        //todo HIT FÖRST och fixa controller för comments så vi kan göra som tidigare logik i andra controllers

*//*        challengeService.updateChallengeInDatabase(challengeModel);*//*
        return new ResponseEntity(HttpStatus.CREATED);
    }*/

    private void updateChallengeToCompleted(ChallengeModel challenge) {
        challenge.setChallengeCompleted(true);
        challenge.setYoutubeUrlProvided(false);
        challenge.setYoutubeVideoUploaded(false);
    }

    private void removePointsFromUsers(ChallengeModel challenge) {
        UserModel challengeCompleter = userService.getUserFromDatabase(challenge.getChallengeClaimer().getId());
        UserModel challengeCreator = userService.getUserFromDatabase(challenge.getChallengeCreator().getId());

        challenge.removePoint();
        challengeCompleter.removeCompletedChallengePoint();
        challengeCreator.removeCreatedChallengePoint();

        userService.updateUserInDatabase(challengeCompleter);
        userService.updateUserInDatabase(challengeCreator);
    }

    private void addPointsToUsers(ChallengeModel challenge) {
        UserModel challengeCompleter = userService.getUserFromDatabase(challenge.getChallengeClaimer().getId());
        UserModel challengeCreator = userService.getUserFromDatabase(challenge.getChallengeCreator().getId());

        challenge.addPoints(1L);
        challengeCompleter.addCompletedChallengePoints(1L);
        challengeCreator.addCreatedChallengePoints(1L);

        userService.updateUserInDatabase(challengeCompleter);
        userService.updateUserInDatabase(challengeCreator);
    }

    private void createAndSaveNotification(UserModel user, ChallengeModel challenge, Action action) {
        NotificationModel notificationModel = new NotificationModel(user, challenge, action);
        notificationController.createNotification(notificationModel);
    }

}