let map;

function initMap() {
  map = new google.maps.Map(document.getElementById("map"), {
    center: { lat: 25, lng: 0 },
    zoom: 2,
  });
}

window.initMap = initMap;

let markerArrays = {
	"agencies": [],
	"stops": []
};

//plot agencies on map and add them to markerArray
function plotAgencies(agencyList) {
	for(let agency of agencyList) {
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
			
			markerArrays.agencies.push(marker);
	}
}

/**
 * plots the stops in the direction
 * plots the path of the route/direction in the color given
 */
function plotDirection(directionStops, stopList, pathArray, color) {		
	for (let dStop of directionStops) {
		let stop = stopList.find((element) => element.tag == dStop.tag);
		
		let location = {
			"lat": stop.lat,
			"lng": stop.lng
		};
						
		
		plotStop(location, stop.title);
	}
	
	let bounds = findBounds(stopList);
	
	for(let path of pathArray) {
		draw(path.pointArray, color);
	}
	
	map.fitBounds(bounds, 5);
}

const findBounds = (stopList) => {
	let bounds = {
		north: -90,  //initial max of lattitude
		south: 90,  //initial min of lattitude
		east: -180,   //initial max of longitude
		west: 180,   //initial min of longitude
	}
	
	for (let stop of stopList) {
		if (stop.lat > bounds.north) {
			bounds.north = stop.lat
		}
		if (stop.lat < bounds.south) {
			bounds.south = stop.lat
		}
		if (stop.lng > bounds.east) {
			bounds.east = stop.lng
		}
		if (stop.lng < bounds.west) {
			bounds.west = stop.lng
		}
	}
	
	return bounds;	
}

function plotStop(location, tag) {	
	let marker = new google.maps.Marker({
				position: location,
				map,
				optimized: true,
				title: tag
			});
			
	marker.setMap(map);
	
	markerArrays.stops.push(marker);
}

function draw(coordinateList, color) {
	const routePath = new google.maps.Polyline({
	    path: coordinateList,
	    geodesic: true,
	    strokeColor: color,
	    strokeOpacity: 1.0,
	    strokeWeight: 2,
	});

	routePath.setMap(map);
}

function haversine_distance(mk1, mk2) {
      var R = 3958.8; // Radius of the Earth in miles
      var rlat1 = mk1.lat * (Math.PI/180); // Convert degrees to radians
      var rlat2 = mk2.lat * (Math.PI/180); // Convert degrees to radians
      var difflat = rlat2-rlat1; // Radian difference (latitudes)
      var difflon = (mk2.lng-mk1.lng) * (Math.PI/180); // Radian difference (longitudes)

      var d = 2 * R * Math.asin(Math.sqrt(Math.sin(difflat/2)*Math.sin(difflat/2)+Math.cos(rlat1)*Math.cos(rlat2)*Math.sin(difflon/2)*Math.sin(difflon/2)));
      return d;
}

