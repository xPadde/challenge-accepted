//app.factory('commentService', function ($http) {
//    return {
//        addCommentToChallenge: function (data, challengeId) {
//            return $http({
//                url: 'http://localhost:8080/api/challenge/' + challengeId + '/comment/',
//                method: 'POST',
//                data: data,
//                header: {'Content-Type': 'application/json'}
//            })
//        }
//    };
//});