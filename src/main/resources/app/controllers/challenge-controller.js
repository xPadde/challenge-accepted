app.controller('ChallengeController', ['$scope', '$http', 'challengeService', 'userService', function ($scope, $http, challengeService, userService) {

    $scope.getUserInputsFromCreateChallengeForm = function () {
        return JSON.stringify({
            'topic': $('#input-topic').val(),
            'description': $('#input-description').val(),
            'creationDate': null
        });
    };

    $scope.getUserInputsFromCreateUserForm = function () {
                return JSON.stringify({
                    'userName': $('#input-username').val(),
                    'password': $('#input-password').val()
                });
            };

    $scope.loginUser = function () {
        $scope.getListOfUsers();

        var username = $('#input-login-username').val();
        var password = $('#input-login-password').val();

        console.log("Testa funktion vid log in" + username + " " + password);


    };

    $scope.showCreateChallengeSection = function () {
        $scope.section = "createNewChallengeSection";
    };

    $scope.showListOfChallengesSection = function () {
        $scope.section = "listOfChallengesSection";
    };

    $scope.showCreateUserSection = function () {
        $scope.section = "createNewUserSection";
        console.log("KLICKED");
        };

    $scope.createNewChallenge = function () {
        challengeService.createNewChallenge($scope.getUserInputsFromCreateChallengeForm()).success(function () {
            console.log('challengeService created a new challenge and saved it to database!')
            $scope.getListOfChallenges();
        });
    };

    $scope.createNewUser = function () {
                    userService.createNewUser($scope.getUserInputsFromCreateUserForm()).success(function () {
                        console.log('userService created a new user and saved it to database!')
                    });
                };

    $scope.getListOfChallenges = function () {
        challengeService.getListOfChallenges().success(function (response) {
            $scope.listOfChallenges = response;
            console.log('challengeService fetched the challenge list from the database succesfully!')
        });
    };

    $scope.getListOfUsers = function () {
        userService.getListOfUsers().success(function (response) {
            $scope.listOfUsers = response;
            console.log('userService fetched the user list from the database succesfully!')
        });
    };

    $scope.getListOfChallenges();

}]);