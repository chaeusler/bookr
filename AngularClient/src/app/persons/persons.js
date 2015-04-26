'use strict';

angular.module('bookr.persons', ['bookr.base'])
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
  .controller('PersonsController', ['$scope', 'Person', function ($scope, Person) {
    $scope.persons = Person.query();

    $scope.newPerson = {};

    $scope.update = function (person) {
      Person.update(person);
    };

    $scope.createPerson = function() {
      newPerson.userId = newPerson.principalName; // TODO generate
      Person.create(newPerson);
    };
  }]);
