'use strict';

angular.module('bookr').controller('PersonsCtrl', ['$scope', 'Person', function ($scope, Person) {
    $scope.persons = Person.query();
    $scope.update = function (person) {
        Person.update(person);
    };
}]);
