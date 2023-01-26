var request;

function getAgencyLocations() {
	alert("calling getAgencyLocations");
	
	var url = "/BusApp/XmlFetchingParsing";
	console.log(url);
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

console.log("JS is running");
getAgencyLocations();

/*// Create the script tag, set the appropriate attributes
var script = document.createElement('script');
script.src = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyDVbO9qu-JXbMHKL6jULNdrP1r3o8L0Q4g&callback=initMap&v=weekly';
script.async = true;*/

// Append the 'script' element to 'head'
//document.head.appendChild(script);

let map;
let marker;

function initMap() {
  map = new google.maps.Map(document.getElementById("map"), {
    center: { lat: -34.397, lng: 150.644 },
    zoom: 8,
  });
  
  marker = new google.maps.Marker({
	  map,
  });
}

window.initMap = initMap;

//getting data from server
var geocodingResultsJSON;
let markerArray = [];

//when server sends response, the html changes
//use callback to use the JSON object 
function getServerResponse() {	 
	if (request.readyState == 4) {  
		geocodingResultsJSON = JSON.parse(request.responseText);
		console.log(geocodingResultsJSON);
		
		let resultArray = geocodingResultsJSON.results;
		
		for(let agency of resultArray) {
			let markers = new google.maps.Marker({
				position: agency.location,
				map,
				optimized: true
			});
			
			markers.setMap(map);
			markerArray.push(marker);
		}
		
		/*map.setCenter();
		marker.setPosition();
		marker.setMap();*/
	}	
}
