package challengeaccepted.controllers;

import com.challengeaccepted.controllers.ChallengeController;
import com.challengeaccepted.models.ChallengeModel;
import com.challengeaccepted.services.ChallengeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ChallengeControllerTest {

    @Mock
    private ChallengeService challengeService;

    @InjectMocks
    private ChallengeController challengeController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateChallenge_Should_Return_Status_Code_201() throws Exception {
        assertEquals("createChallenge() did not respond with http status 201 (created)", HttpStatus.CREATED, challengeController.createChallenge(new ChallengeModel()).getStatusCode());
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
    public void readAllCompletedChallenges_Should_Return_Status_Code_200() throws Exception {
        assertEquals("readAllCompletedChallenges() did not respond with http status 200 (ok)", HttpStatus.OK, challengeController.readAllCompletedChallenges().getStatusCode());
    }

//    @Test
//    public void testUpdateChallenge() throws Exception {
//        assertEquals("updateChallenge() did not respond with http status 200 (ok)", new ResponseEntity(HttpStatus.OK), challengeController.updateChallenge(new Long(new Random().nextLong())));
//    }
//
//    @Test
//    public void testDeleteChallenge() throws Exception {
//        assertEquals("deleteChallenge() did not respond with http status 200 (ok)", new ResponseEntity(HttpStatus.OK), challengeController.deleteChallenge(new Long(new Random().nextLong())));
//    }

}