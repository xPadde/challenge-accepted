app.service('scopeService', function () {

    var activeChallenge;
    var activeUser;

    return {
        setActiveUser: function (user) {
            activeUser = user;
        },

        getActiveUser: function () {
            return activeUser;
        },

        setActiveChallenge: function (challenge) {
            activeChallenge = challenge;
        },

        getActiveChallenge: function () {
            return activeChallenge;
        },

        viewUserProfilePage: function (user, userService, $location) {
            userService.getUserByEmail(user.email).success(function (response) {
                activeUser = response;
                $location.path('/user-profile/' + response.id);
            });
        }
    }
});