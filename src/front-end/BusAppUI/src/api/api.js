import axios from 'axios'

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