app.controller('UserController', ['$scope', '$location', '$log', '$routeParams', 'userService',
    function ($scope, $location, $log, $routeParams, userService) {

        var userUrlId = $routeParams.id;
        userService.getUserById(userUrlId)
            .success(function (response) {
                $scope.activeUser = response;
            })
            .error(function () {
                $location.path('/error-user');
            });

        $scope.getListOfCompletedChallenges = function () {
            challengeService.getListOfCompletedChallenges()
                .success(function (response) {
                    $log.info("challengeService.getListOfCompletedChallenges() fetched all the completed challenges from the database successfully!");
                    $scope.listOfCompletedChallenges = response;
                })
                .error(function (error) {
                    $log.error("challengeService.getListOfCompletedChallenges() ***FAILED*** to fetch the completed challenges from the database!");
                    $log.error(error);
                });
        };

    }]);