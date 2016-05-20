app.controller('ApproveVideoController', ['scopeService', '$scope', 'challengeService',
    function (scopeService, $scope, challengeService) {

        $scope.activeChallenge = scopeService.getActiveChallenge();

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


        $scope.isLoggedInUserTheChallengeCreator = function (challenge) {
            return scopeService.isLoggedInUserTheChallengeCreator(challenge);
        };

        $scope.viewUserProfilePage = function (user) {
            scopeService.viewUserProfilePage(user);
        };

        $scope.viewChallengeProfilePage = function (challenge) {
            scopeService.viewChallengeProfilePage(challenge);
            //$scope.disableLikeButton = $scope.isChallengeUpvotedByUser(challenge); // scope variable for using with ng-show.
        };

        $scope.getListOfUnapprovedChallenges();

    }]);