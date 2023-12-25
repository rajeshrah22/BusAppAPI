package test;

import java.io.PrintWriter;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.spi.loaderwriter.CacheLoadingException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import test.utility.Agency;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;



/**
 * Servlet implementation class Geocoding
 * 
 * Agency List is recieved from GetAgencyList.java through a request forwarding.
 * 
 * build cacheManager with geocoded results list called "gecodedList" 
 *key: String agencyTag, value: String JSONString result
 */
@WebServlet("/Geocoding")
public class Geocoding extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String API_KEY = "AIzaSyDVbO9qu-JXbMHKL6jULNdrP1r3o8L0Q4g";
    private static final String API_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    private static CacheManager cacheManager = null;
    private static Cache<String, JSONObject> geocodedList = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Geocoding() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		cacheManager = (CacheManager) session.getAttribute("cacheManager");
		
		//gets cache if exists
		geocodedList = cacheManager.getCache("geocodedList", String.class, JSONObject.class);
		
		//cache is created if it doesn't
		if (geocodedList == null) {
			geocodedList = cacheManager.createCache("geocodedList",
					CacheConfigurationBuilder
					.newCacheConfigurationBuilder(String.class, JSONObject.class, ResourcePoolsBuilder.heap(100)));
		}
		
//		System.out.println(">>> CAlling DoGET");
		
//		System.out.println("CacheSie: " + size);
		ArrayList<Agency> agencyList = (ArrayList<Agency>) request.getAttribute("agencyList");
//		System.out.println(agencyList);
		PrintWriter out = response.getWriter();
		
		JSONObject resultJSON = new JSONObject();
		JSONArray resultArray = new JSONArray();
	    
		// go through Agency List
		//if geocoded coordinates are not available in cache, call getGeocdingResult method
		//to get coordinate result.
		for (Agency agency: agencyList) {
			JSONObject agencyJSON = new JSONObject();
			
			JSONParser parser = new JSONParser();
			JSONObject result = null;
			
			try {
//				System.out.println("Line 87 AGENCY_TAG: " + agency.getTag());
//				System.out.println("line 89: " + geocodedList.get((String)agency.getTag()));
				if (geocodedList.containsKey(agency.getTag())) {
					result =(JSONObject) geocodedList.get(agency.getTag());
				} else {
					result = getGeocodingResult(agency.getRegion());
					geocodedList.put((String) agency.getTag(), result);
//					System.out.println("putting");
//					System.out.println("key: " + agency.getTag() + " value: " + geocodedList.get(agency.getTag()));
				}
			} catch (CacheLoadingException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			JSONArray results = (JSONArray) result.get("results");
			JSONObject firstResult = (JSONObject) results.get(0);
			JSONObject geometry = (JSONObject) firstResult.get("geometry");
			JSONObject location = (JSONObject) geometry.get("location");
			
			//stores the location
			agency.setLatLng(location.toJSONString());
			
			agencyJSON.put("tag", agency.getTag());
			agencyJSON.put("location", location);
			agencyJSON.put("regionTitle", agency.getRegion());
			
			resultArray.add(agencyJSON);
		}
		
		resultJSON.put("results", resultArray);
		
		out.print(resultJSON.toJSONString());
		System.out.println(resultJSON.toJSONString());
	}
	
	private JSONObject getGeocodingResult (String address) throws MalformedURLException {
		
		URL url = null;
		try {
			url = new URL(API_URL + URLEncoder.encode(address, "UTF-8") + "&key=" + API_KEY);
		} catch (MalformedURLException | UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
        Scanner scan = null;
        
		try {
			scan = new Scanner(url.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

        String jsonStr = "";
        while (scan.hasNext())
            jsonStr += scan.nextLine();
        scan.close();
        
        JSONParser parser = new JSONParser();
        JSONObject resultObject = null;
		try {
			resultObject = (JSONObject) parser.parse(jsonStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
           
        return resultObject; 
	}
	
}
