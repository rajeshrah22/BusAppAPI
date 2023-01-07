/*
Have to figure out how to send api requests with valid ur.
*/

const API_KEY = "AIzaSyDVbO9qu-JXbMHKL6jULNdrP1r3o8L0Q4g";

      /* change these numbers from 0 to real lat long values */
      const latitude = 0;
      const longitude = 0;

      const URL = `https://maps.googleapis.com/maps/api/geocode/json?latlng=${latitude},${longitude}&key=${API_KEY}`;

      fetch(URL)
        .then((res) => {
          if (res.ok) {
            console.log("succesful");
            return res.json();
          } else {
            console.log("not succesfull");
            return undefined;
          }
        })
        .then(data => console.log(data))
        .catch(error => console.log(error));