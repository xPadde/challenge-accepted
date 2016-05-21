app.controller('UserController', ['$scope', '$log', 'scopeService',
    function ($scope, $log, scopeService) {
        console.debug("UserController laddas");
        $scope.activeUser = scopeService.getActiveUser();
        console.debug("active User Scope: " + $scope.activeUser);
    }]);