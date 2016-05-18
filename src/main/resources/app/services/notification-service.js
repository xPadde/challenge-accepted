app.factory('notificationService', function ($http) {

    var baseUrl = 'http://localhost:8080/api/';

    return {
        getAllNotifications: function () {
            return $http({
                url: baseUrl + 'notifications/',
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })
        }
    }
});