package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
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

/**
 * Servlet implementation class XmlFetchingParsing
 */
@WebServlet("/XmlFetchingParsing")
public class XmlFetchingParsing extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public XmlFetchingParsing() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Geocoding");
		System.out.println("XML servlet was called");
		ArrayList<Agency> agencyList = new ArrayList<>();
		
		PrintWriter out = response.getWriter();
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
		
		request.setAttribute("agencyList", agencyList);
		dispatcher.forward(request, response);
	}
}
