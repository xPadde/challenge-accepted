app.factory('notificationService', function ($http) {

    var baseUrl = "http://localhost:8080/api/";

    return {
        getAllNotifications: function (id) {
            return $http({
                url: baseUrl + 'user/'+ id +'/notifications/',
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })
        }
    }
});