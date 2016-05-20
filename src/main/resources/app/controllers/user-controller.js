app.controller('UserController', ['userService', '$scope', function (userService, $scope) {
    console.log("UserController laddas");
    $scope.activeUser = userService.getActiveUser();
    console.log("active User Scope: " + $scope.activeUser);
}]);