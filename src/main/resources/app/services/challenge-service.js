app.factory('challengeService', function ($http) {
    return {
        getChallengeById: function(id) {
            return $http({
                url: 'http://localhost:8080/api/challenge/' + id,
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })
        },
        getListOfChallenges: function () {
            return $http({
                url: 'http://localhost:8080/api/challenges/',
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })
        },
        createNewChallenge: function (data) {
            return $http({
                url: 'http://localhost:8080/api/challenge/',
                method: 'POST',
                data: data,
                header: {'Content-Type': 'application/json'}
            })
        },
        updateChallenge: function (data) {
            return $http({
                url: 'http://localhost:8080/api/challenge/',
                method: 'PUT',
                data: data,
                header: {'Content-Type': 'application/json'}
            })
        },

        addUserToChallengeUpvoters: function (data) {
            var loggedInUser = angular.fromJson(sessionStorage.getItem("loggedInUser"));
            return $http({
                url: 'http://localhost:8080/api/challenge/' + loggedInUser.id + '/',
                method: 'PUT',
                data: data,
                header: {'Content-Type': 'application/json'}
            })
        }
    };

});