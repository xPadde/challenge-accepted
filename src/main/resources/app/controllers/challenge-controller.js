app.controller('ChallengeController', ['$scope', '$http', '$sce', 'challengeService', 'userService', function ($scope, $http, $sce, challengeService, userService) {

    $scope.orderByField = 'creationDate';
    $scope.reverseSort = true;

    $scope.getUserInputsFromCreateChallengeForm = function () {
        return JSON.stringify({
            'topic': $('#input-topic').val(),
            'description': $('#input-description').val(),
            'creationDate': null,
            'challengeClaimed': false
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

    $scope.showListOfCompletedChallengesSection = function () {
        // $scope.getListOfChallenges();
        // TODO view for completed challenges
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

    $scope.upvoteChallenge = function (challenge) {
        if (sessionStorage.getItem("isLoggedIn") == 'true') {
            challengeService.addUserToChallengeUpvoters(sessionStorage.getItem('loggedInUser'), challenge.id)
                .success(function () {
                    console.log("Add user to upvoted challenges success");
                })
                .error(function (response) {
                    console.log(response);
                });
        } else {
            $scope.section = "loginPageSection";
        }
        ;
    };

    $scope.isChallengeUpvotedByUser = function (challenge) {
        for (var i = 0; i < challenge.challengeUpvoters.length; i++) {
            if (challenge.challengeUpvoters[i] === $scope.loggedInUser.id) {
                return true;
            }
        }
        return false;
    };

    $scope.claimCurrentChallenge = function (challenge) {

        $scope.loggedInUser = angular.fromJson(sessionStorage.getItem("loggedInUser"));

        challenge.challengeClaimer = $scope.loggedInUser;
        challenge.challengeClaimed = true;


        challengeService.updateChallenge(angular.toJson(challenge)).success(function () {
            console.log("claim challenge: Gick bra");
        }).error(function (data) {
            console.log("claim challenge: gick INTE bra");
            console.log(data);
        });

    };

    $scope.viewChallengeProfilePage = function (challenge) {
        $scope.section = "challengeProfilePageSection";
        $scope.activeChallenge = challenge;
        console.log("challengeProfileToView object: ");
        console.log($scope.challengeProfileToView);
        console.log(challenge.challengeClaimed);

        console.log("BOOLEANENNNNNNN " + $scope.isChallengeUpvotedByUser(challenge));

        $scope.disableLikeButton = $scope.isChallengeUpvotedByUser(challenge);
    };

    $scope.addYoutubeUrlToCurrentChallenge = function (challenge) {
        var userProvidedUrl = $('#input-youtube-url').val();
        challenge.youtubeURL = convertToYouTubeEmbedUrl(userProvidedUrl);
        challenge.youtubeVideoUploaded = true;

        challengeService.updateChallenge(angular.toJson(challenge))
            .success(function () {
                console.log("nu är youtube url sparat.");
            }).error(function () {
            console.log("nu är youtube url INTE sparat");
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
            console.log('response var undefined, hittade ingen user med email!');
            userService.createNewUser($scope.loggedInUser)
                .success(function (response) {
                    sessionStorage.setItem('loggedInUser', JSON.stringify(response));
                    sessionStorage.setItem('isLoggedIn', true);
                });
        } else {
            console.log("response var INTE undefined. User hittades!");
            sessionStorage.setItem('loggedInUser', JSON.stringify(response));
            console.log(sessionStorage.getItem('loggedInUser'));
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