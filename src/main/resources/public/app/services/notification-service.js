app.factory('notificationService', function ($http) {

    var baseUrl = "http://localhost:8080/challengeaccepted/api";

    return {
        getAllNotifications: function (id) {
            return $http({
                url: baseUrl + '/users/'+ id +'/notifications/',
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })
        }
    }
});