app.factory('notificationService', function ($http) {

    var baseUrl = "http://localhost:8080/api/challengeaccepted";

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