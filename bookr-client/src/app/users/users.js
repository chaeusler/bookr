'use strict';

angular.module('bookr.users', ['uuid', 'bookr.base'])
  .config(function ($stateProvider) {
    $stateProvider.state(
      'app.users',
      {
        url: '/users',
        templateUrl: 'app/users/users.html',
        controller: 'UsersController'
      }
    )
  })
  .controller('UsersController', ['$scope', '$filter', '$modal', 'rfc4122',  'Authorization', 'Person', function ($scope, $filter, $modal, uuid,  Authorization, Person) {

    $scope.persons = Person.query();

    $scope.open = function(selectedPerson) {

      var user = {
        person: selectedPerson,
        authorization: Authorization.get({authorizationId:selectedPerson.id})
      };

      var modalInstance = $modal.open({
        templateUrl: 'app/users/userModal.html',
        controller: 'UserModalController',
        resolve: {
          user: function() {
            return user;
          }
        }
      });
      modalInstance.result.then(function(user){
        // TODO only submit when changed
        Person.update(user.person);
        Authorization.update(user.authorization);
      }, function() {
        $scope.persons = Person.query();
      });

    };
  }]);
