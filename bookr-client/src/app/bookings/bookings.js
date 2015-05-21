'use strict';

angular.module('bookr.bookings', ['uuid', 'bookr.base'])
  .config(function ($stateProvider) {
    $stateProvider
      .state('app.bookings', {
        url: '/bookings',
        abstract: true,
        template: '<div ui-view></div>',
        controller: 'BookingsController'
      })
      .state('app.bookings.list', {
        url: '/list',
        templateUrl: 'app/bookings/bookings.list.html'
      })
      .state('app.bookings.detail', {
        url: '/:id',
        templateUrl: 'app/bookings/bookings.detail.html',
        controller: 'BookingsDetailsController'
      })
  })
  .controller('BookingsController', ['$scope', 'Booking', 'Person', 'Project', '$location', function ($scope, Booking, Person, Project, $location) {
    $scope.bookings = Booking.query();

    $scope.personsMap = {};
    var persons = Person.query(function(){
      angular.forEach(persons, function(person){
        $scope.personsMap[person.id] = person;
      })
    });

    $scope.projectsMap = {};
    var projects = Project.query(function(){
      angular.forEach(projects, function(project){
        $scope.projectsMap[project.id] = project;
      })
    });

    $scope.booking = {};

    $scope.getPersonFor = function(booking){
      return $scope.personsMap[booking['person-id']];
    };

    $scope.getProjectFor = function(booking){
      return $scope.projectsMap[booking['project-id']];
    };

    $scope.formatDate = function(date){
      return date.year + "-" + date.dayOfMonth + "-" + date.monthValue + " " + date.hour + ":" + date.minute;
    };

    $scope.detail = function(booking){
      $scope.booking = booking;

      $location.path("/bookings/" + booking.id);
    };

  }]).controller('BookingsDetailsController', ['$scope', '$state', 'Booking', function($scope, $state, Booking) {

    $scope.update = function(){
      Booking.update($scope.booking);
      $state.go('app.bookings.list');
    };

    $scope.cancel = function(){
      $scope.bookings = Booking.query();
      $state.go('app.bookings.list');
    }

  }]);
