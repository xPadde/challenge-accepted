app.controller('MainController', ['$scope', '$http', function ($scope, $http) {

    $scope.getUserInputsFromCreateChallengeForm = function () {
        return JSON.stringify({
            'topic': $('#input-topic').val(),
            'description': $('#input-description').val(),
            'creationDate': null
        });
    };

    $scope.createNewChallenge = function () {
        $http({
            url: 'http://localhost:8080/api/challenge/',
            method: 'POST',
            data: $scope.getUserInputsFromCreateChallengeForm(),
            header: {'Content-Type': 'application/json'}
        }).success(function() {
            console.log("Success createNewChallenge");
        }).error(function() {
            console.log("Error createNewChallenge");
        })
    };
    
}]);