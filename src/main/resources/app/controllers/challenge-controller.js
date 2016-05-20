app.controller('ChallengeController', ['$scope', '$location', '$route', '$http', '$sce', 'challengeService', 'userService', 'scopeService', 'notificationService',
    function ($scope, $location, $route, $http, $sce, challengeService, userService, scopeService, notificationService) {
        
        $scope.dynamicUrl = "www.youtube.com/watch?v=elN_CPsJ27M";

        var alertPopupMsgLogin = 'Login to use this feature!';
        var alertPopupMsgInvalidYoutubeUrl = 'Please provide a valid YouTube Url';

        var showAlertPopup = function (msg) {
            $.alert({
                title: 'Alert',
                content: msg
            });
        };

        /*
         Below code handles the fetching of the challenge and the user lists.
         The response in the success callbacks is assigned to the scope.
         */
        function initializeSortVariables() {
            // The variables for sorting the challenge lists.
            $scope.orderByFieldAvailable = 'creationDate';
            $scope.orderByCompleted = 'creationDate';
            $scope.orderByClaimed = 'creationDate';
            $scope.reverseSortAvailable = true;
            $scope.reverseSortCompleted = true;
            $scope.reverseSortClaimed = true;
        }

        $scope.addPointToChallenge = function (challenge) {
            if (sessionStorage.getItem('isLoggedIn') == 'true') {
                challengeService.addOrRemovePointToCompletedChallenge(sessionStorage.getItem('loggedInUser'), challenge.id)
                    .success(function () {
                        console.log("addPointToCompletedChallenge() was successfully executed!");
                        $scope.getListOfCompletedChallenges();

                    })
                    .error(function (error) {
                        console.log("addPointToCompletedChallenge() ***FAILED*** to execute");
                        console.log(error);

                    });
            } else {
                showAlertPopup(alertPopupMsgLogin)
            }

        };

        /*$scope.confirmYoutubeVideoToCurrentChallenge = function (challenge) {
            // TODO should we have a confirmation-popup or not?
            // Reset form after confirmation of YouTube URL.
            $('#uploadYoutubeVideoForm').each(function () {
                this.reset();
            });

            challengeService.confirmUploadedYoutubeUrl(challenge.id)
                .success(function (response, status) {
                    if (status == 200) {
                        console.log("challengeService.confirmUploadedYoutubeUrl() was successfully executed!");
                        $scope.activeChallenge = response;
                        $scope.showListOfChallengesSection();
                    }
                })
                .error(function (error) {
                    console.log("challengeService.confirmUploadedYoutubeUrl() ***FAILED*** to execute!");
                    console.log(error);
                });
            showAlertPopup('The video is sent to ' + challenge.challengeCreator.firstName + ' ' + challenge.challengeCreator.lastName + ' and is now pending, waiting for confirmation.');

        };*/

        // The challenge requester confirm the challenge is performed satisfactory.
        $scope.completeChallenge = function (challenge) {
            challengeService.assignPointsToUser(challenge.id)
                .success(function (response) {
                    console.log("challengeService.assignPointsToUser() was successfully executed!");
                    $scope.activeChallenge = response;
                    $scope.getListOfUnapprovedChallenges();
                })
                .error(function (error) {
                    console.log("challengeService.assignPointsToUser() ***FAILED*** to execute!");
                    console.log(error);
                });
        };

        $scope.disapproveChallenge = function (challenge) {
            challengeService.disapproveCurrentChallenge(challenge.id, $('#disapprove-commentfield').val())
                .success(function (response) {
                    console.log("Challenge was disapproved! Returned to available challenges");
                    challenge = response;
                    $scope.getListOfUnapprovedChallenges();
                }).error(function (error) {
                console.log(error);
            })
        };

        

        /*
         Below code handles notifications.
         */
        $scope.updateListOfNotifications = function () {
            $scope.loggedInUser = JSON.parse(sessionStorage.getItem("loggedInUser"));
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


        /*
         Handles the comment sections.
         TODO this section is under development.
         */
        $scope.addCommentToChallenge = function (challenge) {
            var commentFromUser = $('#textarea-comment-field').val();
            $('#commentChallengeForm').each(function () {
                this.reset();
            });

            challengeService.addCommentToChallenge($scope.getUserInputsFromCommentField(), challenge.id)
                .success(function (response) {
                    console.log('challengeService.addCommentToChallenge was successfully executed!');
                    console.log(response);
                })
                .error(function (error) {
                    console.log('challengeService.addCommentToChallenge ***FAILED*** to execute!');
                    console.log(error);
                })
                .then(function () {
                    $scope.activeChallenge = challengeService.getChallengeById(challenge.id);
                });
        };

        $scope.getUserInputsFromCommentField = function () {
            return JSON.stringify({
                'content': $('#textarea-comment-field').val(),
                'commentDate': null
                // TODO add field: commentingUser
            })
        };
        
    }]);