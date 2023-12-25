var request;

//remembers pages visited, used to implement back feature
var pages = [];

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
		
		//display agencies on the side
		document.getElementById("list").innerHTML = displayAgencies(resultArray);
		
		//plots agencies on map
		plotAgencies(resultArray);
	}	
}

function displayAgencies(agencies) {
	let htmlString = "<h3>To view bus agency: click on markers on the map or select from table below</h3><table><tr><td>Agency Tag</td><td>Agency Region</td></tr>"
	for (let agency of agencies) {
		htmlString += `<tr><td>${agency.tag}</td><td>${agency.regionTitle}</td><td><input type=\"button\" value=\"Show routes\" onClick=\"getRoutes('${agency.tag}')\"></td></tr>"`;
	}
	
	htmlString += "</table>"

	pages.push({
		htmlString: htmlString,
		type: "agencies"
	});
		
	return htmlString;
}

function getRoutesResponse() {
	if (request.readyState == 4) {  
		document.getElementById("list").innerHTML = request.responseText;
		
		pages.push({
			htmlString: request.responseText,
			type: "routes"
		});
	}	
}

function getRouteConfigResponse() {
	if (request.readyState == 4) {  
		let responseJSON = JSON.parse(request.responseText);
		
		document.getElementById("list").innerHTML = responseJSON.htmlText;
		
		pages.push({
			htmlString: responseJSON.htmlText,
			type: "routeConfigs"
		});
	}
}

function getDirectionInfoResponse() {
	if (request.readyState == 4) {
		let responseJSON = JSON.parse(request.responseText);
		
		plotDirection(responseJSON.directionStops, responseJSON.stopList, responseJSON.pathArray, responseJSON.color);
	}
}

function navigateBack() {
	if (pages.length == 1) return;
	
	pages.pop();
	
	let newPage = pages.at(pages.length - 1);
	map.setZoom(2);
	map.setCenter({ lat: 25, lng: 0 });
	
	document.getElementById("list").innerHTML = newPage.htmlString;
}