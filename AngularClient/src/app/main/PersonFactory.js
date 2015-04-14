'use strict';

angular.module('bookr').factory('Person', ['$resource', function($resource){
    return $resource('http://localhost:8080/bookr/rest/v1/persons/:userId', {}, {
        query: {method:'GET', isArray: true, withCredentials: true}
    });
}]);
