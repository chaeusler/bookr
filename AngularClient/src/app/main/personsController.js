'use strict';

angular.module('bookr').controller('PersonsCtrl', ['$scope', 'Person', function ($scope, Person) {
        $scope.persons = Person.query();
        $scope.selected = {};

}]);
