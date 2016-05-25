package com.challengeaccepted.controllers;

import com.challengeaccepted.models.ChallengeModel;
import com.challengeaccepted.models.UserModel;
import com.challengeaccepted.services.ChallengeService;
import com.challengeaccepted.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
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
    private NotificationController mockedNotificationController;
    @Mock
    private ChallengeModel mockedChallenge;
    @Mock
    private UserModel mockedUserModel;
    @Mock
    private UserModel mockedChallengeCreator;

    @InjectMocks
    private ChallengeController challengeController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(mockedChallengeService.getChallengeFromDatabase(1L)).thenReturn(mockedChallenge);
        when(mockedUserService.getUserFromDatabase(1L)).thenReturn(mockedUserModel);
        when(mockedChallenge.getChallengeCreator()).thenReturn(mockedUserModel);
    }

    @Test
    public void testCreateChallenge() throws Exception {
        when(mockedChallenge.getTopic()).thenReturn("");
        when(mockedChallenge.getDescription()).thenReturn("");
        assertEquals("createChallenge() did not respond with http status 204 (no content)", HttpStatus.NO_CONTENT, challengeController.createChallenge(mockedChallenge, new Random().nextLong()).getStatusCode());
        when(mockedChallenge.getTopic()).thenReturn("Topic");
        when(mockedChallenge.getDescription()).thenReturn("Description");
        assertEquals("createChallenge() did not respond with http status 201 (created)", HttpStatus.CREATED, challengeController.createChallenge(mockedChallenge, new Random().nextLong()).getStatusCode());
    }

    @Test
    public void testReadChallenge_Should_Return_Status_Code_200() throws Exception {
        assertEquals("readChallenge() did not respond with http status 200 (ok)", HttpStatus.OK, challengeController.readChallenge(1L, mockedUserModel.getId()).getStatusCode());
    }

    /*@Test
    public void testValidateUserRestrictions_Should_Return_Status_Code_200() throws Exception {
        assertEquals("validateUserRestrictions() did not respond with http status 200 (ok)", HttpStatus.OK, challengeController.validateUserRestrictions(mockedChallenge, mockedUserModel));
    }*/

    @Test
    public void testIsLoggedInUserTheCreatorAndIsVideoUploaded_Should_Return_True_Or_False() throws Exception {
        when(mockedUserModel.getId()).thenReturn(1L);
        when(mockedChallenge.getChallengeCreator().getId()).thenReturn(1L);
        when(mockedChallenge.getYoutubeVideoUploaded()).thenReturn(true);
        assertEquals("isChallengeUnavailableForUserNotSignedIn() did not respond with true or false", true, challengeController.isLoggedInUserTheCreatorAndIsVideoUploaded(mockedChallenge, mockedUserModel));
        when(mockedChallenge.getYoutubeVideoUploaded()).thenReturn(false);
        assertEquals("isChallengeUnavailableForUserNotSignedIn() did not respond with true or false", false, challengeController.isLoggedInUserTheCreatorAndIsVideoUploaded(mockedChallenge, mockedUserModel));
    }

    @Test
    public void testIsChallengeUnavailableForUserNotSignedIn_Should_Return_True_Or_False() throws Exception {
        when(mockedChallenge.getChallengeClaimed()).thenReturn(true);
        when(mockedChallenge.getChallengeCompleted()).thenReturn(false);
        assertEquals("isChallengeUnavailableForUserNotSignedIn() did not respond with true or false", true, challengeController.isChallengeUnavailableForUserNotSignedIn(mockedChallenge, null));
        assertEquals("isChallengeUnavailableForUserNotSignedIn() did not respond with true or false", false, challengeController.isChallengeUnavailableForUserNotSignedIn(mockedChallenge, mockedUserModel));
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
    public void testUpdateChallengeClaimer() throws Exception {
        when(mockedUserModel.getId()).thenReturn(1L);
        when(mockedChallenge.getChallengeCreator()).thenReturn(mockedChallengeCreator);
        assertEquals("updateChallengeClaimer() did not respond with http status 200 (ok)", HttpStatus.OK, challengeController.updateChallengeClaimer(1L, mockedUserModel).getStatusCode());
        // Set the two mocked users to the same id, should return a bad request http status.
        when(mockedChallenge.getChallengeCreator().getId()).thenReturn(1L);
        assertEquals("updateChallengeClaimer() did not respond with http status 400 (bad request)", HttpStatus.BAD_REQUEST, challengeController.updateChallengeClaimer(1L, mockedUserModel).getStatusCode());
    }

    @Test
    public void testAddYoutubeUrlToChallenge_Should_Return_Status_Code_200() throws Exception {
        assertEquals("addYoutubeUrlToChallenge() did not respond with http status 200 (ok)", HttpStatus.OK, challengeController.addYoutubeUrlToChallenge(1L, "YouTubeURL").getStatusCode());
    }

    @Test
    public void testAssignPointsToUser_Should_Return_Status_Code_200() throws Exception {
        when(mockedChallenge.getChallengeClaimer()).thenReturn(new UserModel());
        when(mockedChallenge.getChallengeCreator()).thenReturn(new UserModel());
        when(mockedUserService.getUserFromDatabase(mockedChallenge.getChallengeCreator().getId())).thenReturn(new UserModel());
        assertEquals("assignPoinstToUser() did not respond with http status 200 (ok)", HttpStatus.OK, challengeController.assignPointsToUser(1L).getStatusCode());
    }

    @Test
    public void testDisapproveChallenge_Should_Return_Status_Code_200() throws Exception {
        assertEquals("disapproveChallenge() did not respond with http status 200 (ok)", HttpStatus.OK, challengeController.disapproveChallenge(1L, null).getStatusCode());
    }

    @Test
    public void testConfirmUploadedYoutubeUrl() throws Exception {
        assertEquals("confirmUploadedYoutubeUrl() did not respond with http status 200 (ok)", HttpStatus.OK, challengeController.confirmUploadedYoutubeUrl(1L).getStatusCode());
        when(mockedChallenge.getYoutubeVideoUploaded()).thenReturn(true);
        assertEquals("confirmUploadedYoutubeUrl() did not respond with http status 400 (bad request)", HttpStatus.BAD_REQUEST, challengeController.confirmUploadedYoutubeUrl(1L).getStatusCode());
    }

    @Test()
    public void testAddOrRemoveUserToChallengeUpvoters_Should_Return_Status_Code_200() throws Exception {
        when(mockedUserModel.getId()).thenReturn(1L);
        when(mockedUserService.getUserFromDatabase(1L)).thenReturn(new UserModel());
        assertEquals("addUserToChallengeUpvoters() did not respond with http status 200 (ok)", HttpStatus.OK, challengeController.addOrRemoveUserToChallengeUpvoters(1L, mockedUserModel).getStatusCode());
    }

    @Test
    public void addOrRemovePointToCompletedChallenge() throws Exception {
        // TODO Write Test
    }

}