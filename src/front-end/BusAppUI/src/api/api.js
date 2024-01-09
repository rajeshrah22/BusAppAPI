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
  // const response = await axios.get(`${API_URL}/GetAgencyList`)

  return new Promise(resolve => {
    setTimeout(() => {
      resolve(agencies);
    }, 1000);
  });
}