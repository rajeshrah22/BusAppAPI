package app.routes;

import java.io.IOException;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

//local package(s)
import app.model.Agency;
import app.service.XmlApiService;
import app.service.CacheManagerService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.Cache;

@WebServlet("/GetAgencies")
public class GetAgencyList extends BaseServlet {
	private static final long serialVersionUID = 1L;
//	private static CacheManager cacheManager= null;
	private XmlApiService xmlApiService;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("Initializing Bus App");
		
		ServletContext context = getServletContext();
		
		CacheManagerService cacheManagerService = (CacheManagerService) context.getAttribute("cacheManagerService");
		
		//fix the type parameter error
		Cache<String, ArrayList>  agencyCache = cacheManagerService.getAgencyCache();
		ArrayList<Agency> agencyList = xmlApiService.getAgencyList();
		agencyCache.put("agencyList", agencyList);
	}
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAgencyList() {
        super();
        xmlApiService = new XmlApiService();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Geocoding");
		System.out.println("XML servlet was called");
		
		ServletContext context = getServletContext();
		CacheManagerService cacheManagerService = (CacheManagerService) context.getAttribute("cacheManagerService");
		
		@SuppressWarnings("unchecked")
		ArrayList<Agency> agencyList = cacheManagerService.getAgencyCache().get("agencyList");
		
		System.out.println(agencyList);
		request.setAttribute("agencyList", agencyList);
		dispatcher.forward(request, response);
	}
}
