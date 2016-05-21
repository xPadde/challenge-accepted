app.controller('NotificationsController', ['scopeService', 'notificationService', '$scope',
    function (scopeService, notificationService, $scope) {

        $scope.loggedInUser = scopeService.getLoggedInUser();

        $scope.viewUserProfilePage = function (user) {
            scopeService.viewUserProfilePage(user);
        };

        $scope.viewChallengeProfilePage = function (challenge) {
            scopeService.viewChallengeProfilePage(challenge);
        };

        /*
         Below code handles notifications.
         */
        $scope.updateListOfNotifications = function () {
            notificationService.getAllNotifications($scope.loggedInUser.id)
                .success(function (response) {
                    console.log('notificationService.getAllNotifications() was successfully executed!');
                    $scope.listOfNotifications = response;
                })
                .error(function (error) {
                    console.log('$scope.listOfNotifications ***FAILED*** to execute!');
                    console.log(error);
                })
        };

        $scope.updateListOfNotifications();

    }]);