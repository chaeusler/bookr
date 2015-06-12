'use strict';

angular.module('bookr.login', ['Credentials'])
  .config(function ($stateProvider) {
    $stateProvider.state(
      'app.login',
      {
        url: '/login',
        templateUrl: 'app/login/login.html',
        controller: 'LoginController'
      }
    )
  })
  .controller('LoginController', ['$scope' , 'credentials', function ($scope, credentials) {
    $scope.credentials = credentials;



  }]);
