app.controller('ChallengeController', ['$scope', '$location', '$route', '$http', '$sce', 'challengeService', 'userService', 'scopeService', 'notificationService',
    function ($scope, $location, $route, $http, $sce, challengeService, userService, scopeService, notificationService) {
        
        $scope.dynamicUrl = "www.youtube.com/watch?v=elN_CPsJ27M";

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