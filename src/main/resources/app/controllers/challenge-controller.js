app.controller('ChallengeController', ['$scope', '$http', 'challengeService', 'userService', function ($scope, $http, challengeService, userService) {

    var list = [];
    $scope.orderByField = 'creationDate';
    $scope.reverseSort = true;

    $scope.isChallengeUpvotedByUser = function (challenge) {
        console.log(challenge);
        var userToCheck = angular.fromJson(sessionStorage.getItem("loggedInUser"));
        console.log("challengeUpvotersLength: " + challenge.challengeUpvoters.length);
        console.log("userToCheck.id: " + userToCheck.id);
        console.log("challengeUpvoters: " + challenge.challengeUpvoters);
        for(var i = 0; i < challenge.challengeUpvoters.length; i++) {
        console.log(challenge.challengeUpvoters[i].id);
            if(challenge.challengeUpvoters[i].id === userToCheck.id) {
            console.log("Redan gillat denna");
                return true;
            } else {
                console.log("Id'na var inte lika varandra");
            }
            return false;
        }
    };

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
                $scope.getListOfChallenges()
                
            })
            .error(function () {
                console.log('challengeService.createNewChallenge() called and it ***FAILED*** to create new challenge');
            })
    };

    $scope.getListOfChallenges = function () {
        challengeService.getListOfChallenges()
            .success(function (response) {
                $scope.listOfChallenges = response;
                console.log('challengeService.getListOfChallenges() fetched all the challenges from the database successfully!')
            })
            .error(function () {
                console.log('challengeService.getListOfChallenges() ***FAILED*** to fetch all the challenges from the database!')
            })
    };

    $scope.upvoteChallenge = function (challenge) {
        challenge.upvotes += 1;
        var loggedInUser = angular.fromJson(sessionStorage.getItem("loggedInUser"));
        challenge.challengeUpvoters.push(loggedInUser);
        challengeService.updateChallenge(angular.toJson(challenge)).success(function(){
          console.log("add upvote: nu gick det bra");
        }).error(function(){
            console.log("add upvote: nu gick det INTE bra");
        });
        
        /*challengeService.addChallengeToUpvotedChallenges(angular.toJson(challenge)).success(function(){
           console.log("add upvote: nu gick det bra");
        }).error(function(){
            console.log("add upvote: nu gick det INTE bra");
        });*/
    }

    $scope.claimCurrentChallenge = function (challenge) {
        challenge.challengeClaimed = true;
        challengeService.updateChallenge(challenge);
    };

    // Update the list of challenges on page load.
    $scope.getListOfChallenges();

}]);