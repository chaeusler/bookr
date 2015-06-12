'use strict';

angular.module('bookr.base', ['ui.router', 'ngResource', 'Credentials'])
  .factory('sessionInjector', ['credentials', function(credentials) {
    var sessionInjector = {
      request: function(config) {
          config.headers['Authorization'] = 'Basic ' + btoa(credentials.name + ':' + credentials.password);
        return config;
      }
    };
    return sessionInjector;
  }])
  .config(['$httpProvider', function($httpProvider) {
    $httpProvider.interceptors.push('sessionInjector');
  }])
  //.config(['$httpProvider', function($httpProvider){
    // TODO use http-interceptor instead
    //$httpProvider.defaults.headers.common.Authorization = 'Basic ' + btoa('administrator' + ':' + 'administrator');
  //}])

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
  .config(['$resourceProvider', function($resourceProvider){
    $resourceProvider.defaults.actions['update'] = {method: 'PUT'};
  }]);

