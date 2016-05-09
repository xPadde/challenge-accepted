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
            $scope.section = "listOfCompletedChallengesSection";
        };

    $scope.showSecretListOfChallengesSection = function () {
        $scope.section = "secretListOfChallengesSection";
    };

    $scope.createNewChallenge = function () {
        var loggedInUser = angular.fromJson(sessionStorage.getItem("loggedInUser"));
        var userId = loggedInUser.id;
        console.log(userId)
        challengeService.createNewChallenge($scope.getUserInputsFromCreateChallengeForm(), userId)
            .success(function () {
                console.log('challengeService.createNewChallenge() called and it created a new challenge and saved it to the database!');
                $scope.getListOfChallenges()
                $scope.section = "listOfChallengesSection";
            })
            .error(function () {
                console.log('challengeService.createNewChallenge() called and it ***FAILED*** to create new challenge');
            })

        $( '#createNewChallengeForm' ).each(function(){
            this.reset();
        });
    };

    $scope.getListOfChallenges = function () {
        challengeService.getListOfChallenges()
            .success(function (response) {
                $scope.listOfChallenges = response;
                console.log('challengeService.getListOfChallenges() fetched all the challenges from the database successfully!');
            })
            .error(function () {
                console.log('challengeService.getListOfChallenges() ***FAILED*** to fetch all the challenges from the database!');
            })
    };

    $scope.getListOfUnapprovedChallenges = function () {
        challengeService.getListOfUnapprovedChallenges()
            .success(function (response) {
                $scope.listOfUnapprovedChallenges = response;
                console.log("challengeService.getListOfUnapprovedChallenges fetched all the unapproved challenges successfully");
        })
        .error(function(error) {
            console.log(error);
            console.log("challengeService.getListOfUnapprovedChallenges ***FAILED*** to fetch all the challenges from the database!");
        })
    };

    $scope.upvoteChallenge = function (challenge) {
        if (sessionStorage.getItem("isLoggedIn") == 'true') {
            $scope.loggedInUser = angular.fromJson(sessionStorage.getItem("loggedInUser"));
            challengeService.addUserToChallengeUpvoters($scope.loggedInUser, challenge.id).success(function () {
                console.log("Add user to upvoted challenges success");
                $scope.getListOfChallenges();
            })
        } else {
            $scope.section = "loginPageSection";
        }
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
        console.log(challenge);
        console.log("challengeProfileToView object: ");
        console.log($scope.challengeProfileToView);
        console.log(challenge.challengeClaimed);

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
        $( '#uploadYoutubeVideoForm' ).each(function(){
            this.reset();
        });
        challenge.youtubeVideoUploaded = true;
        challenge.youtubeVideoCorrect = false;
        challengeService.updateChallenge(angular.toJson(challenge))
            .success(function () {
                console.log("Nu är challenge uppdaterad med booleanen  sparad");
            }).error(function() {
                console.log("Nu är challenge INTE uppdaterad med booleanen");
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
        $( '#commentChallengeForm' ).each(function(){
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
            }).then(function(){
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

}]);