app.controller('UserController', ['scopeService', '$scope', function (scopeService, $scope) {
    console.log("UserController laddas");
    $scope.activeUser = scopeService.getActiveUser();
    console.log("active User Scope: " + $scope.activeUser);
}]);