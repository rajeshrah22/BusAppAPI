/*
Have to figure out how to send api requests with valid ur.
*/

const API_KEY = "AIzaSyCTlAqA4NEvCbatYdTX4ZVZdCCPPAAFiH4";

//sample numbers
const latitude = 42.272442;
const longitude = 71.613617;

 let request = new XMLHttpRequest();
 request.open("GET", `https://maps.googleapis.com/maps/api/geocode/json?latlng=${latitude}, ${longitude}&key=${API_KEY}`);
 request.onload = () => {
  console.log(request);
  if (request.status === 200) {
    console.log(JSON.parse(request.response));
  } else {
    console.log(`error ${request.status} ${request.statusText}`)
  }
 }