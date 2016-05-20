app.controller('ListController', ['scopeService', 'challengeService', 'userService', '$scope', function (scopeService, challengeService, userService, $scope) {
    console.log("LIST-CONTROLLER KÖRS!");
    $scope.loggedInUser = scopeService.getLoggedInUser();
    console.log($scope.loggedInUser);

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

    $scope.getListOfUnapprovedChallenges = function () {
        challengeService.getListOfUnapprovedChallenges()
            .success(function (response) {
                $scope.listOfUnapprovedChallenges = response;
                console.log("challengeService.getListOfUnapprovedChallenges fetched all the unapproved challenges successfully!");
            })
            .error(function (error) {
                console.log(error);
                console.log("challengeService.getListOfUnapprovedChallenges() ***FAILED*** to fetch all the challenges from the database!");
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

    // Fetch the list of challenges on application start.
    $scope.getListOfChallenges();
    $scope.getListOfCompletedChallenges();
    $scope.getListOfUsers();
    $scope.getListOfUnapprovedChallenges();
    $scope.getListOfCompletedChallenges();
    //$scope.updateListOfNotifications();


}]);