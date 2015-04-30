'use strict';

angular
    .module('bookr.error', ['bookr.base'])
    .config(function ($stateProvider) {
        $stateProvider
            .state('bookr.error', {
                url: '/error/:error',
                templateUrl: 'app/error/error.html',
                accessLevel: accessLevels.public
            });
    });
