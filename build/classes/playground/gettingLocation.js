//code to get locaiton information from browser

function getLocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(showPos, showError);
  }
  else {
    console.log("Geolocation is not supported by this browser");
  }
}

function showError(error) {
  switch(error.code) {
    case error.PERMISSION_DENIED:
      //action for permission denied error
      break;
    case error.POSITION_UNAVAILABLE:
      //action for position unavailable error error
      break;
    case error.TIMEOUT:
      //action for request timeout error
      break;
    case error.UNKNOWN_ERROR:
      //action for unknown error
      break;
  }
}

function showPos(position) {
  //complete action required with position object
}