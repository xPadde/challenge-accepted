app.factory('userService', function ($http) {
    return {
        createNewUser: function (data) {
            return $http({
                url: 'http://localhost:8080/api/user/',
                method: 'POST',
                data: data,
                header: {'Content-Type': 'application/json'}
            })
        },

        getUserByEmail: function (email) {
            return $http({
                url: 'http://localhost:8080/api/user/find-by-email?email=' + email + '',
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })
        },

        updateUser: function (data, id) {
            return $http({
                url: 'http://localhost:8080/api/user/' + id,
                method: 'PUT',
                header: {'Content-Type': 'application/json'}
            })
        },

        getListOfAllUsers: function () {
            return $http({
                url: 'http://localhost:8080/api/users/',
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })
        }
    };

});