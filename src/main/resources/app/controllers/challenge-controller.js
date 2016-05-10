app.controller('ChallengeController', ['$scope', '$http', '$sce', 'challengeService', 'userService', function ($scope, $http, $sce, challengeService, userService) {

    $scope.orderByField = 'creationDate';
    $scope.reverseSort = true;

    $scope.getUserInputsFromCreateChallengeForm = function () {
        return JSON.stringify({
            'topic': $('#input-topic').val(),
            'description': $('#input-description').val(),
            'creationDate': null,
            'challengeClaimed': false,
            'youtubeVideoUploaded': false,
            'youtubeVideoCorrect': false,
            'challengeCompleted': false
        });
    };

    $scope.showCreateChallengeSection = function () {
        console.log("Vad är booleanen: " + sessionStorage.getItem("isLoggedIn"));
        if (sessionStorage.getItem("isLoggedIn") == 'true') {
            $scope.section = "createNewChallengeSection";
        } else {
            $scope.section = "loginPageSection";
        }

    };

    $scope.showListOfChallengesSection = function () {
        $scope.getListOfChallenges();
        $scope.section = "listOfChallengesSection";
    };

    $scope.showApproveVideosSection = function () {
        $scope.section = "listApproveVideosSection";
        $scope.getListOfUnapprovedChallenges();
    };

    $scope.showListOfCompletedChallengesSection = function () {
        $scope.getListOfCompletedChallenges();
        $scope.section = "listOfCompletedChallengesSection";
    };

    $scope.showSecretListOfChallengesSection = function () {
        $scope.section = "secretListOfChallengesSection";
    };

    $scope.createNewChallenge = function () {
        $scope.loggedInUser = JSON.parse(sessionStorage.getItem('loggedInUser'));
        challengeService.createNewChallenge($scope.getUserInputsFromCreateChallengeForm(), $scope.loggedInUser.id)
            .success(function () {
                console.log('challengeService.createNewChallenge() called and it created a new challenge and saved it to the database!');
                $scope.getListOfChallenges();
                $scope.section = "listOfChallengesSection";
            })
            .error(function (response) {
                console.log('challengeService.createNewChallenge() called and it ***FAILED*** to create new challenge');
                console.log(response);
            })

        $('#createNewChallengeForm').each(function () {
            this.reset();
        });
    };

    $scope.getListOfChallenges = function () {
        challengeService.getListOfAllChallenges()
            .success(function (response) {
                $scope.listOfChallenges = response;
                console.log('challengeService.getListOfChallenges() fetched all the challenges from the database successfully!');
            })
            .error(function (response) {
                console.log(response);
                console.log('challengeService.getListOfChallenges() ***FAILED*** to fetch all the challenges from the database!');
            });
    };

    $scope.getListOfUnapprovedChallenges = function () {
        challengeService.getListOfUnapprovedChallenges()
            .success(function (response) {
                $scope.listOfUnapprovedChallenges = response;
                console.log("challengeService.getListOfUnapprovedChallenges fetched all the unapproved challenges successfully");
            })
            .error(function (error) {
                console.log(error);
                console.log("challengeService.getListOfUnapprovedChallenges ***FAILED*** to fetch all the challenges from the database!");
            });
    };

    $scope.getListOfCompletedChallenges = function () {
        challengeService.getListOfCompletedChallenges()
            .success(function (response) {
                $scope.listOfCompletedChallenges = response;
                console.log("Hämtade listan med alla färdiga challenges");
            }).error(function (error) {
            console.log("Hämtade inte listan med färdiga challenges");
        });
    }

    $scope.upvoteChallenge = function (challenge) {
        if (sessionStorage.getItem("isLoggedIn") == 'true') {
            challengeService.addUserToChallengeUpvoters(sessionStorage.getItem('loggedInUser'), challenge.id)
                .success(function () {
                    console.log("Add user to upvoted challenges success");
                    $scope.getListOfChallenges();
                })
                .error(function (response) {
                    console.log(response);
                });
        } else {
            $scope.section = "loginPageSection";
        }
    };

    $scope.isChallengeUpvotedByUser = function (challenge) {
        $scope.loggedInUser = JSON.parse(sessionStorage.getItem('loggedInUser'));
        for (var i = 0; i < challenge.challengeUpvoters.length; i++) {
            if (challenge.challengeUpvoters[i] === $scope.loggedInUser.id) {
                return true;
            }
        }
        return false;
    };

    $scope.claimCurrentChallenge = function (challenge) {
        debugger;

        $scope.loggedInUser = JSON.parse(sessionStorage.getItem("loggedInUser"));
        //TODO Har man upvotat en challenge så blir det krasch eftersom vi skickar in en challengeUpvoter i form av en Long

        $scope.loggedInUser = JSON.parse(sessionStorage.getItem("loggedInUser"));

        challengeService.updateChallengeClaimer($scope.loggedInUser, challenge.id)
            .success(function (response) {
                console.log("challengeService.updateChallenge() SUCCESS");
                console.log(response);
                $scope.activeChallenge = response;
            })
            .error(function (error) {
                console.log("challengeService.updateChallenge() ERROR");
                console.log(error);
            });

    };

    $scope.viewChallengeProfilePage = function (challenge) {
        $scope.section = "challengeProfilePageSection";
        $scope.activeChallenge = challenge;
        console.log("challengeProfileToView object: ");
        console.log($scope.challengeProfileToView);
        console.log(challenge.challengeClaimed);
        console.log(challenge);

        console.log("BOOLEANENNNNNNN " + $scope.isChallengeUpvotedByUser(challenge));

        $scope.disableLikeButton = $scope.isChallengeUpvotedByUser(challenge);
    };

    $scope.addYoutubeUrlToCurrentChallenge = function (challenge) {
        var userProvidedUrl = $('#input-youtube-url').val();
        challenge.youtubeURL = convertToYouTubeEmbedUrl(userProvidedUrl);
        challenge.youtubeVideoCorrect = true;

        challengeService.updateChallenge(angular.toJson(challenge))
            .success(function () {
                console.log("nu är youtube url sparat.");
            }).error(function () {
            console.log("nu är youtube url INTE sparat");
        });
    };

    $scope.confirmYoutubeVideoToCurrentChallenge = function (challenge) {
        $('#uploadYoutubeVideoForm').each(function () {
            this.reset();
        });
        challenge.youtubeVideoUploaded = true;
        challenge.youtubeVideoCorrect = false;
        challengeService.updateChallenge(JSON.stringify(challenge))
            .success(function () {
                console.log("Nu är challenge uppdaterad med booleanen  sparad");
            }).error(function () {
            console.log("Nu är challenge INTE uppdaterad med booleanen");
        });

        alert("VIDEO SENT TO CHALLENGE-REQUESTER");
    };

/*
    $scope.assignPointsToUser = function(challenge) {
        $scope.loggedInUser = JSON.parse(sessionStorage.getItem("loggedInUser"));
        $scope.loggedInUser.points += challenge.upvotes;
        console.log("Upvotes " + challenge.upvotes);
        console.log($scope.loggedInUser);
        userService.updateUser(JSON.stringify($scope.loggedInUser), $scope.loggedInUser.id)
            .success(function() {
                console.log("Points updated and saved");
            }).error(function(error) {
                console.log(error);
                console.log("Points NOT updated and saved");
        });
    };
*/

    $scope.completeChallenge = function (challenge) {
        challenge.challengeCompleted = true;
/*        $scope.assignPointsToUser(challenge);*/
/*        challenge.upvotes = 0;*/
        challenge.youtubeVideoUploaded = false;
        challengeService.updateChallenge(JSON.stringify(challenge))
            .success(function () {
                console.log("Uppdaterat challenge med booleanen completed, upVotes är 0");
            }).error(function () {
            console.log("Uppdaterade inte challenge med booleanen completed, upVotes är inte 0");
        });
    };

    $scope.markUrlAsTrusted = function (src) {
        return $sce.trustAsResourceUrl(src);
    };

    var convertToYouTubeEmbedUrl = function (url) {
        var isYoutubeUrlCorrect = url.indexOf("watch?v=") > 1;
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

    $scope.addCommentToChallenge = function (challenge) {
        var commentFromUser = $('#textarea-comment-field').val();
        $('#commentChallengeForm').each(function () {
            this.reset();
        });
        challengeService.addCommentToChallenge($scope.getUserInputsFromCommentField(), challenge.id)
            .success(function (response) {
                console.log('addCommentToChallenge() was successful!');
                console.log(response);
            })
            .error(function (error) {
                console.log('addCommentToChallenge() failed!');
                console.log(error);
            }).then(function () {
            $scope.activeChallenge = challengeService.getChallengeById(challenge.id);
        });
    };

    $scope.getUserInputsFromCommentField = function () {
        return JSON.stringify({
            'content': $('#textarea-comment-field').val(),
            'commentDate': null
            // TODO add field commentingUser
        })
    };


    // Hardcoded User Login Functions

    // TODO två användare skapas, checked för om en användare redan finns fungerar inte
    // TODO Fixa så man kan skapa challenge

    $scope.getUserInfo = function (profile) {
        return JSON.stringify({
            'firstName': profile.getGivenName(),
            'lastName': profile.getFamilyName(),
            'email': profile.getEmail()
        });
    };

    $scope.setLoggedInUser = function (response) {
        if (response == "") {
            console.log('New user detected - saving to database!');
            userService.createNewUser($scope.loggedInUser)
                .success(function (response) {
                    sessionStorage.setItem('loggedInUser', JSON.stringify(response));
                    sessionStorage.setItem('isLoggedIn', true);
                });
        } else {
            console.log("User found in database!");
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

}]);