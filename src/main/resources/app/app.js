var app = angular.module('ChallengeAccepted', ['ngValidate', 'ngRoute']);

// Requires 'ngRoute' dependency.

app.config(function ($routeProvider) {

    $routeProvider
        .when('/available-challenges', {
            title: 'List of available challenges',
            templateUrl: 'views/list-challenges.html',
            controller: 'ChallengeController'
        })
        .when('/completed-challenges', {
            title: 'List of completed challenges',
            templateUrl: 'views/list-completed-challenges.html',
            controller: 'ChallengeController'
        })
        .when('/create-challenge', {
            title: 'Create a new challenge',
            templateUrl: 'views/create-challenge.html',
            controller: 'ChallengeController'
        })
        .when('/claimed-challenges', {
            title: 'Your claimed challenges',
            templateUrl: 'views/list-claimed-challenges.html',
            controller: 'ChallengeController'
        })
        .when('/approve-video', {
            title: 'Approve video',
            templateUrl: 'views/list-approve-videos.html',
            controller: 'ChallengeController'
        })
        .when('/home', {
            title: 'Challenge accepted',
            templateUrl: '/index.html',
            controller: 'ChallengeController'
        })
        .when('/notifications', {
            title: 'Notifications',
            templateUrl: 'views/notifications.html',
            controller: 'ChallengeController'
        })
        .when('/toplist', {
            title: 'Toplist',
            templateUrl: 'views/list-users.html',
            controller: 'ChallengeController'
        })
        .otherwise({redirectTo: "/home"})
});