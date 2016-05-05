package com.challengeaccepted.controllers;

import com.challengeaccepted.models.ChallengeModel;
import com.challengeaccepted.models.UserModel;
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

    @CrossOrigin
    @RequestMapping(value = "/challenge/", method = RequestMethod.POST)
    public ResponseEntity createChallenge(@RequestBody ChallengeModel challengeModel) {
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
    @RequestMapping(value = "/challenge/", method = RequestMethod.PUT)
    public ResponseEntity updateChallenge(@RequestBody ChallengeModel challengeModel) {
        challengeService.updateChallengeInDatabase(challengeModel);
        return new ResponseEntity(HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/", method = RequestMethod.PUT)
    public ResponseEntity addUserToChallengeUpvoters(@PathVariable Long id, @RequestBody ChallengeModel challenge) {
        UserModel loggedInUser = userService.getUserFromDatabase(id);

        challenge.addUserModelToChallengeUpvoters(loggedInUser);
        challengeService.updateChallengeInDatabase(challenge);
        return new ResponseEntity(HttpStatus.OK);
    }

}