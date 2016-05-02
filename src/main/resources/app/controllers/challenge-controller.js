app.controller('ChallengeController', ['$scope', '$http', 'challengeService', 'userService', function ($scope, $http, challengeService, userService) {

    var list = [];

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
        var username = $('#input-login-username').val();
        var password = $('#input-login-password').val();
        $scope.validateLogin(username, password);
    };

    $scope.showLoginWindowSection = function () {
        $scope.section = "loginWindowSection";
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

    $scope.showCreateUserSection = function () {
        $scope.section = "createNewUserSection";
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


    $scope.createNewUser = function () {
        userService.createNewUser($scope.getUserInputsFromCreateUserForm())
            .success(function () {
                console.log('userService.createNewUser() called and it created a new user and saved it to the database!');
            })
            .error(function () {
                console.log('userService.createNewUser() called and it ***FAILED*** to create a new user!');
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

    $scope.listOfUsers = [];
    $scope.validateLogin = function (username, password) {
        userService.validateLogin().success(function (response) {
            $scope.listOfUsers = response;

            for (var i = 0; i < $scope.listOfUsers.length; i++) {
                if (username == $scope.listOfUsers[i].userName && password == $scope.listOfUsers[i].password) {
                    console.log("Login correct and showSecretListOfChallengesSection() will be called!");
                    $scope.showSecretListOfChallengesSection();
                } else {
                    console.log("Login ***FAILED*** and showSecretListOfChallengesSection() will ***NOT*** be called!");
                }
            }
            console.log('userService.validateLogin() fetched the user list from the database successfully!')
        });
    };

    $scope.acceptChallenge = function (challenge) {
        challenge.challengeClaimed = true;
        challengeService.updateChallenge(challenge);
    };

    // Update the list of challenges on page load.
    $scope.getListOfChallenges();

}]);