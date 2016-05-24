app.controller('UserController', ['$scope', '$location', '$routeParams', 'userService', 'scopeService',
    function ($scope, $location, $routeParams, userService, scopeService) {

        var userUrlId = $routeParams.id;
        userService.getUserById(userUrlId)
            .success(function (response) {
                $scope.activeUser = response;
            })
            .error(function () {
                $location.path('/error-user');
            });

    }]);