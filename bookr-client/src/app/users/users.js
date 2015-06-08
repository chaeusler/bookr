'use strict';

angular.module('bookr.users', ['uuid', 'bookr.base'])
  .config(function ($stateProvider) {
    $stateProvider
      .state('app.users', {
        url: '/users',
        abstract: true,
        template: '<div ui-view></div>',
        controller: 'UsersController'
      })
      .state('app.users.list', {
        url: '/list',
        templateUrl: 'app/users/users.list.html'
      })
      .state('app.users.detail', {
        url: '/:id',
        templateUrl: 'app/users/users.detail.html',
        controller: 'UsersDetailController'
      })
  })
  .controller('UsersController', ['$scope', '$filter', '$location', 'rfc4122',  'Authorization', 'Person', function ($scope, $filter, $location, uuid,  Authorization, Person) {

    $scope.persons = Person.query();

    $scope.user = {};

    $scope.detail = function(selectedPerson){
      $scope.user = {
        person: selectedPerson,
        authorization: Authorization.get({authorizationId:selectedPerson.id})
      };

      $location.path("/users/" + $scope.user.person.id);
    };

  }]).controller('UsersDetailController', ['$scope', '$state', 'Person', 'Authorization', function ($scope, $state, Person, Authorization) {

    $scope.toggleRole = function(rolename) {
      var roles = $scope.user.authorization.roles;
      var index = roles.indexOf(rolename);
      if (index == -1) {
        roles.push(rolename);
      } else {
        roles.splice(index, 1);
      }
    };

    $scope.update = function() {
      Person.update($scope.user.person);
      Authorization.update($scope.user.authorization);
      $state.go('app.users.list');
    };

    $scope.cancel = function() {
      $scope.persons = Person.query();
      $state.go('app.users.list');
    }

  }]);
