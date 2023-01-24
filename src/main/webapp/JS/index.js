alert("JS is running");

/*// Create the script tag, set the appropriate attributes
var script = document.createElement('script');
script.src = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyDVbO9qu-JXbMHKL6jULNdrP1r3o8L0Q4g&callback=initMap&v=weekly';
script.async = true;*/

// Append the 'script' element to 'head'
//document.head.appendChild(script);

let map;

function initMap() {
  myLatLang = getLatLng();
  map = new google.maps.Map(document.getElementById("map"), {
    center: { lat: -34.397, lng: 150.644 },
    zoom: 8,
  });
  
  new google.maps.Marker({
	  position: myLatLang,
	  map,
	  title: "Hello!"
  });
}

window.initMap = initMap;

//getting data from server
var request;
var results;

//when server sends response, the html changes
//use callback to use the JSON object 
function getServerResponse(){ 
	if (request.readyState == 4) {  
		results = JSON.parse(request.responseText);
	}
}

function getAgencyLocation(address) {
	address = encodeURI(address);
	alert("calling showRoute");
	var v = document.vinform.t1.value;  
	var url = "Geocoding?address=" + address;
	
	if (window.XMLHttpRequest) {  
		request = new XMLHttpRequest();  
	}  
	else if (window.ActiveXObject) {  
		request = new ActiveXObject("Microsoft.XMLHTTP");  
	}  
	  
	try  
	{  
		request.onreadystatechange  = getServerResponse;  
		request.open("GET", url, true);  
		request.send();
	}  
	catch(e)  
	{  
		alert("Unable to connect to server");  
	}  
}

function getLatLng() {
	locationObject = results.results[0];
	
	let resultJSON = {};
	resultJSON.lat = locationObject.location.lat;
	resultJSON.lng = locationObject.location.lng;
	
	return resultJSON;
}


