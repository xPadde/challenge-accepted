package com.challengeaccepted.controllers;

import com.challengeaccepted.models.ChallengeModel;
import com.challengeaccepted.models.CommentModel;
import com.challengeaccepted.models.UserModel;
import com.challengeaccepted.services.ChallengeService;
import com.challengeaccepted.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentController commentController;
    private Long resetUpvotes = 0l;

    @CrossOrigin
    @RequestMapping(value = "/challenge/create/challengecreator/{challengeCreatorId}", method = RequestMethod.POST)
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
    @RequestMapping(value = "/challenge/{id}/updatechallengeclaimer/", method = RequestMethod.PUT)
    public ResponseEntity<ChallengeModel> updateChallengeClaimer(@PathVariable Long id, @RequestBody UserModel userModel) {
        ChallengeModel challengeModel = challengeService.getChallengeFromDatabase(id);
        challengeModel.setChallengeClaimer(userModel);
        challengeModel.setChallengeClaimed(true);
        challengeService.updateChallengeInDatabase(challengeModel);
        return new ResponseEntity<ChallengeModel>(challengeModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/addyoutubeurl/", method = RequestMethod.PUT)
    public ResponseEntity<ChallengeModel> addYoutubeUrlToChallenge(@PathVariable Long id, @RequestBody String youtubeUrl) {
        ChallengeModel challengeModel = challengeService.getChallengeFromDatabase(id);
        challengeModel.setYoutubeURL(youtubeUrl);
        challengeModel.setYoutubeUrlProvided(true);
        challengeService.updateChallengeInDatabase(challengeModel);
        return new ResponseEntity<ChallengeModel>(challengeModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/assignpointstouser/", method = RequestMethod.PUT)
    public ResponseEntity<ChallengeModel> assignPointsToUser(@PathVariable Long id) {
        ChallengeModel challengeToUpdateToDatabase = challengeService.getChallengeFromDatabase(id);

        UserModel challengeCompleter = userService.getUserFromDatabase(challengeToUpdateToDatabase.getChallengeClaimer().getId());
        UserModel challengeCreator = userService.getUserFromDatabase(challengeToUpdateToDatabase.getChallengeCreator().getId());

        Long pointsToDistribute = (long) challengeToUpdateToDatabase.getChallengeUpvoters().size();

        challengeCreator.addCreatedChallengePoints(pointsToDistribute / 2);
        challengeCompleter.addCompletedChallengePoints(pointsToDistribute);

        challengeToUpdateToDatabase.setUpvotes(resetUpvotes);
        challengeToUpdateToDatabase.setChallengeCompleted(true);
        challengeToUpdateToDatabase.setYoutubeUrlProvided(false);
        challengeToUpdateToDatabase.setYoutubeVideoUploaded(false);

        userService.updateUserInDatabase(challengeCompleter);
        userService.updateUserInDatabase(challengeCreator);
        challengeService.updateChallengeInDatabase(challengeToUpdateToDatabase);

        System.out.println("LOGS BABY");
        System.out.println(challengeCompleter);
        System.out.println(challengeCreator);
        System.out.println(challengeToUpdateToDatabase);

        return new ResponseEntity<ChallengeModel>(challengeToUpdateToDatabase, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/disapprovechallenge/", method = RequestMethod.PUT)
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
    @RequestMapping(value = "/challenge/{id}/confirmuploadedyoutubeurl/", method = RequestMethod.PUT)
    public ResponseEntity<ChallengeModel> confirmUploadedYoutubeUrl(@PathVariable Long id) {
        ChallengeModel challengeModel = challengeService.getChallengeFromDatabase(id);
        if (challengeModel.getYoutubeVideoUploaded() == false) {
            challengeModel.setYoutubeVideoUploaded(true);
            challengeModel.setYoutubeUrlProvided(false);
            challengeService.updateChallengeInDatabase(challengeModel);
            return new ResponseEntity<ChallengeModel>(challengeModel, HttpStatus.OK);
        } else {
            // TODO lägga logger här.
            return new ResponseEntity<ChallengeModel>(challengeModel, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/", method = RequestMethod.PUT)
    public ResponseEntity addUserToChallengeUpvoters(@PathVariable Long id, @RequestBody UserModel loggedInUser) {

        ChallengeModel challenge = challengeService.getChallengeFromDatabase(id);
        challenge.addUpvote(1l);

        challenge.addUserModelToChallengeUpvoters(loggedInUser);
        challengeService.updateChallengeInDatabase(challenge);
        return new ResponseEntity(HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{challengeId}/addcomment/", method = RequestMethod.PUT)
    public ResponseEntity addCommentToChallenge(@RequestBody CommentModel commentModel, @PathVariable Long challengeId) {
        /*ChallengeModel challengeModel = challengeService.getChallengeFromDatabase(challengeId);
        challengeModel.addCommentToChallenge(commentModel);
        */

        commentController.addChallengeToComment(commentModel, challengeId);

        //todo HIT FÖRST och fixa controller för comments så vi kan göra som tidigare logik i andra controllers

/*        challengeService.updateChallengeInDatabase(challengeModel);*/
        return new ResponseEntity(HttpStatus.CREATED);
    }

}