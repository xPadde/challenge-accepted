package com.challengeaccepted.controllers;

import com.challengeaccepted.models.CommentModel;
import com.challengeaccepted.services.ChallengeService;
import com.challengeaccepted.services.CommentService;
import com.challengeaccepted.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class CommentControllerTest {

    @Mock
    ChallengeService mockedChallengeService;
    @Mock
    CommentService mockedCommentService;
    @Mock
    UserService mockedUserService;

    @InjectMocks
    CommentController commentController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddChallengeToComment_Should_Return_Status_Code_200() throws Exception {
        assertEquals("addChallengeToComment() did not respond with http status 201 (created)", HttpStatus.CREATED, commentController.addChallengeToComment(new CommentModel(), new Random().nextLong()).getStatusCode());
    }

}