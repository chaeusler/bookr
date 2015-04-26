'use strict';

angular.module('bookr.persons').factory('Person', ['$resource', function ($resource) {
    return $resource('http://localhost:8080/bookr/rest/v1/persons/:userId', {userId: '@id'}, {
        query: {
            method: 'GET',
            headers: {Authorization: 'Basic ' + btoa('administrator' + ':' + 'administrator')},
            isArray: true
        },
        update: {
            method: 'PUT',
            headers: {Authorization: 'Basic ' + btoa('administrator' + ':' + 'administrator')}
        }, create: {
             method: 'POST',
             headers: {Authorization: 'Basic ' + btoa('administrator' + ':' + 'administrator')}
      }
    });
}]);
