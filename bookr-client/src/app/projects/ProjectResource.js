'use strict';

angular.module('bookr.projects')
  .factory('Project', ['$resource', function ($resource) {
    return $resource('http://localhost:8080/bookr/rest/v1/projects/:projectId', {projectId: '@id'});
  }]);
