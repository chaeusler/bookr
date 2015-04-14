'use strict';

angular.module('bookr', ['ngAnimate', 'ngCookies', 'ngTouch', 'ngSanitize', 'ngResource', 'ngRoute', 'ui.bootstrap', 'xeditable'])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/main', {
                templateUrl: 'app/main/main.html',
                controller: 'MainCtrl'
            })
            .when('/persons', {
                templateUrl: 'app/main/persons.html',
                controller: 'PersonsCtrl'
            })
            .otherwise({
                redirectTo: '/main'
            });
    });

