var request;

//initial css
//hideAside();

function getAgencyLocations() {	
	var url = "/BusApp/GetAgencyList";
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
	var url = "/BusApp/GetRoutes?agencyTag=" + agencyTag;
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

function getRouteConfig(agencyTag, routeTag) {	
	var url = "/BusApp/GetRouteConfig?agencyTag=" + agencyTag + "&routeTag=" + routeTag;
	if (window.XMLHttpRequest) {  
		request = new XMLHttpRequest();  
	}  
	else if (window.ActiveXObject) {  
		request = new ActiveXObject("Microsoft.XMLHTTP");  
	}  
	  
	try  
	{  
		request.onreadystatechange  = getRouteConfigResponse;  
		request.open("GET", url, true);  
		request.send();
	}  
	catch(e)  
	{  
		alert("Unable to connect to server");  
	}
}

function getDirectionInfo(agencyTag, routeTag, directionTag) {	
	var url = "/BusApp/GetDirectionInfo?agencyTag=" + agencyTag + "&routeTag=" + routeTag + "&directionTag=" + directionTag;
	if (window.XMLHttpRequest) {  
		request = new XMLHttpRequest();  
	}  
	else if (window.ActiveXObject) {  
		request = new ActiveXObject("Microsoft.XMLHTTP");  
	}  
	  
	try  
	{  
		request.onreadystatechange  = getDirectionInfoResponse;  
		request.open("GET", url, true);  
		request.send();
	}  
	catch(e)  
	{  
		alert("Unable to connect to server");  
	}
}

getAgencyLocations();

//getting data from server
var geocodingResultsJSON;
var resultArray;

//when server sends response, the html changes
//use callback to use the JSON object 
function getAgencyResponse() {
	if (request.readyState == 4) {  
		geocodingResultsJSON = JSON.parse(request.responseText);
		
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

function getRouteConfigResponse() {
	if (request.readyState == 4) {  
		let responseJSON = JSON.parse(request.responseText);
		
		document.getElementById("routes").innerHTML = responseJSON.htmlText;
	}
}

function getDirectionInfoResponse() {
	if (request.readyState == 4) {
		let responseJSON = JSON.parse(request.responseText);
		
		plotDirection(responseJSON.directionStops, responseJSON.stopList, responseJSON.pathArray, responseJSON.color);
	}
}