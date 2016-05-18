app.factory('challengeService', function ($http) {
    return {
        getChallengeById: function (id) {
            return $http({
                url: 'http://localhost:8080/api/challenge/' + id,
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })
        },
        getListOfAllChallenges: function () {
            return $http({
                url: 'http://localhost:8080/api/challenges/',
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })
        },
        createNewChallenge: function (data, loggedInUserId) {
            return $http({
                url: 'http://localhost:8080/api/challenge/create/challenge-creator/' + loggedInUserId,
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

        updateChallengeClaimer: function (loggedInUser, challengeId) {
            return $http({
                url: 'http://localhost:8080/api/challenge/' + challengeId + '/update-challenge-claimer/',
                method: 'PUT',
                data: loggedInUser,
                header: {'Content-Type': 'application/json'}
            })
        },

        addYoutubeUrlToChallenge: function (challengeId, youtubeUrl) {
            return $http({
                url: 'http://localhost:8080/api/challenge/' + challengeId + '/add-youtube-url/',
                method: 'PUT',
                data: youtubeUrl,
                header: {'Content-Type': 'application/json'}
            })
        },

        confirmUploadedYoutubeUrl: function (challengeId) {
            return $http({
                url: 'http://localhost:8080/api/challenge/' + challengeId + '/confirm-uploaded-youtube-url/',
                method: 'PUT',
                header: {'Content-Type': 'application/json'}
            })
        },

        addOrRemoveUserToChallengeUpvoters: function (data, id) {
            return $http({
                url: 'http://localhost:8080/api/challenge/' + id + '/add-or-remove-user-to-challenge-upvoters/',
                method: 'PUT',
                data: data,
                header: {'Content-Type': 'application/json'}
            })
        },

        addOrRemovePointToCompletedChallenge: function (data, id) {
            return $http({
                url: 'http://localhost:8080/api/challenge/' + id + '/add-or-remove-point-to-completed-challenge/',
                method: 'PUT',
                data: data,
                header: {'Content-Type': 'application/json'}
            })
        },

        assignPointsToUser: function (id) {
            return $http({
                url: 'http://localhost:8080/api/challenge/' + id + '/assign-points-to-user/',
                method: 'PUT',
                header: {'Content-Type': 'application/json'}
            })
        },

        disapproveCurrentChallenge: function (id) {
            return $http({
                url: 'http://localhost:8080/api/challenge/' + id + '/disapprove-challenge/',
                method: 'PUT',
                header: {'Content-Type': 'application/json'}
            })
            
        },

        addCommentToChallenge: function (data, challengeId) {
            return $http({
                url: 'http://localhost:8080/api/challenge/' + challengeId + '/comment/',
                method: 'POST',
                data: data,
                header: {'Content-Type': 'application/json'}
                // TODO move this to comment-service!
            })

        },

        getListOfUnapprovedChallenges: function () {
            return $http({
                url: 'http://localhost:8080/api/challenges/unapproved/',
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })
        },

        getListOfCompletedChallenges: function() {
            return $http({
                url: 'http://localhost:8080/api/challenges/completed/',
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })
        }
    }
});