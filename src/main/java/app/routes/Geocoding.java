package app.routes;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehcache.Cache;
import org.ehcache.spi.loaderwriter.CacheLoadingException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import app.model.Agency;
import app.service.CacheManagerService;



/**
 * Servlet implementation class Geocoding
 * 
 * Agency List is recieved from GetAgencyList.java through a request forwarding.
 * 
 * build cacheManager with geocoded results list called "gecodedList" 
 *key: String agencyTag, value: String JSONString result
 */
@WebServlet("/Geocoding")
public class Geocoding extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static String API_KEY;
    private static String API_URL;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Geocoding() {
        super();
        System.out.println(API_URL);
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	
    	API_KEY = System.getenv("GOOGLE_API_KEY");
    	API_URL = System.getenv("GOOGLE_API_URL");
    }

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		ServletContext context = getServletContext();
		
		CacheManagerService cacheManager = (CacheManagerService) context.getAttribute("cacheManagerService");
		
		Cache<String,JSONObject> geocodedListCache  = cacheManager.getGeocodedListCache();
				
		ArrayList<Agency> agencyList = (ArrayList<Agency>) request.getAttribute("agencyList");
		PrintWriter out = response.getWriter();
		
		JSONObject resultJSON = new JSONObject();
		JSONArray resultArray = new JSONArray();
	    
		for (Agency agency: agencyList) {
			JSONObject agencyJSON = new JSONObject();
			
			JSONObject result = null;
			
			try {

				if (geocodedListCache.containsKey(agency.getTag())) {
					result =(JSONObject) geocodedListCache.get(agency.getTag());
				} else {
					result = getGeocodingResult(agency.getRegion());
					geocodedListCache.put((String) agency.getTag(), result);
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
