package challengeaccepted.controllers;

import com.challengeaccepted.controllers.UserController;
import com.challengeaccepted.models.UserModel;
import com.challengeaccepted.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser_Should_Return_Status_Code_201() throws Exception {
        assertEquals("createUser() did not respond with http status 201 (created)", HttpStatus.CREATED, userController.createUser(new UserModel()).getStatusCode());
    }

    @Test
    public void testReadUser_Should_Return_Status_Code_200() throws Exception {
        assertEquals("readUser() did not respond with http status 200 (ok)", HttpStatus.OK, userController.readUser(new Random().nextLong()).getStatusCode());
    }

/*    @Test
    public void testDeleteUser_Should_Return_Status_Code_200() throws Exception {
        assertEquals("deleteUser() did not respond with http status 200 (delete)", HttpStatus.OK, userController.deleteUser(new UserModel()).getStatusCode());
    }*/

}
