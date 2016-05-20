app.service('scopeService', ['$location', 'userService', function ($location, userService) {

    var activeChallenge;
    var activeUser;

    return {
        setActiveUser: function (user) {
            activeUser = user;
        },

        getActiveUser: function () {
            return activeUser;
        },

        setActiveChallenge: function (challenge) {
            activeChallenge = challenge;
        },

        getActiveChallenge: function () {
            return activeChallenge;
        },

        viewUserProfilePage: function (user) {
            userService.getUserByEmail(user.email).success(function (response) {
                activeUser = response;
                $location.path('/user-profile/' + response.id);
            });
        },

        viewChallengeProfilePage: function (challenge) {
         activeChallenge = challenge;
         $location.path('/challenge-profile/' + activeChallenge.id);
         //$scope.disableLikeButton = $scope.isChallengeUpvotedByUser(challenge); // scope variable for using with ng-show.
         }
    }
}]);