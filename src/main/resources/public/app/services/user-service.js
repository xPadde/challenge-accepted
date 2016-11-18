app.factory('userService', function ($http) {

    var baseUrl = "http://localhost:8080/challengeaccepted/api";

    return {
        createNewUser: function (data) {
            return $http({
                url: baseUrl + '/users',
                method: 'POST',
                data: data,
                header: {'Content-Type': 'application/json'}
            })
        },

        getUserById: function (id) {
            return $http({
                url: baseUrl + '/users/' + id,
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })
        },

        getUserByEmail: function (email) {
            return $http({
                url: baseUrl + '/users/find-by-email?email=' + email,
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })
        },

        validateLocalLogin: function (email, password) {
            console.log("i servicen");
            return $http({
                url: baseUrl + '/users/login?email=' + email + '&password=' + password,
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })
        },

        updateUser: function (data, id) {
            return $http({
                url: baseUrl + '/users' + id,
                method: 'PUT',
                header: {'Content-Type': 'application/json'}
            })
        },

        getListOfAllUsers: function () {
            return $http({
                url: baseUrl + '/users',
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })
        }
    };

});