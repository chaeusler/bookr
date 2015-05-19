'use strict';

angular.module('bookr.projects', ['uuid', 'bookr.base'])
  .config(function ($stateProvider) {
    $stateProvider.state(
      'app.projects',
      {
        url: '/projects',
        templateUrl: 'app/projects/projects.html',
        controller: 'ProjectsController'
      }
    )
  })
  .controller('ProjectsController', ['$scope', '$modal', 'Project', function ($scope, $modal, Project) {
    $scope.projects = Project.query();

    $scope.open = function(selectedProject) {

      var modalInstance = $modal.open({
        templateUrl: 'app/projects/projectModal.html',
        controller: 'ProjectModalController',
        resolve: {
          project: function() {
            return selectedProject;
          }
        }
      });
      modalInstance.result.then(function(project){
        // TODO only submit when changed
        Project.update(project);
      }, function() {
        $scope.projects = Project.query();
      });

    };

  }]);
