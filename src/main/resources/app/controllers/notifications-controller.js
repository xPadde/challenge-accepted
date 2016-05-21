app.controller('NotificationsController', ['scopeService', 'notificationService', '$scope',
    function (scopeService, notificationService, $scope) {
        console.log("Går in i notifications-controller");

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
            console.log("går in i updateListOf");
            notificationService.getAllNotifications($scope.loggedInUser.id)
                .success(function (response) {
                    console.log('notificationService.getAllNotifications() was successfully executed!');
                    console.log(response);
                    $scope.listOfNotifications = response;
                })
                .error(function (error) {
                    console.log('$scope.listOfNotifications ***FAILED*** to execute!');
                    console.log(error);
                })
        };

        $scope.updateListOfNotifications();

    }]);