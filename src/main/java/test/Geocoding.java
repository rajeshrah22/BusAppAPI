package test;

import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;



/**
 * Servlet implementation class Geocoding
 */
@WebServlet("/Geocoding")
public class Geocoding extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Geocoding() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		final String API_KEY = "AIzaSyDVbO9qu-JXbMHKL6jULNdrP1r3o8L0Q4g";
	    final String API_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
	    String address = "";
	    
	    try {
	    	address = URLEncoder.encode(request.getParameter("address"), "UTF-8");
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    
	    System.out.println("Address: " + address);
	    
	 // Make the API call and get the JSON response
        URL url = new URL(API_URL + address + "&key=" + API_KEY);
        Scanner scan = new Scanner(url.openStream());
        String jsonStr = "";
        while (scan.hasNext())
            jsonStr += scan.nextLine();
        scan.close();
        
        out.println(jsonStr);

        // Parse the JSON response
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        
		try {
			json = (JSONObject) parser.parse(jsonStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
        if (!json.get("status").equals("OK")) {
            System.out.println("Error: " + json.get("status"));
            return;
        }
        
        JSONArray results = (JSONArray) json.get("results");
        HashMap<String, String> addressMap = new HashMap<String, String>();
        for (Object result : results) {
            JSONObject resultJson = (JSONObject) result;
            String formattedAddress = (String) resultJson.get("formatted_address");
            JSONObject geometry = (JSONObject) resultJson.get("geometry");
            JSONObject location = (JSONObject) geometry.get("location");
            double lat = (Double) location.get("lat");
            double lng = (Double) location.get("lng");
            addressMap.put(formattedAddress, lat + ", " + lng);
        }
        System.out.println(addressMap);
	}

}
