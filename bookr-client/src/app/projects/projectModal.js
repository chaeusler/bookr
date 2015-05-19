'use strict';

angular.module('bookr.projects')
  .controller('ProjectModalController', ['$scope', '$modalInstance', 'project', function ($scope, $modalInstance, project) {

    $scope.project = project;


    $scope.ok = function() {
      $modalInstance.close(project);
    };

    $scope.cancel = function() {
      $modalInstance.dismiss();
    }

  }]);
