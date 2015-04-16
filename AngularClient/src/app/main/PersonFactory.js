'use strict';

angular.module('bookr').factory('Person', ['$resource', function ($resource) {
    return $resource('http://localhost:8080/bookr/rest/v1/persons/:userId', {}, {
        query: {
            method: 'GET',
            headers: {Authorization: 'Basic ' + btoa('user' + ':' + 'user')},
            isArray: true
        }
    });
}]);
