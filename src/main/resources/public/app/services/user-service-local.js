app.factory('userServiceLocal', function ($http) {

    var session = {};

    session.login = function (data) {
        return $http.post('/challengeaccepted/login', 'username=' + data.email +
            '&password=' + data.password,
            {headers: {'Content-type': 'application/x-www-form-urlencoded'}}
        ).then(function (data) {
            alert('Great SUCCESS');
            localStorage.setItem('session', {});
        }, function (data) {
            alert('FAILURE!1');
        });
    };

    session.logout = function () {
        localStorage.removeItem('session');
    };

    session.isLoggedIn = function () {
        return localStorage.getItem('session') !== null;
    };

    return session;
});