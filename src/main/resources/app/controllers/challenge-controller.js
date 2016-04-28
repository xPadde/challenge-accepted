app.controller('ChallengeController', ['$scope', '$http', function ($scope, $http) {

    $scope.getUserInputsFromCreateChallengeForm = function () {
        return JSON.stringify({
            'topic': $('#input-topic').val(),
            'description': $('#input-description').val(),
            'creationDate': null
        });
    };

    $scope.showCreateChallengeSection = function() {
        console.log("KLICKAD");
        $scope.section = "createNewChallengeSection";
    }

    $scope.showListOfChallengesSection = function() {
        console.log("OCKSÅ KLICKAD!");
        $scope.section = "listOfChallengesSection";
    }

    $scope.createNewChallenge = function () {
        $http({
            url: 'http://localhost:8080/api/challenge/',
            method: 'POST',
            data: $scope.getUserInputsFromCreateChallengeForm(),
            header: {'Content-Type': 'application/json'}
        }).success(function() {
            $scope.getListOfChallenges();
            console.log("Success createNewChallenge");
        }).error(function() {
            console.log("Error createNewChallenge");
        })
    };

    $scope.listOfChallenges = {};
    $scope.getListOfChallenges = function() {
    $http({
            url: 'http://localhost:8080/api/challenges/',
            method: 'GET',
            header: {'Content-Type': 'application/json'}
        }).success(function(response){
            $scope.listOfChallenges = response;
            console.log("Success get listOfChallenges");
        }).error(function(){
            console.log("Error get listOfChallenges");
        })
    }

    
}]);