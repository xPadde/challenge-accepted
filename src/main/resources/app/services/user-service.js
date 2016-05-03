app.factory('userService', function ($http) {
    return {
        createNewUser: function (data) {
            return $http({
                url: 'http://localhost:8080/api/user/',
                method: 'POST',
                data: data,
                header: {'Content-Type': 'application/json'}
            })
        }
        
    };
});