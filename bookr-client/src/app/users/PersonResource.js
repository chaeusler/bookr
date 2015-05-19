'use strict';

angular.module('bookr.users')
  .factory('Person', ['$resource', function ($resource) {
  return $resource('http://localhost:8080/bookr/rest/v1/persons/:userId', {userId: '@id'});
}]);