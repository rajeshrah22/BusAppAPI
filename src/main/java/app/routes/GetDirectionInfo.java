package app.routes;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ehcache.CacheManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.ehcache.Cache;

/**
 * Servlet implementation class GetDirectionInfo
 */

/*
resultJSON Format:
	{
		agencyTag: String,
		routeTag: String,
		directionTag: String,
		color: String,
		oppositeColor: String,
		stopList: [{
	  		title: String,
	  		tag: String,
	  		lat: number,
	  		lng: number
	  	}, ... ],
		directionStops: [{
  			tag: String
  		}, ... ],
		pathArray: [{
	 		pointArray: [{
	 			lat: number,
	 			lng: number
	 		}, ... ]
	 	}, ... ]
	}
*/
@WebServlet("/GetDirectionInfo")
public class GetDirectionInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static CacheManager cacheManager = null;
       
    public GetDirectionInfo() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//gets session and its cache manager attribute
		HttpSession session = request.getSession();
		cacheManager = (CacheManager) session.getAttribute("cacheManager");
		
		PrintWriter out = response.getWriter();
		String agencyTag = request.getParameter("agencyTag");
		String routeTag = request.getParameter("routeTag");
		String directionTag = request.getParameter("directionTag");
		
		String cacheKey = agencyTag+"_"+routeTag;
		
		Cache<String, JSONObject> routeCache = cacheManager.getCache("routeCache", String.class, JSONObject.class);
		
		JSONObject resultJSON = new JSONObject();
		
		JSONObject routeInfo = routeCache.get(cacheKey);
		JSONObject route = (JSONObject) routeInfo.get("route");
		String color = (String) route.get("color");
		String oppositeColor = (String) route.get("oppositeColor");
		JSONArray directionArray = (JSONArray) routeInfo.get("directionArray");
		JSONArray stopList = (JSONArray) routeInfo.get("stopList");
		JSONArray pathArray = (JSONArray) routeInfo.get("pathArray");
		JSONArray directionStops = null;
		
		//getCorrect stopList
		for (int i = 0; i < directionArray.size(); i++) {
			JSONObject direction = (JSONObject) directionArray.get(i);
			
			if (direction.get("tag").equals(directionTag)) {
				directionStops = (JSONArray) direction.get("stopList");
				break;
			}
		}
		
		resultJSON.put("agencyTag", agencyTag);
		resultJSON.put("routeTag", routeTag);
		resultJSON.put("directionTag", directionTag);
		resultJSON.put("color", color);
		resultJSON.put("oppositeColor", oppositeColor);
		resultJSON.put("stopList", stopList);
		resultJSON.put("directionStops", directionStops);
		resultJSON.put("pathArray", pathArray);
		
		
		out.println(resultJSON.toJSONString());
	}
}
