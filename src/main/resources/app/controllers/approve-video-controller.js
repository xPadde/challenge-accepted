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
        };

        $scope.markUrlAsTrusted = function (src) {
            return scopeService.markUrlAsTrusted(src);
        };

        // The challenge requester confirm the challenge is performed satisfactory.
        $scope.completeChallenge = function (challenge) {
            challengeService.assignPointsToUser(challenge.id)
                .success(function (response) {
                    console.log("challengeService.assignPointsToUser() was successfully executed!");
                    $scope.activeChallenge = response;
                    $scope.getListOfUnapprovedChallenges();
                })
                .error(function (error) {
                    console.log("challengeService.assignPointsToUser() ***FAILED*** to execute!");
                    console.log(error);
                });
        };

        $scope.disapproveChallenge = function (challenge) {
            challengeService.disapproveCurrentChallenge(challenge.id, $('#disapprove-commentfield').val())
                .success(function (response) {
                    console.log("Challenge was disapproved! Returned to available challenges");
                    challenge = response;
                    $scope.getListOfUnapprovedChallenges();
                }).error(function (error) {
                console.log(error);
            })
        };

        $scope.getListOfUnapprovedChallenges();

    }]);