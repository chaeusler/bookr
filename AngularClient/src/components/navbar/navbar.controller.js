'use strict';

angular.module('bookr')
    .controller('NavbarCtrl', function ($scope) {
        $scope.pages = [
            {
                href: '#main',
                title: 'Home'
            },
            {
                href: '#persons',
                title: 'Persons'
            }];


        $scope.activeHref = '/#/main.html';

        $scope.date = new Date();
    });
