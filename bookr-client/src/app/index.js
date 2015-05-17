'use strict';

angular.module('bookr',
  [
    'loginService',
    'bookr.error',
    'bookr.login',
    'bookr.home',
    'bookr.persons',
    'bookr.authorizations',
    'ngAnimate',
    'ngCookies',
    'ngTouch',
    'ngSanitize',
    'ngResource',
    'ngRoute',
    'ui.bootstrap',
    'xeditable'])
  .config(function ($urlRouterProvider) {
    $urlRouterProvider.otherwise('/');
  })
  .run(function ($rootScope, $window) {
    // google analytics
    $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams) {
      var realURL = toState.url;
      if (!!$window.ga) {
        // resolves variables inside urls, ex: /error/:error in /error/unauthorized
        for (var v in toParams) {
          realURL = realURL.replace(':' + v, toParams[v]);
        }
        $window.ga('send', 'pageview', realURL);
      }
    });
    /**
     * $rootScope.doingResolve is a flag useful to display a spinner on changing states.
     * Some states may require remote data so it will take awhile to load.
     */
    var resolveDone = function () {
      $rootScope.doingResolve = false;
    };
    $rootScope.doingResolve = false;

    $rootScope.$on('$stateChangeStart', function () {
      $rootScope.doingResolve = true;
    });
    $rootScope.$on('$stateChangeSuccess', resolveDone);
    $rootScope.$on('$stateChangeError', resolveDone);
    $rootScope.$on('$statePermissionError', resolveDone);
  })
  .controller('BodyController', function ($scope, $state, $stateParams) {
    // Expose $state and $stateParams to the <body> tag
    $scope.$state = $state;
    $scope.$stateParams = $stateParams;
  });



