'use strict';

angular.module('bookr.users')
  .controller('UserModalController', ['$scope', '$modalInstance', 'user', function ($scope, $modalInstance, user) {

    $scope.user = user;

    $scope.toggleRole = function(rolename) {
      var roles = $scope.user.authorization.roles;
      var index = roles.indexOf(rolename);
      if (index == -1) {
        roles.push(rolename);
      } else {
        roles.splice(index, 1);
      }
    };

    $scope.ok = function() {
      $modalInstance.close(user);
    };

    $scope.cancel = function() {
      $modalInstance.dismiss();
    }

  }]);
