'use strict';

angular.module('bookr.persons', ['uuid', 'bookr.base'])
  .config(function ($stateProvider) {
    $stateProvider.state(
      'app.persons',
      {
        url: '/persons',
        templateUrl: 'app/persons/persons.html',
        controller: 'PersonsController'
      }
    )
  })
  .controller('PersonsController', ['rfc4122', '$scope', 'Person', function (uuid, $scope, Person) {
    $scope.persons = Person.query();
    $scope.personToAdd = {};

    $scope.update = function (person) {
      Person.update(person);
    };

    $scope.createPerson = function () {
      $scope.personToAdd.id = $scope.personToAdd.principalName; // TODO generate
      var newPerson = new Person($scope.personToAdd);
      newPerson.$create();
      // TODO reload
    };

    $scope.savePerson = function (data, id) {
      angular.extend(data, {id: id});
      Person.update(data);
    };

    $scope.removePerson = function(index) {
      $scope.persons.splice(index, 1);
      // TODO call Resource
    };

    $scope.addPerson = function() {
      $scope.inserted = {
        id: uuid.v4(),
        principalName: ''
      };
    };

  }]);
