app.controller('ChallengeController', ['$scope', '$http', '$sce', 'challengeService', 'userService', 'angulike', function ($scope, $http, $sce, challengeService, userService) {

    // The variables for sorting the challenge list.
    $scope.orderByField = 'creationDate';
    $scope.reverseSort = true;
    $scope.dynamicUrl = "www.youtube.com/watch?v=elN_CPsJ27M";

    // Message for prompting the user to login if trying to access features when logged out.
    var alertLoginPrompt = "Log in to use this feature.";


    /*
     Handles the user login.
     */
    $scope.getUserInfo = function (profile) {
        return JSON.stringify({
            'firstName': profile.getGivenName(),
            'lastName': profile.getFamilyName(),
            'email': profile.getEmail()
        });
    };

    $scope.setLoggedInUser = function (response) {
        if (response == "") {
            userService.createNewUser($scope.loggedInUser)
                .success(function (response) {
                    console.log('Login success! The user NOT found in database and successfully saved to the database!');
                    sessionStorage.setItem('loggedInUser', JSON.stringify(response));
                    sessionStorage.setItem('isLoggedIn', true);
                })
                .error(function (error) {
                    console.log('The user NOT found in database but ***FAILED*** to save to the database!');
                    console.log(error);
                });
        } else {
            console.log("Login success! The user already found in database! Proceeding...");
            sessionStorage.setItem('loggedInUser', JSON.stringify(response));
            sessionStorage.setItem('isLoggedIn', true);
        }
    };

    onSignIn = function (googleUser) {
        var profile = googleUser.getBasicProfile();
        var userFoundByEmail = "";
        $scope.loggedInUser = JSON.parse($scope.getUserInfo(profile));

        userService.getUserByEmail($scope.loggedInUser.email)
            .success(function (response) {
                userFoundByEmail = response;
            })
            .error(function (error) {
                console.log("userService.getUserByEmail ERROR!");
                console.log(error);
            }).then(function () {
            $scope.setLoggedInUser(userFoundByEmail);
        });
    };

    $scope.signOut = function () {
        var auth2 = gapi.auth2.getAuthInstance();
        auth2.signOut().then(function () {
            sessionStorage.setItem("loggedInUser", null);
            sessionStorage.setItem("isLoggedIn", false);
            console.log("Auth2 isSignedIn?: " + auth2.isSignedIn.get());
            window.location.reload();
        });
    };

    
    

    /*
     Below code handles the toggling of the different sections in index.html.
     The methods is assigned to the ng-clicks.
     */
    $scope.section = "listOfChallengesNotLoggedInSection";

    $scope.showStartPageLists = function () {
        $scope.section = "listOfChallengesNotLoggedInSection";
    };

    $scope.showListOfChallengesSection = function () {
        $scope.getListOfChallenges();
        $scope.section = "listOfChallengesSection";
    };

    $scope.showListOfUsersSection = function () {
        $scope.getListOfUsers();
        $scope.section = "listOfUsersSection";
    };

    $scope.showApproveVideosSection = function () {
        $scope.getListOfUnapprovedChallenges();
        $scope.section = "listApproveVideosSection";
    };

    $scope.showListOfCompletedChallengesSection = function () {
        $scope.getListOfCompletedChallenges();
        $scope.section = "listOfCompletedChallengesSection";
    };

    $scope.showSecretListOfChallengesSection = function () {
        $scope.getListOfClaimedChallenges();
        $scope.section = "secretListOfChallengesSection";
    };

    $scope.showListOfClaimedChallenges = function () {
        $scope.section = "listOfClaimedChallengesSection";
    };

    $scope.showCreateChallengeSection = function () {
        if (sessionStorage.getItem("isLoggedIn") == 'true') {
            $scope.section = "createNewChallengeSection";
        } else {
            alert(alertLoginPrompt);
        }
    };

    $scope.viewChallengeProfilePage = function (challenge) {
        $scope.section = "challengeProfilePageSection";
        $scope.activeChallenge = challenge;
        $scope.disableLikeButton = $scope.isChallengeUpvotedByUser(challenge); // scope variable for using with ng-show.
    };

    $scope.viewUserProfilePage = function (user) {
        console.log(user);
        $scope.activeUser = user;
        $scope.section = "userProfilePageSection";


    }


    /*
     Below code handles the creation of the new challenges.
     The code does not execute if no user is logged in.
     */
    $scope.getUserInputsFromCreateChallengeForm = function () {
        return JSON.stringify({
            'topic': $('#input-topic').val(),
            'description': $('#input-description').val(),
            'creationDate': null,
            'challengeClaimed': false,
            'youtubeVideoUploaded': false,
            'youtubeVideoCorrect': false,
            'challengeCompleted': false,
            'challengeDisapproved': false,
            'upvotes': 0
        });
    };

    $scope.createNewChallenge = function () {
        /*if (!isInputAndTextareaEmpty()) {*/
            $scope.loggedInUser = JSON.parse(sessionStorage.getItem('loggedInUser'));
            challengeService.createNewChallenge($scope.getUserInputsFromCreateChallengeForm(), $scope.loggedInUser.id)
                .success(function (response, status) {
                    if (status == 201) {
                        console.log('challengeService.createNewChallenge() called. New challenge created and saved to the database!');
                        // Update the list of challenges after creation of the new challenge,
                        $scope.getListOfChallenges();
                        // then show the list.
                        $scope.section = "listOfChallengesSection";
                    }
                })
                .error(function (response) {
                    console.log('challengeService.createNewChallenge() called. ***FAILED*** to create new challenge!');
                    console.log(response);
                });

            $('#createNewChallengeForm').each(function () {
                this.reset();
            });

        /*} else {
            console.log('challengeService.createNewChallenge() did ***NOT*** create a new challenge. Fields was empty!');
            alert('challengeService.createNewChallenge() did ***NOT*** create a new challenge. Fields was empty!');
        }*/
    };


    /*
     Below code handles the fetching of the challenge and the user lists.
     The response in the success callbacks is assigned to the scope.
     */
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
            challengeService.addUserToChallengeUpvoters(sessionStorage.getItem('loggedInUser'), challenge.id)
                .success(function () {
                    console.log("challengeService.addUserToChallengeUpvoters() was successfully executed!");
                    // Update the list of challenges after creation of the new challenge,
                    $scope.getListOfChallenges();
                })
                .error(function (error) {
                    console.log("challengeService.addUserToChallengeUpvoters() ***FAILED*** to execute!");
                    console.log(error);
                });
        } else {
            alert(alertLoginPrompt);
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
                .error(function (error) {
                    console.log("challengeService.updateChallenge() ***FAILED*** to execute!");
                    console.log(error);
                });
        } else {
            alert(alertLoginPrompt);
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
    };

    // The user confirm the YouTube url of the performance is correct.
    $scope.confirmYoutubeVideoToCurrentChallenge = function (challenge) {
        if (confirm("This will confirm your video for review. Are you sure you want to upload this video?")) {
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
            alert("Thank you. The video was sent to the challenge requester and is now pending, waiting for confirmation.");
        }
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

        challengeService.disapproveCurrentChallenge(challenge.id)
            .success(function (response) {
                console.log("Challenge was disapproved! Returned to available challenges");
                challenge = response;
                /*alert("Challenge was disapproved!");*/
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
            alert("Please provide a valid YouTube URL");
            return "";
        }
        return finalUrl;
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


    // Form validation.
    function isInputAndTextareaEmpty() {
        var isInputAndTextareaEmpty = false;
        $('input, textarea').each(function (index) {
            if ($(this).val() === '' || $(this).val() === null) {
                isInputAndTextareaEmpty = true;
            }
        });
        console.log(isInputAndTextareaEmpty)
        return isInputAndTextareaEmpty;
    }

    // Fetch the list of challenges on application start.
    $scope.getListOfChallenges();
    $scope.getListOfCompletedChallenges();

}]);