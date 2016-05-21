app.controller('LoginController', ['$scope', '$route', '$location', 'userService', 'scopeService', function ($scope, $route, $location, userService, scopeService) {

    $scope.absoluteUrl = $location.absUrl();
    $scope.mailtoSubject = "You%20have%20been%20Challenged!";
    $scope.mailtoBody = "A%20friend%20of%20yours%20challenge%20you.%20Do%20you%20accept?%0AGo%20to:%20" + $scope.absoluteUrl;

    /*
     Below Code handles the user login.
     */
    $scope.getUserInfo = function (profile) {
        return JSON.stringify({
            'firstName': profile.getGivenName(),
            'lastName': profile.getFamilyName(),
            'email': profile.getEmail()
        });
    };

    $scope.setLoggedInUser = function (response) {
        if (response == "") {
            userService.createNewUser($scope.loggedInUser)
                .success(function (response) {
                    console.log('Login success! The user NOT found in database and successfully saved to the database!');
                    sessionStorage.setItem('loggedInUser', JSON.stringify(response));
                    sessionStorage.setItem('isLoggedIn', true);
                })
                .error(function (error) {
                    console.log('The user NOT found in database but ***FAILED*** to save to the database!');
                    console.log(error);
                });
        } else {
            console.log("Login success! The user already found in database! Proceeding...");
            sessionStorage.setItem('loggedInUser', JSON.stringify(response));
            sessionStorage.setItem('isLoggedIn', true);
        }
    };

    onSignIn = function (googleUser) {
        var profile = googleUser.getBasicProfile();
        var userFoundByEmail = "";
        $scope.loggedInUser = JSON.parse($scope.getUserInfo(profile));

        userService.getUserByEmail($scope.loggedInUser.email)
            .success(function (response) {
                userFoundByEmail = response;
            })
            .error(function (error) {
                console.log("userService.getUserByEmail ERROR!");
                console.log(error);
            }).then(function () {
            $scope.setLoggedInUser(userFoundByEmail);
        });
    };

    $scope.signOut = function () {
        var auth2 = gapi.auth2.getAuthInstance();
        auth2.signOut().then(function () {
            sessionStorage.setItem("loggedInUser", null);
            sessionStorage.setItem("isLoggedIn", false);
            console.log("Auth2 isSignedIn?: " + auth2.isSignedIn.get());
            window.location.reload();
        });
    };

    $scope.viewUserProfilePage = function (user) {
        scopeService.viewUserProfilePage(user);
    };

    /*
     Below code handles the toggling of the different sections in index.html.
     The methods is assigned to the ng-clicks.
     */
    $scope.showCreateChallenge = function () {
        if (sessionStorage.getItem("isLoggedIn") == 'true') {
            $location.path('/create-challenge');
        } else {
            scopeService.showAlertPopup(scopeService.loginAlertMessage());
        }
    };
}]);