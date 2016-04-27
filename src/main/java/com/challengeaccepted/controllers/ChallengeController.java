package com.challengeaccepted.controllers;

import com.challengeaccepted.models.ChallengeModel;
import com.challengeaccepted.services.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Stridsberg on 2016-04-27.
 */
@RestController
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;

    @CrossOrigin
    @RequestMapping(value ="/challenge/", method = RequestMethod.POST)
    public void createChallenge(@RequestBody ChallengeModel challengeModel) {
        challengeService.createChallenge(challengeModel);
    }
}
