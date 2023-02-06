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
		request.onreadystatechange  = getAgencyResponse;  
		request.open("GET", url, true);  
		request.send();
	}  
	catch(e)  
	{  
		alert("Unable to connect to server");  
	}  
}

function getRoutes(agencyTag) {
	alert("calling getRouteLocations");
	
	var url = "/BusApp/displayRoutes?agencyTag=" + agencyTag;
	console.log("url: " + url);
	if (window.XMLHttpRequest) {  
		request = new XMLHttpRequest();  
	}  
	else if (window.ActiveXObject) {  
		request = new ActiveXObject("Microsoft.XMLHTTP");  
	}  
	  
	try  
	{  
		request.onreadystatechange  = getRoutesResponse;  
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

let map;

function initMap() {
  map = new google.maps.Map(document.getElementById("map"), {
    center: { lat: 0, lng: 0 },
    zoom: 2,
  });
}

window.initMap = initMap;

//getting data from server
var geocodingResultsJSON;
let markerArray = [];

//when server sends response, the html changes
//use callback to use the JSON object 
function getAgencyResponse() {	 
	if (request.readyState == 4) {  
		geocodingResultsJSON = JSON.parse(request.responseText);
		console.log(geocodingResultsJSON);
		
		let resultArray = geocodingResultsJSON.results;
		
		for(let agency of resultArray) {
			let marker = new google.maps.Marker({
				position: agency.location,
				map,
				optimized: true,
				title: agency.tag
			});
			
			marker.setMap(map);
			
			//if clicked on
			marker.addListener("click", () => {
				getRoutes(agency.tag);
			});
			
			markerArray.push(marker);
		}
		
		/*map.setCenter();
		marker.setPosition();
		marker.setMap();*/
	}	
}

function getRoutesResponse() {	 
	if (request.readyState == 4) {  
		document.getElementById("routes").innerHTML = request.responseText;
	}	
}
