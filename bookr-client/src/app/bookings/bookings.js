'use strict';

angular.module('bookr.bookings', ['uuid', 'bookr.base'])
  .config(function ($stateProvider) {
    $stateProvider
      .state('app.bookings', {
        url: '/',
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
      .state('app.bookings.create', {
        url: '/create',
        templateUrl: 'app/bookings/bookings.create.html',
        controller: 'BookingsCreateController'
      })
  })
  .controller('BookingsController', ['$scope', 'Booking', 'Person', 'Project', '$location', '$state', function ($scope, Booking, Person, Project, $location, $state) {
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

    $scope.newBooking = {};

    $scope.getPersonFor = function(booking){
      return $scope.personsMap[booking['person-id']];
    };

    $scope.getProjectFor = function(booking){
      return $scope.projectsMap[booking['project-id']];
    };

    $scope.formatDate = function(date){
      var d = new Date(date);
      return d.toLocaleString();
    };

    $scope.detail = function(booking){
      $scope.booking = booking;

      $location.path("/bookings/" + booking.id);
    };

    $scope.createBooking = function () {
      $state.go('app.bookings.create');
    };

  }])
  .controller('BookingsDetailsController', ['$scope', '$state', 'Booking', function($scope, $state, Booking) {

    $scope.update = function(){
      Booking.update($scope.booking);
      $state.go('app.bookings.list');
    };

    $scope.cancel = function(){
      $scope.bookings = Booking.query();
      $state.go('app.bookings.list');
    }

  }])
  .controller('BookingsCreateController', ['$scope', '$state', 'rfc4122', 'Booking', function($scope, $state, uuid, Booking) {
    $scope.newBooking = new Booking();

    $scope.day = new Date();
    $scope.fromTime = new Date();
    $scope.toTime = new Date();
    $scope.description = "";

    $scope.createBooking = function(){
      $scope.newBooking.id = uuid.v4();
      $scope.newBooking.startTime = angular.copy($scope.day);
      $scope.newBooking.startTime.setHours($scope.fromTime.getHours());
      $scope.newBooking.startTime.setMinutes($scope.fromTime.getMinutes());
      $scope.newBooking.endTime = angular.copy($scope.day);
      $scope.newBooking.endTime.setHours($scope.toTime.getHours());
      $scope.newBooking.endTime.setMinutes($scope.toTime.getMinutes());
      $scope.newBooking.description = $scope.description;
      $scope.newBooking.$save();
      $state.go('app.bookings.list');
    };

    $scope.cancel = function(){
      $scope.bookings = Booking.query();
      $state.go('app.bookings.list');
    }

  }]);
