'use strict';

angular.module('bookr.projects', ['uuid', 'bookr.base'])
  .config(function ($stateProvider) {
    $stateProvider
      .state('app.projects', {
        url: '/projects',
        abstract: true,
        template: '<div ui-view></div>',
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
      .state('app.projects.create', {
        url: '/create',
        templateUrl: 'app/projects/projects.create.html',
        controller: 'ProjectsDetailsController'
      })
  })
  .controller('ProjectsController', ['$scope', '$state', 'rfc4122', 'Project', 'Person', '$location', function ($scope, $state, uuid, Project, Person, $location) {
    $scope.projects = Project.query();

    $scope.allPersons = Person.query();

    $scope.project = {};

    $scope.detail = function(project){
      $scope.project = project;

      $location.path("/projects/" + project.id);
    };

    $scope.createProject = function() {
      $scope.project = new Project();
      $scope.project.id = uuid.v4();
      $scope.project['person-ids'] = [];

      $state.go('app.projects.create');
    }

  }]).controller('ProjectsDetailsController', ['$scope', '$state', 'Person', 'Project', function($scope, $state, Person, Project) {

    $scope.persons = [];

    angular.forEach($scope.project['person-ids'], function(personId){
      Person.get({personId: personId}, function (person) {
        $scope.persons.push(person);
      });
    });

    $scope.isPartOfIt = function(person) {
      if ($scope.project['person-ids']) {
        return $scope.project['person-ids'].indexOf(person.id) !== -1;
      }
      return false;
    };

    $scope.toggle = function(person) {
      var personIds = $scope.project['person-ids'];
      var index = personIds.indexOf(person.id);
      if (index == -1) {
        personIds.push(person.id);
      } else {
        personIds.splice(index, 1);
      }
    };

    $scope.update = function(){
      Project.update($scope.project);
      $state.go('app.projects.list');
    };

    $scope.save = function(){
      $scope.project.$save();
      $state.go('app.projects.list');
    };

    $scope.cancel = function(){
      $scope.projects = Project.query();
      $state.go('app.projects.list');
    }

  }]);
