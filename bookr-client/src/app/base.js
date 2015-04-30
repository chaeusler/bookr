'use strict';

angular.module('bookr.base', ['ui.router', 'ngResource'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('app', {
            abstract: true,
            template: '<ui-view></ui-view>',

            resolve: {
                'login': function (loginService, $q, $http) {
                    var roleDefined = $q.defer();

                    /**
                     * In case there is a pendingStateChange means the user requested a $state,
                     * but we don't know yet user's userRole.
                     *
                     * Calling resolvePendingState makes the loginService retrieve his userRole remotely.
                     */
                    if (loginService.pendingStateChange) {
                        return loginService.resolvePendingState($http.get('/user'));
                    } else {
                        roleDefined.resolve();
                    }
                    return roleDefined.promise;
                }
            }
        })
    }])
  .config(['$httpProvider', function($httpProvider){
    $httpProvider.defaults.headers.common.Authorization = 'Basic ' + btoa('administrator' + ':' + 'administrator');
  }])
  .config(['$resourceProvider', function($resourceProvider){
    $resourceProvider.defaults.actions['update'] = {method: 'PUT'};
  }]);

