'use strict';

angular.module('bookr.authorizations')
  .factory('Authorization', ['$resource', function ($resource) {
  return $resource('http://localhost:8080/bookr/rest/v1/authorizations/:authorizationId', {authorizationId: '@id'});
}]);
