app.controller('UserController', ['$scope', '$http', 'userService', function ($scope, $http, userService) {

    $scope.getUserInputsFromCreateUserForm = function () {
            return JSON.stringify({
                'userName': $('#input-username').val(),
                'password': $('#input-password').val()
            });
        };

        $scope.showCreateUserSection = function () {
        console.log("KLICKED")
            $scope.section = "createNewUserSection";
        };

        $scope.createNewUser = function () {
            userService.createNewUser($scope.getUserInputsFromCreateUserForm()).success(function () {
                console.log('userService created a new user and saved it to database!')
            });
        };
}]);