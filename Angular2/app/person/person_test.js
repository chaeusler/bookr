'use strict';

describe('myApp.person module', function() {

  beforeEach(module('myApp.person'));

  describe('person controller', function(){

    it('should ....', inject(function($controller) {
      //spec body
      var view1Ctrl = $controller('Person1Ctrl');
      expect(view1Ctrl).toBeDefined();
    }));

  });
});
