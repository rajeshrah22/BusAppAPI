package app.routes;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.json.simple.JSONObject;

import app.model.*;
import app.service.CacheManagerService;
import app.service.XmlApiService;

/**
 * Servlet implementation class displayRoutes
 */
@WebServlet("/GetRoutes")
public class GetRoutes extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private XmlApiService xmlApiService;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		xmlApiService = new XmlApiService();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRoutes() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		
		CacheManagerService cacheManagerService = (CacheManagerService) context.getAttribute("cacheManagerService");
		
		System.out.println("displayRoutes called");
		String agencyTag = request.getParameter("agencyTag");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/GetRoutesAndConfig");
		Cache<String, ArrayList> routeCache = cacheManagerService.getRoutecache();
		
		ArrayList<Route> routeList;
		
		if (routeCache.containsKey(agencyTag)) {
			routeList = routeCache.get(agencyTag);
		} else {
			routeList = xmlApiService.getRoutes(agencyTag);
		}
		
		request.setAttribute("routeList", routeList);
		request.setAttribute("agencyTag", agencyTag);
		dispatcher.forward(request, response);
		
		PrintWriter out = response.getWriter();
		out.print(routeList);
	}
}
