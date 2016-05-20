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
        }
    }
});