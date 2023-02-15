let map;

function initMap() {
  map = new google.maps.Map(document.getElementById("map"), {
    center: { lat: 25, lng: 0 },
    zoom: 2,
  });
}

window.initMap = initMap;

let markerArray = [];

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
			
			markerArray.push(marker);
		}
}

function plotDirection(directionStops, stopList, pathArray) {
	for (let dStop of directionStops) {
		let stop = stopList.find((element) => element.tag == dStop.tag);
		
		let location = {
			"lat": stop.lat,
			"lng": stop.lng
		};
		
		
	}
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

