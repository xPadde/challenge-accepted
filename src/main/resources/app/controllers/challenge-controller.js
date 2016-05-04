app.controller('ChallengeController', ['$scope', '$http', '$sce', 'challengeService', 'userService', function ($scope, $http, $sce, challengeService, userService) {

    var list = [];
    $scope.orderByField = 'creationDate';
    $scope.reverseSort = true;

    var loggedInUser;

    $scope.getUserInputsFromCreateChallengeForm = function () {
        return JSON.stringify({
            'topic': $('#input-topic').val(),
            'description': $('#input-description').val(),
            'creationDate': null
        });
    };

    $scope.showCreateChallengeSection = function () {
        $scope.section = "createNewChallengeSection";
    };

    $scope.showListOfChallengesSection = function () {
        $scope.section = "listOfChallengesSection";
    };

    $scope.showSecretListOfChallengesSection = function () {
        $scope.section = "secretListOfChallengesSection";
    };

    $scope.createNewChallenge = function () {
        challengeService.createNewChallenge($scope.getUserInputsFromCreateChallengeForm())
            .success(function () {
                console.log('challengeService.createNewChallenge() called and it created a new challenge and saved it to the database!');
                $scope.getListOfChallenges()

            })
            .error(function () {
                console.log('challengeService.createNewChallenge() called and it ***FAILED*** to create new challenge');
            })
    };

    $scope.getListOfChallenges = function () {
        challengeService.getListOfChallenges()
            .success(function (response) {
                $scope.listOfChallenges = response;
                console.log('challengeService.getListOfChallenges() fetched all the challenges from the database successfully!')
            })
            .error(function () {
                console.log('challengeService.getListOfChallenges() ***FAILED*** to fetch all the challenges from the database!')
            })
    };

    $scope.upvoteChallenge = function (challenge) {
        challenge.upvotes += 1;
        /*        challengeService.updateChallenge(challenge);*/
        loggedInUser = angular.fromJson(sessionStorage.getItem("loggedInUser"));
        challenge.challengeUpvoters.push(loggedInUser);
        challengeService.updateChallenge(angular.toJson(challenge)).success(function () {
            console.log("add upvote: nu gick det bra");
        }).error(function () {
            console.log("add upvote: nu gick det INTE bra");
        });

        /*challengeService.addChallengeToUpvotedChallenges(angular.toJson(challenge)).success(function(){
         console.log("add upvote: nu gick det bra");
         }).error(function(){
         console.log("add upvote: nu gick det INTE bra");
         });*/
    };

    $scope.claimCurrentChallenge = function (challenge) {

        loggedInUser = angular.fromJson(sessionStorage.getItem("loggedInUser"));

        challenge.challengeClaimer = loggedInUser;
        challenge.challengeClaimed = true;

        challengeService.updateChallenge(angular.toJson(challenge)).success(function () {
            console.log("claim challenge: Gick bra");
        }).error(function (data) {
            console.log("claim challenge: gick INTE bra");
            console.log(data);
        });

    };

    // Update the list of challenges on page load.
    $scope.getListOfChallenges();

    $scope.viewChallengeProfilePage = function (challenge) {
        $scope.section = "challengeProfilePageSection";
        $scope.challengeProfileToView = challenge;
        console.log("challengeProfileToView object: ");
        console.log($scope.challengeProfileToView);
    };

    $scope.addYoutubeUrlToCurrentChallenge = function (challenge) {
        var userProvidedUrl = $('#input-youtube-url').val();
        challenge.youtubeURL = convertToYouTubeEmbedUrl(userProvidedUrl);
    };


    $scope.markUrlAsTrusted = function (src) {
        return $sce.trustAsResourceUrl(src);
    };

    var convertToYouTubeEmbedUrl = function (url) {
        var isYouTubeUrlCorrect = url.indexOf("watch?v=") > 1;
        var baseUrl = "https://www.youtube.com/embed/";

        if (isYouTubeUrlCorrect) {
            var youTubeVideoId = url.substr(32);
            var finalUrl = baseUrl + youTubeVideoId;
        } else {
            alert("Please provide a valid YouTube URL");
            return "";
        }
        return finalUrl;
    }


}]);