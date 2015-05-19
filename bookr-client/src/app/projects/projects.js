'use strict';

angular.module('bookr.projects', ['uuid', 'bookr.base'])
  .config(function ($stateProvider) {
    $stateProvider
      .state('app.projects', {
        url: '/projects',
        abstract: true,
        templateUrl: 'app/projects/projects.html',
        controller: 'ProjectsController'
      })
      .state('app.projects.list', {
        url: '/list',
        templateUrl: 'app/projects/projects.list.html'
      })
      .state('app.projects.detail', {
        url: '/:id',
        templateUrl: 'app/projects/projects.detail.html',
        controller: 'ProjectsDetailsController'
      })
  })
  .controller('ProjectsController', ['$scope', '$modal', 'Project', '$location', function ($scope, $modal, Project, $location) {
    $scope.projects = Project.query();

    $scope.project = {};

    $scope.detail = function(project){
      $scope.project = project;

      $location.path("/projects/" + project.id);
    };

    $scope.open = function (selectedProject) {

      var modalInstance = $modal.open({
        templateUrl: 'app/projects/projectModal.html',
        controller: 'ProjectModalController',
        resolve: {
          project: function () {
            return selectedProject;
          }
        }
      });
      modalInstance.result.then(function (project) {
        // TODO only submit when changed
        Project.update(project);
      }, function () {
        $scope.projects = Project.query();
      });

    };

  }]).controller('ProjectsDetailsController', ['$scope', 'Person', function($scope, Person) {

    $scope.persons = [];

    angular.forEach($scope.project['person-ids'], function(personId){
      Person.get({personId: personId}, function (person) {
        $scope.persons.push(person);
      });
    });
  }]);
