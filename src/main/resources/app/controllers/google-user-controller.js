app.controller('GoogleUserController', ['$scope','userService', function($scope, userService){
    
    function onSignIn(googleUser) {
        var profile = googleUser.getBasicProfile();
        var idToken = googleUser.getAuthResponse().id_token;
        console.log("id token här " + idToken);
        console.log('ID: ' + profile.getId());
        console.log('Name: ' + profile.getName());
        console.log('Image URL: ' + profile.getImageUrl());
        console.log('Email: ' + profile.getEmail());

        userService.createNewUser($scope.getUserInfo(profile)).success(function () {
            console.log('userService created a new user and saved it to database!')
        });

        $scope.showSecretListOfChallengesSection();

    }

    $scope.getUserInfo = function(profile, idToken){
        return JSON.stringify({
            'firstName': profile.getGivenName(),
            'lastName': profile.getFamilyName(),
            'email': profile.getEmail()
        });
    }

    window.onSignIn = onSignIn;

    $scope.signOut = function () {
        var auth2 = gapi.auth2.getAuthInstance();
        auth2.signOut().then(function () {
            console.log('User signed out.');
            console.log(auth2.isSignedIn.get());
        });
    }


}]);