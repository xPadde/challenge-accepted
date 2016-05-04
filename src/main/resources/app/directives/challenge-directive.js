app.directive("directiveChallenge", function () {
    return {
        restrict: 'C',
        templateUrl: 'views/challenge-profile.html'
    }
});

app.directive("directiveCreateChallenge", function () {
    return {
        restrict: 'C',
        templateUrl: 'views/create-challenge.html'
    }
});

app.directive("directiveListChallenges", function () {
    return {
        restrict: 'C',
        templateUrl: 'views/list-challenges.html'
    }
});

app.directive("directiveListYourChallenges", function () {
    return {
        restrict: 'C',
        templateUrl: 'views/list-your-challenges.html'
    }
});
app.directive("directiveChallengeProfile", function () {
    return {
       restrict: 'C',
        templateUrl: 'views/challenge-profile.html'
    } 
});    
