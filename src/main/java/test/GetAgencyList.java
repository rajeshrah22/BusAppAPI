package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

//local package(s)
import test.utility.Agency;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
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

@WebServlet("/GetAgencyList")
public class GetAgencyList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static CacheManager cacheManager= null;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("Initializing Bus App");
		
		//build cacheManager
		cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
			    .withCache("nextBusCache",
			        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, ArrayList.class, ResourcePoolsBuilder.heap(10)))
			    .build();
		
		cacheManager.init();
		
		//fix the type parameter error
		Cache<String, ArrayList>  nextBusCache = cacheManager.getCache("nextBusCache", String.class, ArrayList.class);
		
		//fill agencyList with info from 
		ArrayList<Agency> agencyList = new ArrayList<>();
		try {
			//gets inputStream from URL
			URL url = new URL("https://retro.umoiq.com/service/publicXMLFeed?command=agencyList");
			InputStream stream = url.openStream();
			
			//StaxParser Setup
			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
			XMLEventReader reader = xmlInputFactory.createXMLEventReader(stream);
			
			//read XML from nextBus
			while (reader.hasNext()) {
				
				XMLEvent nextEvent = reader.nextEvent();
				if (nextEvent.isStartElement()) {
					StartElement startElm = nextEvent.asStartElement();
					//if element is not an agency, then skip
					if (!startElm.getName().getLocalPart().equals("agency")) {
						continue;
					}
					
					String agencyTag = startElm.getAttributeByName(new QName("tag")).getValue();
					String title = startElm.getAttributeByName(new QName("title")).getValue();
					String regionTitle = startElm.getAttributeByName(new QName("regionTitle")).getValue();
					String shortTitleText = "";
					
					Attribute shortTitle = startElm.getAttributeByName(new QName("shortTitle"));
					if (shortTitle != null) {
						shortTitleText = shortTitle.getValue();
					}
					
					Agency agency = new Agency(agencyTag, regionTitle, title, shortTitleText);
					agencyList.add(agency);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		nextBusCache.put("agencyList", agencyList);
	}
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAgencyList() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		session.setAttribute("cacheManager", cacheManager);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Geocoding");
		System.out.println("XML servlet was called");
		
		@SuppressWarnings("unchecked")
		ArrayList<Agency> agencyList = cacheManager.getCache("nextBusCache", String.class, ArrayList.class).get("agencyList");
		
		System.out.println(agencyList);
		request.setAttribute("agencyList", agencyList);
		dispatcher.forward(request, response);
	}
}
