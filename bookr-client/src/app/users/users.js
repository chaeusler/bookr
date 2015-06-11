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
      .state('app.users.create', {
        url: '/create',
        templateUrl: 'app/users/users.detail.html',
        controller: 'UsersDetailController'
      })
  })
  .controller('UsersController', ['$scope', '$state', '$location', 'rfc4122',  'Authorization', 'Person', function ($scope, $state, $location, uuid,  Authorization, Person) {

    $scope.persons = Person.query();

    $scope.user = {};

    $scope.detail = function(selectedPerson){
      $scope.user = {
        person: selectedPerson,
        authorization: Authorization.get({authorizationId:selectedPerson.id})
      };

      $location.path("/users/" + $scope.user.person.id);
    };

    $scope.createUser = function() {
      var id = uuid.v4();
      $scope.user = {
        person: new Person(),
        authorization: new Authorization()
      };

      $scope.user.person.id = id;
      $scope.user.authorization.id = id;
      $scope.user.authorization.roles = [];

      $state.go("app.users.create");
    }

  }]).controller('UsersDetailController', ['$scope', '$state', 'Person', 'Authorization', 'Password',
    function ($scope, $state, Person, Authorization, Password) {

    $scope.newPassword = new Password();

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
      $scope.newPassword.id = $scope.user.authorization.id;
      Password.update($scope.newPassword);
      Person.update($scope.user.person);
      Authorization.update($scope.user.authorization);
      $state.go('app.users.list');
    };

    $scope.save = function() {
      $scope.user.person.$save(function(person){
        $scope.user.authorization.$save(function(authorization){
          $scope.newPassword.id = $scope.user.authorization.id;
          $scope.newPassword.authorization = $scope.user.authorization.id;
          $scope.newPassword.$save(function(){
            $scope.persons.push($scope.user.person);
          }, function(){
            authorization.$delete();
            person.$delete();
          })
        }, function(){
          person.$delete();
        });
      });

      $state.go('app.users.list');
    };

    $scope.cancel = function() {
      $scope.persons = Person.query();
      $state.go('app.users.list');
    };

    $scope.delete = function() {
      $scope.user.authorization.$delete(function(){
        $scope.user.person.$delete(function(){
          $scope.persons = Person.query();
        });
      });
      $state.go('app.users.list');
    };

  }]);
