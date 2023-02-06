let map;

function initMap() {
  map = new google.maps.Map(document.getElementById("map"), {
    center: { lat: 0, lng: 0 },
    zoom: 2,
  });
}

window.initMap = initMap;

let markerArray = [];

//plot agencies on map and add them to markerArray
export function plotAgencies(agencyList) {
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