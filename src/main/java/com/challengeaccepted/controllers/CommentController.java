package com.challengeaccepted.controllers;

import com.challengeaccepted.models.ChallengeModel;
import com.challengeaccepted.models.CommentModel;
import com.challengeaccepted.services.ChallengeService;
import com.challengeaccepted.services.CommentService;
import com.challengeaccepted.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class CommentController {

    @Autowired
    ChallengeService challengeService;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;

    @CrossOrigin
    @RequestMapping(value = "/challenge/{challengeid}/comment/", method = RequestMethod.POST)
    public ResponseEntity addChallengeToComment(@RequestBody CommentModel commentModel, @PathVariable Long challengeid){
        ChallengeModel challengeToPutInComment = challengeService.getChallengeFromDatabase(challengeid);
        commentModel.setCommentedChallenge(challengeToPutInComment);

        commentService.saveCommentToDatabase(commentModel);
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
