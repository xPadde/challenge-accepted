app.controller('ChallengeController', ['$scope', '$http', 'challengeService', 'userService', function ($scope, $http, challengeService, userService) {

    $scope.orderByField = 'creationDate';
    $scope.reverseSort = true;
    var loggedInUser;

    $scope.getUserInputsFromCreateChallengeForm = function () {
        return JSON.stringify({
            'topic': $('#input-topic').val(),
            'description': $('#input-description').val(),
            'creationDate': null
        });
    };

    $scope.showCreateChallengeSection = function () {
        $scope.section = "createNewChallengeSection";
    };

    $scope.showListOfChallengesSection = function () {
        $scope.section = "listOfChallengesSection";
    };

    $scope.showSecretListOfChallengesSection = function () {
        $scope.section = "secretListOfChallengesSection";
    };

    $scope.createNewChallenge = function () {
        challengeService.createNewChallenge($scope.getUserInputsFromCreateChallengeForm())
            .success(function () {
                console.log('challengeService.createNewChallenge() called and it created a new challenge and saved it to the database!');
                $scope.getListOfChallenges();
                
            })
            .error(function () {
                console.log('challengeService.createNewChallenge() called and it ***FAILED*** to create new challenge');
            })
    };

    $scope.getListOfChallenges = function () {
        challengeService.getListOfChallenges()
            .success(function (response) {
                $scope.listOfChallenges = response;
                console.log('challengeService.getListOfChallenges() fetched all the challenges from the database successfully!');
            })
            .error(function () {
                console.log('challengeService.getListOfChallenges() ***FAILED*** to fetch all the challenges from the database!');
            })
    };

    // Update the list of challenges on page load.
    $scope.getListOfChallenges();

    $scope.upvoteChallenge = function (challenge) {
        var loggedInUser = angular.fromJson(sessionStorage.getItem("loggedInUser"));
        console.log("LOGGED IN USER IN upvoteChallenge()" + loggedInUser);
        challenge.challengeUpvoters.push(loggedInUser);
        challengeService.updateChallenge(angular.toJson(challenge)).success(function(){
          console.log("Challenge updated with challengeUpvoter");
        }).error(function(){
            console.log("ERROR in method upvoteChallenge; Could not update challenge with challengeUpvoters");
        });
    }

    $scope.isChallengeUpvotedByUser = function (challenge) {
        console.log("-----------isChallengeUpvotedByUser runs---------------");

        var loggedInUser = angular.fromJson(sessionStorage.getItem("loggedInUser"));
        console.log("Logged in user: " + loggedInUser.firstName + ", ID: " + loggedInUser.id);
        console.log("Challenge topic: " + challenge.topic + ", Size in challengeUpvoters: " + challenge.challengeUpvoters.length);

        if (challenge.challengeUpvoters !== null) {
            for(var i = 0; i < challenge.challengeUpvoters.length; i++) {
                if (challenge.challengeUpvoters[i] === loggedInUser.id) {
                    console.log("-------------------------------------------------------");
                    return true;
                }
            }
        } else {
            return false;
        }
        console.log("-------------------------------------------------------");
    };

    $scope.claimCurrentChallenge = function (challenge) {
        challenge.challengeClaimed = true;
        challengeService.updateChallenge(challenge);
    };

}]);