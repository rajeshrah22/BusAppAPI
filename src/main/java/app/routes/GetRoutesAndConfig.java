package app.routes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehcache.Cache;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import app.model.Route;
import app.service.CacheManagerService;
import app.service.XmlApiService;

/**
 * Servlet implementation class GetRoutesAndConfig
 */
@WebServlet("/GetRoutesAndConfig")
@SuppressWarnings("unchecked")
public class GetRoutesAndConfig extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static XmlApiService xmlApiService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRoutesAndConfig() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	xmlApiService = new XmlApiService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("GetRoutesAndConfig called");
    	ServletContext context = getServletContext();
    	CacheManagerService cacheManagerService = (CacheManagerService) context.getAttribute("cacheManagerService");
    	Cache<String, JSONObject> routeConfigCache = cacheManagerService.getRouteConfigCache();
    	PrintWriter out = response.getWriter();
    	
    	ArrayList<Route> routeList = (ArrayList<Route>) request.getAttribute("routeList");
    	String agencyTag = (String) request.getAttribute("agencyTag");
    	
    	JSONObject resultJSON = new JSONObject();
    	JSONArray resultArray = new JSONArray();
    	
    	for (Route route: routeList) {
    		String cacheKey = agencyTag+"_"+route.getTag();
    		
    		if(routeConfigCache.containsKey(cacheKey)) {
    			resultArray.add(routeConfigCache.get(cacheKey));
    			continue;
    		}
    		
    		JSONObject routeConfig = xmlApiService.getRouteConfig(agencyTag, route.getTag());
    		routeConfigCache.put(cacheKey, routeConfig);
    		
    		resultArray.add(routeConfig);
    	}
    	
    	resultJSON.put("results", resultArray);
    	out.print(resultJSON.toJSONString());
    }
}
