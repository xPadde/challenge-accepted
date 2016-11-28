app.controller('LoginController', ['$scope', '$route', '$log', '$location', 'userService', 'scopeService',
    function ($scope, $route, $log, $location, userService, scopeService) {

        $scope.absoluteUrl = $location.absUrl();
        $scope.mailtoSubject = "You%20have%20been%20Challenged!";
        $scope.mailtoBody = "A%20friend%20of%20yours%20challenge%20you.%20Do%20you%20accept?%0AGo%20to:%20" + $scope.absoluteUrl;

        /*
         Below Code handles the user login.
         */

        $scope.getLoginInfo = function () {
            return JSON.stringify({
                'email': $('#input-email-login').val(),
                'password': $('#input-password-login').val(),
                'otp': $('#input-otp-login').val()
            });
        };

        $scope.login = function() {
            var user = JSON.parse($scope.getLoginInfo());

            console.log("inne i controllern");

           userService.validateLocalLogin(user)
               .success(function(response) {
                   $scope.loggedInUser = response;
                   $scope.setLoggedInUser($scope.loggedInUser);

               })
               .error(function(response) {
                   console.log("vi är inne i error");
               }).then(function () {
               $scope.setLoggedInUser($scope.loggedInUser);
               console.log($scope.loggedInUser);
               console.log('---------');
               sessionStorage.setItem('loggedInUser', JSON.stringify($scope.loggedInUser));
               console.log(scopeService.getLoggedInUser());
           })
        };

        $scope.setLoggedInUser = function (response) {
            if (response == "") {
                userService.createNewUser($scope.loggedInUser)
                    .success(function (response) {
                        $log.info('Login success! The user NOT found in database and successfully saved to the database!');
                        sessionStorage.setItem('loggedInUser', JSON.stringify(response));
                        sessionStorage.setItem('isLoggedIn', true);
                    })
                    .error(function (error) {
                        $log.error('The user NOT found in database but ***FAILED*** to save to the database!');
                        $log.error(error);
                    });
            } else {
                $log.info("Login success! The user already found in database! Proceeding...");
                sessionStorage.setItem('loggedInUser', JSON.stringify(response));
                sessionStorage.setItem('isLoggedIn', true);
            }
        };

        onSignIn = function (googleUser) {
            var idToken = googleUser.getAuthResponse().id_token;

            userService.createGoogleUser(idToken)
                .success(function (response) {
                    $scope.loggedInUser = response;
                    sessionStorage.setItem('loggedInUser', JSON.stringify(response));
                    sessionStorage.setItem('isLoggedIn', true);
                })
                .error(function (error) {
                    $log.error("userService.getUserByEmail ERROR!");
                    $log.error(error);
                });
        };

        $scope.signOut = function () {
            var auth2 = gapi.auth2.getAuthInstance();
            auth2.signOut().then(function () {
                sessionStorage.setItem("loggedInUser", null);
                sessionStorage.setItem("isLoggedIn", false);
                $log.debug("Auth2 isSignedIn?: " + auth2.isSignedIn.get());
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