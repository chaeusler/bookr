'use strict';

angular.module('bookr.authorizations', ['uuid', 'bookr.base'])
  .config(function ($stateProvider) {
    $stateProvider.state(
      'app.authorizations',
      {
        url: '/authorizations',
        templateUrl: 'app/users/authorizations.html',
        controller: 'AuthorizationsController'
      }
    )
  })
  .controller('AuthorizationsController', ['rfc4122', '$scope', '$filter', 'Authorization', 'Person', function (uuid, $scope, $filter, Authorization, Person) {
    $scope.authorizations = Authorization.query();

    $scope.persons = Person.query(function(){
      angular.forEach($scope.persons, function(person){
        var collection = this;
        Authorization.get({authorizationId: person.id}).$promise.then(function(auth){
          collection.push({person: person, authorization: auth})
        }, function() {
          collection.push({person: person})
        });
      }, $scope.users);
    });

    $scope.visibleRoles = [
      {value: 'MANAGER', text: "Manager"},
      {value: 'ADMINISTRATOR', text: "Administrator"}
    ];

    $scope.users = [];

    var findAuthorization = function(id) {
      return $filter('filter')($scope.authorizations, { id: id })[0];
    };

    $scope.showRoles = function(authorization) {
      var selected = [];
      angular.forEach($scope.visibleRoles, function(vr) {
        if (authorization.roles.indexOf(vr.value) >= 0) {
          selected.push(vr.text);
        }
      });
      return selected.length ? selected.join(', ') : '';
    };

    $scope.update = function (data, id) {
      angular.extend(data, {id: id});
      Authorization.update(data);
    };

    $scope.delete = function(index) {
      var toDelete = $scope.persons[index];
      $scope.authorizations.splice(index, 1);
      toDelete.$delete();
    };

    $scope.add = function() {
      var id = uuid.v4();
      var newAuthorization = new Authorization({
        id: id,
        principalName: '',
        roles: [
          "USER"
        ]
      });
      newAuthorization.$save();
      $scope.authorizations.push(newAuthorization);
    };

  }]);
