app.controller('ChallengeProfileController', ['scopeService', '$scope', function (scopeService, $scope) {
    console.log("ChallengeProfileController laddas");
    $scope.activeChallenge = scopeService.getActiveChallenge();
    console.log("active challenge Scope: " + $scope.activeChallenge);
}]);