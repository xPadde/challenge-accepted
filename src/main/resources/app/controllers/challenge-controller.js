app.controller('ChallengeController', ['$scope', '$http', 'challengeService', function ($scope, $http, challengeService) {

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

    $scope.createNewChallenge = function () {
        //$http({
        //    url: 'http://localhost:8080/api/challenge/',
        //    method: 'POST',
        //    data: $scope.getUserInputsFromCreateChallengeForm(),
        //    header: {'Content-Type': 'application/json'}
        //}).success(function () {
        //    //$scope.getListOfChallenges();
        //    console.log("Success createNewChallenge");
        //}).error(function () {
        //    console.log("Error createNewChallenge");
        //})
        challengeService.createNewChallenge($scope.getUserInputsFromCreateChallengeForm()).success(function () {
            console.log('challengeService created a new challenge and saved it to database!')
            $scope.getListOfChallenges();
        });
    };

    $scope.getListOfChallenges = function () {
        challengeService.getListOfChallenges().success(function (response) {
            $scope.listOfChallenges = response;
            console.log('challengeService fetched the challenge list from the database succesfully!')
        });
    };

    $scope.getListOfChallenges();

}]);