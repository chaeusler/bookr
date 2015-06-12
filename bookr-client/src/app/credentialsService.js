'use strict';

'use strict';

angular.module('Credentials', [])
  .factory('credentials', function() {
    var credentials = {
      name: '',
      password: ''
    };

    return credentials;
  });
