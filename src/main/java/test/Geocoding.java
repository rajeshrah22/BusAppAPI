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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;



/**
 * Servlet implementation class Geocoding
 */
@WebServlet("/Geocoding")
public class Geocoding extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final String API_KEY = "AIzaSyDVbO9qu-JXbMHKL6jULNdrP1r3o8L0Q4g";
    static final String API_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Geocoding() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		JSONObject resultJSON = new JSONObject();
		JSONArray resultArray = new JSONArray();
	    
		// go through Agency List
		for (Agency agency: XmlFetchingParsing.agencyList) {
			JSONObject agencyJSON = new JSONObject();
			
			JSONObject result = getGeocodingResult(agency.getRegion());
			JSONArray results = (JSONArray) result.get("results");
			JSONObject firstResult = (JSONObject) results.get(0);
			JSONObject geometry = (JSONObject) firstResult.get("geometry");
			JSONObject location = (JSONObject) firstResult.get("location");
			
			//stores the location
			agency.setLatLng(location.toJSONString());
			
			agencyJSON.put("tag", agency.getTag());
			agencyJSON.put("location", location);
			
			resultArray.add(agencyJSON);
		}
		
		resultJSON.put("results", resultArray);
		
		System.out.println(resultJSON.toJSONString());
		out.print(resultJSON.toJSONString());
	}
	
	private JSONObject getGeocodingResult (String address) throws MalformedURLException {
		URL url = new URL(API_URL + address + "&key=" + API_KEY);
        Scanner scan = null;
        
		try {
			scan = new Scanner(url.openStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        String jsonStr = "";
        while (scan.hasNext())
            jsonStr += scan.nextLine();
        scan.close();
        
        JSONParser parser = new JSONParser();
        JSONObject result = null;
        
        try {
			result = (JSONObject) parser.parse(jsonStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        return result;
	}
}
