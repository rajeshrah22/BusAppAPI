//import { plotAgencies } from "./maps";

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

//getting data from server
var geocodingResultsJSON;
var resultArray;

//when server sends response, the html changes
//use callback to use the JSON object 
function getAgencyResponse() {	 
	if (request.readyState == 4) {  
		geocodingResultsJSON = JSON.parse(request.responseText);
		console.log(geocodingResultsJSON);
		
		resultArray = geocodingResultsJSON.results;
		
		//plots agencies on map
		plotAgencies(resultArray);
	}	
}

function getRoutesResponse() {	 
	if (request.readyState == 4) {  
		document.getElementById("routes").innerHTML = request.responseText;
	}	
}
