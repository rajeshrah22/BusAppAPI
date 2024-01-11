package app.routes;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehcache.Cache;
import org.json.simple.JSONObject;

import app.service.CacheManagerService;
import app.service.XmlApiService;

/**
 * Servlet implementation class GetRouteConfig
 * 
 * Gets routeConfig info from NextBus API with routeConfig command,
 * parses through its xml response.
 * 
 * Sends list of directions to client side
 * Adds list of stops, list of path items and points are cached.
 * this cache will be accessed from GetStops.java, GetPredictions.java, getPathInfo.java
 * 
 * cache info:
 * 		key: String agencyTag+"_"+routeTag
 * 		value: JSONObject
 * 
 * JSONObject format:
 * 	
 */

/*
 * result JSONObject format:
	{
	  	route: {
	  		title: String,
	  		color: String,
	  		oppositeColor: String,
	  		latMin: number,
	  		latMax: number,
	  		lngMin: number,
	  		lngMax: number
	  	},
	  	stopList: [{
	  		title: String,
	  		tag: String,
	  		lat: number,
	  		lng: number
	  	}, ... ],
	  	directionArray: [{
	  		title: String,
	  		tag: String,
	  		stopList: [{
	  			tag: String
	  		}, ... ]
	  	}, ... ],
	 	pathArray: [{
	 		pathID: String,
	 		pointArray: [{
	 			lat: number,
	 			lng: number
	 		}, ... ]
	 	}, ... ],
	  	htmlText: String
  	}
 
*/

@WebServlet("/GetRouteConfig")
public class GetRouteConfig extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static XmlApiService xmlApiService;
       
    public GetRouteConfig() {
        super();
    }
    
    public void init(ServletConfig config) throws ServletException{
    	super.init(config);
    	xmlApiService = new XmlApiService();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext application = getServletContext();
		CacheManagerService cacheManagerService = (CacheManagerService) application.getAttribute("cacheManagerService");
		
		System.out.println("routeConfigServletCalled");
		
		PrintWriter out = response.getWriter();
		String agencyTag = request.getParameter("agencyTag");
		String routeTag = request.getParameter("routeTag");
		
		String cacheKey = agencyTag+"_"+routeTag;
		
		//key values in the cache are stored in the format: [AGENCY TAG]_[ROUTE TAG].
		//in words, the keys are consisted of the string agencyTag concatenated to "_" and to the string routeTag
		Cache<String, JSONObject> routeConfigCache = cacheManagerService.getRouteConfigCache();
		
		if (routeConfigCache.containsKey(cacheKey)){
			out.println(routeConfigCache.get(cacheKey).toJSONString());
			return;
		}
				
		JSONObject storageJSON = xmlApiService.getRouteConfig(agencyTag, routeTag);
		
		System.out.println(storageJSON.toJSONString());
			
		routeConfigCache.put(cacheKey, storageJSON);
	
		out.println(storageJSON.toJSONString());
	}

}
