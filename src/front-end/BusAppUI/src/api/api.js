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

const API_URL = '/BusApp'

export const fetchAgencies = async () => {
  return axios
    .get(encodeURI(`${API_URL}/GetAgencies`))
    .then(response => response.data.results)
    .catch(error => {
      console.error('Error fetching agencies:', error)
      throw error
    })
}

export const fetchRoutes = async (agencyTag) => {
  return axios
    .get(encodeURI(`${API_URL}/GetRoutes?agencyTag=${agencyTag}`))
    .then(response => response.data.results)
    .catch(error => {
      console.error('Error fetching routes:', error)
      throw error
    })
}