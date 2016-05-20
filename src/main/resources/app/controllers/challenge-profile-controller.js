app.controller('ChallengeProfileController', ['scopeService', 'challengeService', '$scope', '$sce',
    function (scopeService, challengeService, $scope, $sce) {

        $scope.activeChallenge = scopeService.getActiveChallenge();

        var alertPopupMsgLogin = 'Login to use this feature!';
        var alertPopupMsgInvalidYoutubeUrl = 'Please provide a valid YouTube Url';

        var showAlertPopup = function (msg) {
            $.alert({
                title: 'Alert',
                content: msg
            });
        };

        $scope.isLoggedInUserTheChallengeCreator = function (challenge) {
            return scopeService.isLoggedInUserTheChallengeCreator(challenge);
        };

        /*
         Below code manage different scenarios for when a user tries to update and manage the challenge.
         The response is updating the challenge the user is currently viewing.
         Usually all the code requires the user to be logged in before execution.
         */

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
                    }
                })
                .error(function (error) {
                    console.log("challengeService.confirmUploadedYoutubeUrl() ***FAILED*** to execute!");
                    console.log(error);
                });
            showAlertPopup('The video is sent to ' + challenge.challengeCreator.firstName + ' ' + challenge.challengeCreator.lastName + ' and is now pending, waiting for confirmation.');

        };

    }]);