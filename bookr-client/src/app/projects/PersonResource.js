'use strict';

angular.module('bookr.projects')
  .factory('Person', ['$resource', function ($resource) {
  return $resource('http://localhost:8080/bookr/rest/v1/persons/:personId', {personId: '@id'});
}]);
