package com.challengeaccepted.controllers;


import com.challengeaccepted.models.ChallengeModel;
import com.challengeaccepted.models.NewsFeedModel;
import com.challengeaccepted.services.ChallengeService;
import com.challengeaccepted.services.NewsFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class NewsFeedController {

    @Autowired
    private NewsFeedService newsFeedService;
    @Autowired
    private ChallengeService challengeService;
    private Long newsFeedId = 1L;
    public NewsFeedController() {
    }

    @CrossOrigin
    @RequestMapping(value = "/newsfeed/", method = RequestMethod.POST)
    public ResponseEntity createNewsFeed(@RequestBody NewsFeedModel newsFeedModel){
        newsFeedService.saveNewsFeedToDatabase(newsFeedModel);
        return new ResponseEntity(HttpStatus.CREATED);

    }

    @CrossOrigin
    @RequestMapping(value = "/newsfeed/addLikedChallenge/", method = RequestMethod.POST)
    public ResponseEntity addLikedChallenge(@RequestBody ChallengeModel challengeModel){

        ChallengeModel challengeModelFromDatabase = challengeService.getChallengeFromDatabase(challengeModel.getId());
        NewsFeedModel newsFeedModelFromDatabase = newsFeedService.getNewsFeedFromDatabase(newsFeedId);

        challengeModelFromDatabase.setLikedChallengeInNewsFeed(newsFeedModelFromDatabase);
        challengeService.updateChallengeInDatabase(challengeModelFromDatabase);
        return new ResponseEntity(HttpStatus.OK);

        /*NewsFeedModel newsFeedModelFromDatabase = newsFeedService.getNewsFeedFromDatabase(newsFeedId);

        if(newsFeedModelFromDatabase != null){
            newsFeedModelFromDatabase.addLikedChallenge(challengeModel);
            newsFeedService.updateNewsFeedToDatabase(newsFeedModelFromDatabase);
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);*/
    }

    /*@CrossOrigin
    @RequestMapping(value = "/newsfeed/getLikedchallenges/", method = RequestMethod.GET)
    public ResponseEntity<List<ChallengeModel>> getLikedChallenges(){
        *//*NewsFeedModel newsFeedModelFromDatabase = newsFeedService.getNewsFeedFromDatabase(newsFeedId);
        List<ChallengeModel> likedChallenges = newsFeedModelFromDatabase.getLikedChallenges();

        System.out.println(likedChallenges);

        return new ResponseEntity<List<ChallengeModel>>(likedChallenges, HttpStatus.ACCEPTED);*//*
    }*/
}
