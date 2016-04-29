app.factory('challengeService', function ($http) {
    return {
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
        }
    };

});