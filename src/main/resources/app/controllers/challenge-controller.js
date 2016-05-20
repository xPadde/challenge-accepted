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

        $scope.viewChallengeProfilePage = function(challenge) {
            scopeService.viewChallengeProfilePage(challenge);
            //$scope.disableLikeButton = $scope.isChallengeUpvotedByUser(challenge); // scope variable for using with ng-show.
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
        
        $scope.getListOfChallenges = function () {
            challengeService.getListOfAllChallenges()
                .success(function (response) {
                    console.log('challengeService.getListOfChallenges() fetched the challenges from the database successfully!');
                    $scope.listOfChallenges = response;
                })
                .error(function (error) {
                    console.log('challengeService.getListOfChallenges() ***FAILED*** to fetch the challenges from the database!');
                    console.log(error);
                });
        };

        $scope.getListOfUsers = function () {
            userService.getListOfAllUsers()
                .success(function (response) {
                    console.log('challengeService.getListOfUsers() fetched the users from the database!');
                    $scope.listOfUsers = response;
                })
                .error(function (error) {
                    console.log('challengeService.getListOfUsers() ***FAILED*** to fetch the users from the database!');
                    console.log(error);
                })
        };

        $scope.getListOfUnapprovedChallenges = function () {
            challengeService.getListOfUnapprovedChallenges()
                .success(function (response) {
                    $scope.listOfUnapprovedChallenges = response;
                    console.log("challengeService.getListOfUnapprovedChallenges fetched all the unapproved challenges successfully!");
                })
                .error(function (error) {
                    console.log(error);
                    console.log("challengeService.getListOfUnapprovedChallenges() ***FAILED*** to fetch all the challenges from the database!");
                });
        };

        $scope.getListOfCompletedChallenges = function () {
            challengeService.getListOfCompletedChallenges()
                .success(function (response) {
                    console.log("challengeService.getListOfCompletedChallenges() fetched all the completed challenges from the database successfully!");
                    $scope.listOfCompletedChallenges = response;
                })
                .error(function (error) {
                    console.log("challengeService.getListOfCompletedChallenges() ***FAILED*** to fetch the completed challenges from the database!");
                    console.log(error);
                });
        };

        /*
         Below code handles the upvoting and the managing of the already upvoted challenges.
         The code does not execute if no user is logged in.
         */
        $scope.upvoteChallenge = function (challenge) {
            if (sessionStorage.getItem("isLoggedIn") == 'true') {
                challengeService.addOrRemoveUserToChallengeUpvoters(sessionStorage.getItem('loggedInUser'), challenge.id)
                    .success(function () {
                        console.log("challengeService.addUserToChallengeUpvoters() was successfully executed!");
                        // Update the list of challenges after creation of the new challenge,
                        $route.reload();
                        /*$scope.getListOfChallenges();
                         $scope.getListOfCompletedChallenges();
                         $scope.updateListOfNotifications();*/
                    })
                    .error(function (error) {
                        console.log("challengeService.addUserToChallengeUpvoters() ***FAILED*** to execute!");
                        console.log(error);
                    });
            } else {
                showAlertPopup(alertPopupMsgLogin);
            }
        };

        // Return true if the challenge is already upvoted by the logged in user, else false.
        $scope.isChallengeUpvotedByUser = function (challenge) {
            if ((sessionStorage.getItem('isLoggedIn') == 'true') && (challenge != null)) {
                $scope.loggedInUser = JSON.parse(sessionStorage.getItem('loggedInUser'));
                for (var i = 0; i < challenge.challengeUpvoters.length; i++) {
                    if (challenge.challengeUpvoters[i] === $scope.loggedInUser.id) {
                        return true;
                    }
                }
            }
            return false;
        };

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

        }


        /*
         Below code manage different scenarios for when a user tries to update and manage the challenge.
         The response is updating the challenge the user is currently viewing.
         Usually all the code requires the user to be logged in before execution.
         */

        // Return true if the logged in user is the creator of the challenge, else false.
        $scope.isLoggedInUserTheChallengeCreator = function (challenge) {
            if (challenge != null || challenge != undefined) {
                if (sessionStorage.getItem('isLoggedIn') == 'true') {
                    $scope.loggedInUser = JSON.parse(sessionStorage.getItem('loggedInUser'));
                    if (challenge.challengeCreator.id == $scope.loggedInUser.id) {
                        return true;
                    }
                }
            }
            return false;
        };

        // The user claims the challenge and "promise" to perform it.
        $scope.claimCurrentChallenge = function (challenge) {
            if (sessionStorage.getItem("isLoggedIn") == 'true') {
                $scope.loggedInUser = JSON.parse(sessionStorage.getItem("loggedInUser"));
                challengeService.updateChallengeClaimer($scope.loggedInUser, challenge.id)
                    .success(function (response) {
                        console.log("challengeService.updateChallenge() was successfully executed!");
                        $scope.activeChallenge = response;
                    })
                    .error(function (error, status) {
                        if (status === 400) {
                            console.log("challengeService.updateChallenge() ***FAILED*** to execute! Bad request! User cannot claim own challenge!");
                        } else {
                            console.log("challengeService.updateChallenge() ***FAILED*** to execute!");
                            console.log(error);
                        }
                    });
            } else {
                showAlertPopup(alertPopupMsgLogin);
            }
        };

        // The user adds a YouTube url after performing the challenge.
        $scope.addYoutubeUrlToCurrentChallenge = function (challenge) {
            // TODO review naming conventions for 'YouTube' and 'Url'.

            var userProvidedUrl = $('#input-youtube-url').val();
            var convertedYoutubeUrl = convertToYouTubeEmbedUrl(userProvidedUrl);


            challengeService.addYoutubeUrlToChallenge(challenge.id, convertedYoutubeUrl)
                .success(function (response) {
                    console.log("challengeService.addYoutubeUrlToChallenge() was successfully executed and YouTube url was saved to the challenge!");
                    $scope.activeChallenge = response;
                })
                .error(function (error) {
                    console.log("challengeService.addYoutubeUrlToChallenge() ***FAILED*** to execute!");
                    console.log(error);
                });

            showAlertPopup('Once you click confirm in the next step, the video will be sent to ' + challenge.challengeCreator.firstName + ' ' + challenge.challengeCreator.lastName + '. Be sure it is the right one!');
        };

        $scope.confirmYoutubeVideoToCurrentChallenge = function (challenge) {
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

        };

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
         Handles the YouTube URL.
         */

        // Function for marking a YouTube URL as trusted.
        $scope.markUrlAsTrusted = function (src) {
            return $sce.trustAsResourceUrl(src);
        };

        // Convert the user provided YouTube URL to a embedded URL for use in an iframe.
        var convertToYouTubeEmbedUrl = function (url) {
            var isYoutubeUrlCorrect = url.indexOf("watch?v=") > 1; // Is true if the string contains "watch?v="
            var baseUrl = "https://www.youtube.com/embed/";

            if (isYoutubeUrlCorrect) {
                var youTubeVideoId = url.substr(32);
                var finalUrl = baseUrl + youTubeVideoId;
            } else {
                showAlertPopup(alertPopupMsgInvalidYoutubeUrl);
            }
            return finalUrl;
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


        // Fetch the list of challenges on application start.
        $scope.getListOfChallenges();
        $scope.getListOfCompletedChallenges();
        initializeSortVariables();
        $scope.getListOfUsers();
        $scope.getListOfUnapprovedChallenges();
        $scope.getListOfCompletedChallenges();
        $scope.updateListOfNotifications();
    }]);