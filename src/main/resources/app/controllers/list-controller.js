app.controller('ListController', ['scopeService', 'challengeService', 'userService', '$sce', '$scope',
    function (scopeService, challengeService, userService, $sce, $scope) {
        
        $scope.loggedInUser = scopeService.getLoggedInUser();

        /*
         * Functions for challenges in lists
         * */
        $scope.viewChallengeProfilePage = function (challenge) {
            scopeService.viewChallengeProfilePage(challenge);
            //$scope.disableLikeButton = $scope.isChallengeUpvotedByUser(challenge); // scope variable for using with ng-show.
        };

        $scope.getListOfChallenges = function () {
            challengeService.getListOfAllChallenges()
                .success(function (response) {
                    console.log('challengeService.getListOfChallenges() fetched the challenges from the database successfully!');
                    $scope.listOfChallenges = response;
                })
                .error(function (error) {
                    console.log('challengeService.getListOfChallenges() ***FAILED*** to fetch the challenges from the database!');
                    console.log(error);
                });
        };

        $scope.getListOfCompletedChallenges = function () {
            challengeService.getListOfCompletedChallenges()
                .success(function (response) {
                    console.log("challengeService.getListOfCompletedChallenges() fetched all the completed challenges from the database successfully!");
                    $scope.listOfCompletedChallenges = response;
                })
                .error(function (error) {
                    console.log("challengeService.getListOfCompletedChallenges() ***FAILED*** to fetch the completed challenges from the database!");
                    console.log(error);
                });
        };


        /*
         * Functions for users in lists
         * */

        $scope.viewUserProfilePage = function (user) {
            scopeService.viewUserProfilePage(user);
        };

        $scope.getListOfUsers = function () {
            userService.getListOfAllUsers()
                .success(function (response) {
                    console.log('challengeService.getListOfUsers() fetched the users from the database!');
                    $scope.listOfUsers = response;
                })
                .error(function (error) {
                    console.log('challengeService.getListOfUsers() ***FAILED*** to fetch the users from the database!');
                    console.log(error);
                })
        };


        /*
         Below code handles the upvoting and the managing of the already upvoted challenges.
         The code does not execute if no user is logged in.
         */
        $scope.upvoteChallenge = function (challenge) {
            if (sessionStorage.getItem("isLoggedIn") == 'true') {
                challengeService.addOrRemoveUserToChallengeUpvoters(sessionStorage.getItem('loggedInUser'), challenge.id)
                    .success(function () {
                        console.log("challengeService.addUserToChallengeUpvoters() was successfully executed!");
                        // Update the list of challenges after creation of the new challenge,
                        $scope.getListOfChallenges();
                    })
                    .error(function (error) {
                        console.log("challengeService.addUserToChallengeUpvoters() ***FAILED*** to execute!");
                        console.log(error);
                    });
            } else {
                scopeService.showAlertPopup(scopeService.loginAlertMessage());
            }
        };

        // Return true if the challenge is already upvoted by the logged in user, else false.
        $scope.isChallengeUpvotedByUser = function (challenge) {
            if ((sessionStorage.getItem('isLoggedIn') == 'true') && (challenge != null)) {
                $scope.loggedInUser = JSON.parse(sessionStorage.getItem('loggedInUser'));
                for (var i = 0; i < challenge.challengeUpvoters.length; i++) {
                    if (challenge.challengeUpvoters[i] === $scope.loggedInUser.id) {
                        return true;
                    }
                }
            }
            return false;
        };

        $scope.isLoggedInUserTheChallengeCreator = function (challenge) {
            return scopeService.isLoggedInUserTheChallengeCreator(challenge);
        };
        
        $scope.markUrlAsTrusted = function (src) {
            return scopeService.markUrlAsTrusted(src);
        };

        // Fetch the list of challenges on application start.
        $scope.getListOfChallenges();
        $scope.getListOfCompletedChallenges();
        $scope.getListOfUsers();
        $scope.getListOfCompletedChallenges();
        //$scope.updateListOfNotifications();


    }]);