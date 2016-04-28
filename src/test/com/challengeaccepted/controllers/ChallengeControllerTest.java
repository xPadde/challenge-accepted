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
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

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
    public void testCreateChallenge() throws Exception {
        assertEquals("createChallenge() did not respond with http status 201 (created)", new ResponseEntity(HttpStatus.CREATED), challengeController.createChallenge(new ChallengeModel()));
    }

    @Test
    public void testReadChallenge() throws Exception {
        assertEquals("readChallenge() did not respond with http status 200 (ok)", new ResponseEntity(HttpStatus.OK), challengeController.readChallenge(new Random().nextLong()));
    }

    @Test
    public void testReadAllChallenges() throws Exception {
        String expectedStatusCode = "200";
        assertEquals("readAllChallenges() did not respond with http status 200 (ok)", expectedStatusCode, challengeController.readAllChallenges().getStatusCode().toString());

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