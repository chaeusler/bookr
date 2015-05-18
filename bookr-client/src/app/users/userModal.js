'use strict';

angular.module('bookr.users')
  .controller('UserModalController', ['$scope', '$modalInstance', 'user', function ($scope, $modalInstance, user) {

    $scope.user = user;

    $scope.ok = function() {
      $modalInstance.close(user);
    };

    $scope.cancel = function() {
      $modalInstance.dismiss();
    }

  }]);
