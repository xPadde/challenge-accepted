package com.challengeaccepted.controllers;

import com.challengeaccepted.models.ChallengeModel;
import com.challengeaccepted.models.CommentModel;
import com.challengeaccepted.models.UserModel;
import com.challengeaccepted.services.ChallengeService;
import com.challengeaccepted.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ChallengeControllerTest {

    @Mock
    private ChallengeService mockedChallengeService;
    @Mock
    private UserService mockedUserService;
    @Mock
    private CommentController mockedCommentController;
    @Mock
    private ChallengeModel mockedChallenge;
    @Mock
    private UserModel mockedUserModel;

    @InjectMocks
    private ChallengeController challengeController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(mockedChallengeService.getChallengeFromDatabase(1L)).thenReturn(new ChallengeModel());
        when(mockedUserService.getUserFromDatabase(1L)).thenReturn(new UserModel());
        when(mockedChallenge.getChallengeClaimer()).thenReturn(new UserModel());
    }

    @Test
    public void testCreateChallenge_Should_Return_Status_Code_201() throws Exception {
        assertEquals("createChallenge() did not respond with http status 201 (created)", HttpStatus.CREATED, challengeController.createChallenge(new ChallengeModel(), new Random().nextLong()).getStatusCode());
    }

    @Test
    public void testReadChallenge_Should_Return_Status_Code_200() throws Exception {
        assertEquals("readChallenge() did not respond with http status 200 (ok)", HttpStatus.OK, challengeController.readChallenge(new Random().nextLong()).getStatusCode());
    }

    @Test
    public void testReadAllChallenges_Should_Return_Status_Code_200() throws Exception {
        assertEquals("readAllChallenges() did not respond with http status 200 (ok)", HttpStatus.OK, challengeController.readAllChallenges().getStatusCode());
    }

    @Test
    public void testReadAllCompletedChallenges_Should_Return_Status_Code_200() throws Exception {
        assertEquals("readAllCompletedChallenges() did not respond with http status 200 (ok)", HttpStatus.OK, challengeController.readAllCompletedChallenges().getStatusCode());
    }

    @Test
    public void testReadAllUnapprovedChallenges_Should_Return_Status_Code_200() throws Exception {
        assertEquals("readAllUnapprovedChallenges() did not respond with http status 200 (ok)", HttpStatus.OK, challengeController.readAllUnapprovedChallenges().getStatusCode());
    }

    @Test
    public void testUpdateChallenge_Should_Return_Status_Code_200() throws Exception {
        assertEquals("updateChallenge() did not respond with http status 200 (ok)", HttpStatus.OK, challengeController.updateChallenge(new ChallengeModel()).getStatusCode());
    }

    @Test
    public void testUpdateChallengeClaimer_Should_Return_Status_Code_200() throws Exception {
        assertEquals("updateChallengeClaimer() did not respons with http status 200 (ok)", HttpStatus.OK, challengeController.updateChallengeClaimer(1L, new UserModel()).getStatusCode());
    }

    @Test
    public void testAddYoutubeUrlToChallenge_Should_Return_Status_Code_200() throws Exception {
        assertEquals("addYoutubeUrlToChallenge() did not respond with http status 200 (ok)", HttpStatus.OK, challengeController.addYoutubeUrlToChallenge(1L, "YouTubeURL").getStatusCode());
    }

    @Test
    public void testAssignPointsToUser_Should_Return_Status_Code_200() throws Exception {
        assertEquals("assignPoinstToUser() did not respond with http status 200 (ok)", HttpStatus.OK, challengeController.assignPointsToUser(1L).getStatusCode());
    }

    @Test
    public void testDisapproveChallenge_Should_Return_Status_Code_200() throws Exception {
        assertEquals("disapproveChallenge() did not respond with http status 200 (ok)", HttpStatus.OK, challengeController.disapproveChallenge(1L).getStatusCode());
    }

    @Test
    public void testConfirmUploadedYoutubeUrl_Should_Return_Status_Code_200() throws Exception {
        assertEquals("confirmUploadedYoutubeUrl() did not respond with http status 200 (ok)", HttpStatus.OK, challengeController.confirmUploadedYoutubeUrl(1L).getStatusCode());
    }

    @Test
    public void testAddUserToChallengeUpvoters_Should_Return_Status_Code_200() throws Exception {
        assertEquals("addUserToChallengeUpvoters() did not throw NullPointerException", HttpStatus.OK, challengeController.addOrRemoveUserToChallengeUpvoters(1L, new UserModel()).getStatusCode());
    }

    @Test
    public void testAddCommentToChallenge_Should_Return_Status_Code_201() throws Exception {
        assertEquals("addCommentToChallenge() did not respond with http status 201 (created)", HttpStatus.CREATED, challengeController.addCommentToChallenge(new CommentModel(), new Random().nextLong()).getStatusCode());
    }
}