app.controller('MainController', ['$scope', '$http', function ($scope, $http) {

    $scope.getUserInputsFromCreateChallengeForm = function () {
        return JSON.stringify({
            'topic': $('#input-topic').val(),
            'description': $('#input-description').val(),
            'date': null
        });
    };

    $scope.createNewChallenge = function () {
        console.log("Klickade här");
        $http({
            url: 'http://localhost:8080/api/challenge/',
            method: 'POST',
            data: $scope.getUserInputsFromCreateChallengeForm(),
            header: {'Content-Type': 'application/json'}
        }).success(function() {
            console.log("Success!!!!");
        }).error(function() {
            console.log("Error!!!!");
        })


    };


}]);