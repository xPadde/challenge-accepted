package com.challengeaccepted.controllers;


import com.challengeaccepted.models.NewsFeedModel;
import com.challengeaccepted.services.NewsFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class NewsFeedController {

    @Autowired
    private NewsFeedService newsFeedService;

    public NewsFeedController() {
    }

    @CrossOrigin
    @RequestMapping(value = "/newsfeed/", method = RequestMethod.POST)
    public ResponseEntity createNewsFeed(@RequestBody NewsFeedModel newsFeedModel){

        /*NewsFeedModel newsFeedModelToCheckWithDatabase = newsFeedService.getNewsFeedFromDatabase(1l);*/

        newsFeedService.saveNewsFeedToDatabase(newsFeedModel);
        return new ResponseEntity(HttpStatus.CREATED);
        /*if(newsFeedModelToCheckWithDatabase == null){
            newsFeedService.saveNewsFeedToDatabase(newsFeedModel);
            return new ResponseEntity(HttpStatus.CREATED);
        }else{
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }*/
    }
}
