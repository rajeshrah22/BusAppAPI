import axios from 'axios'

/*------------simulation data start ------- */
const agencies = [
  {
    "tag": "yessir",
    "regionTitle": "Westborough"
  },
  {
    "tag": "yeah",
    "regionTitle": "New York"
  },
  {
    "tag": "Malborough",
    "regionTitle": "Manchester"
  },
  {
    "tag": "yessir",
    "regionTitle": "Westborough"
  },
  {
    "tag": "yeah",
    "regionTitle": "New York"
  },
  {
    "tag": "Malborough",
    "regionTitle": "Manchester"
  },
  {
    "tag": "yessir",
    "regionTitle": "Westborough"
  },
  {
    "tag": "yeah",
    "regionTitle": "New York"
  },
  {
    "tag": "Malborough",
    "regionTitle": "Manchester"
  },
]

const routes = [
  {
    "title": "Purple Line",
    "tag": "fullservice",
    "color": "#800080",
    "directions": [
      {
        "title": "Inbound",
        "tag": "inbound",
      },
      {
        "title": "Outbound",
        "tag": "outbound",
      }
    ]
  },
  {
    "title": "Red Line",
    "tag": "express",
    "color": "#ff0000",
    "directions": [
      {
        "title": "Inbound",
        "tag": "inbound",
      },
      {
        "title": "Outbound",
        "tag": "outbound",
      }
    ]
  }
]

const agencyRoutes = {
  "*": routes,
}

/*------------simulation data end ------- */

const API_URL = 'http://localhost:8080/BusApp'

export const fetchAgencies = async () => {
  return axios
    .get(`${API_URL}/GetAgencyList`)
    .then(response => response.data.results)
    .catch(error => {
      console.error('Error fetching agencies:', error)
      throw error
    })
}

export const fetchRoutes = async (agencyTag) => {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      resolve(agencyRoutes[agencyTag] || agencyRoutes['*']);  //replace with agencyRoutes[agencyTag]
    }, 1000); //simulate delay
  });
}