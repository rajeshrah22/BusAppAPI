package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class displayRoutes
 */
@WebServlet("/displayRoutes")
public class displayRoutes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public displayRoutes() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("displayRoutes called");
		ArrayList<Route> routeList = new ArrayList<>();
		String agencyTag = request.getParameter("agencyTag");
		
		PrintWriter out = response.getWriter();
		out.print("<table>");  //start table
		out.print("<TR>");
			out.print("<TD>");
			out.print("Route Title");
			out.print("</TD>");
			out.print("<TD>");
			out.print("Tag");
			out.print("</TD>");
			out.print("<TD>");
			out.print("Show Route Config");
			out.print("</TD>");
		out.print("</TR>");
		
		System.out.println("agencyTag: " + agencyTag);
		try {
			//gets inputStream from URL
			URL url = new URL("https://retro.umoiq.com/service/publicXMLFeed?command=routeList&a=" + agencyTag);
			InputStream stream = url.openStream();
			
			//StaxParser Setup
			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
			XMLEventReader reader = xmlInputFactory.createXMLEventReader(stream);
			
			//read XML from nextBus
			while (reader.hasNext()) {
				//System.out.println("nextEvent");
				
				XMLEvent nextEvent = reader.nextEvent();
				if (nextEvent.isStartElement()) {
					out.print("<TR>");
					StartElement startElm = nextEvent.asStartElement();
					//if element is not an agency, then skip
					if (!startElm.getName().getLocalPart().equals("route")) {
						continue;
					}
					
					//get values from xml
					String routeTag = startElm.getAttributeByName(new QName("tag")).getValue();
					String title = startElm.getAttributeByName(new QName("title")).getValue();
					
					System.out.println("route: " + title);
					routeList.add(new Route(title, routeTag));
					
					out.print("<TD>" + title + "</TD> <TD>" + routeTag + "</TD>");
					out.print("<TD><input type=\"button\" value=\"ShowRouteConfig\" onClick=\"showRouteConfig('" + agencyTag + "', '" + routeTag + "')\"></TD>");

					out.print("</TR>");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
