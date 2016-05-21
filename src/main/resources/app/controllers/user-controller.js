app.controller('UserController', ['$scope', '$log', '$location', '$routeParams', 'scopeService', 'userService',
    function ($scope, $log, $location, $routeParams, scopeService, userService) {

        var userUrlId = $routeParams.id;
        userService.getUserById(userUrlId).success(function (response) {
            $scope.activeUser = response;
        }).error(function () {
            $location.path('/error-user');
        });
    }]);