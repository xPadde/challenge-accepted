var app = angular.module('ChallengeAccepted', ['ngValidate', 'ngRoute']);

// Requires 'ngRoute' dependency.

app.config(function ($routeProvider) {

    $routeProvider
        .when('/available-challenges', {
            title: 'List of available challenges',
            templateUrl: 'views/list-challenges.html',
            controller: 'ListController'
        })
        .when('/completed-challenges', {
            title: 'List of completed challenges',
            templateUrl: 'views/list-completed-challenges.html',
            controller: 'ListController'
        })
        .when('/create-challenge', {
            title: 'Create a new challenge',
            templateUrl: 'views/create-challenge.html',
            controller: 'CreateChallengeController'
        })
        .when('/claimed-challenges', {
            title: 'Your claimed challenges',
            templateUrl: 'views/list-claimed-challenges.html',
            controller: 'ListController'
        })
        .when('/approve-video', {
            title: 'Approve video',
            templateUrl: 'views/list-approve-videos.html',
            controller: 'ApproveVideoController'
        })
        .when('/notifications', {
            title: 'Notifications',
            templateUrl: 'views/notifications.html',
            controller: 'NotificationsController'
        })
        .when('/challenge-profile/:id', {
            title: 'Challenge profile',
            templateUrl: 'views/challenge-profile.html',
            controller: 'ChallengeProfileController'
        })
        .when('/user-profile/:id', {
            title: 'User profile',
            templateUrl: 'views/user-profile.html',
            controller: 'UserController'
        })
        .when('/toplist', {
            title: 'Toplist',
            templateUrl: 'views/list-users.html',
            controller: 'ListController'
        })
        .when('/home', {
            title: 'Home',
            templateUrl: 'views/list-challenges-not-logged-in.html',
            controller: 'ListController'
        })
        .when('/error-challenge', {
            title: 'Error challenge',
            templateUrl: 'views/errorpage-challenge.html'
        })
        .when('/error-user', {
            title: 'Error User profile',
            templateUrl: 'views/errorpage-user.html'
        })
        .when('/register', {
            title: "Register new user",
            templateUrl: 'views/register-new-user.html',
            controller: 'RegisterNewUserController'
        })


        .otherwise({redirectTo: "/home"})
});