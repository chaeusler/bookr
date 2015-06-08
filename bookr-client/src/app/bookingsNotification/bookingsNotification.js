'use strict';

angular.module('bookr.bookingsNotification', ['ngWebsocket'])
  .run(function ($websocket) {

    var ws = new WebSocket('ws://localhost:8080/bookr/ws/bookingChange');

    ws.onmessage = function(event) {
      alert(event.data);
    }
  });
