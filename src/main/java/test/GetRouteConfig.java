package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;

import test.utility.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.json.simple.*;

/**
 * Servlet implementation class GetRouteConfig
 */
@WebServlet("/GetRouteConfig")
public class GetRouteConfig extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**	
     * @see HttpServlet#HttpServlet()
     */
    public GetRouteConfig() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String agencyTag = request.getParameter("agencyTag");
		String routeTag = request.getParameter("routeTag");
		
		try {
			URL url = new URL("https://retro.umoiq.com/service/publicXMLFeed?command=routeConfig&a=" + agencyTag + "&r=" + routeTag);
			InputStream stream = url.openStream();
			
			//StaxParser Setup
			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
			XMLEventReader reader = xmlInputFactory.createXMLEventReader(stream);
			
			JSONObject resultJSON = new JSONObject();
			JSONObject routeObj = new JSONObject();
			JSONArray stopArray = new JSONArray();
			JSONArray directionArray = new JSONArray();
			JSONArray pathArray = new JSONArray();
			
			while(reader.hasNext()) {
				XMLEvent nextEvent = reader.nextEvent();
				if (nextEvent.isStartElement()) {
					StartElement startElm = nextEvent.asStartElement();
					//if element is not a route, then skip
					if (startElm.getName().getLocalPart().equals("route")) {
						String title = startElm.getAttributeByName(new QName("title")).getValue();
						String color = startElm.getAttributeByName(new QName("color")).getValue();
						String oppositeColor = startElm.getAttributeByName(new QName("oppositeColor")).getValue();
						String latMin = startElm.getAttributeByName(new QName("latMin")).getValue();
						String latMax = startElm.getAttributeByName(new QName("latMax")).getValue();
						String lngMin = startElm.getAttributeByName(new QName("lonMin")).getValue();
						String lngMax = startElm.getAttributeByName(new QName("lonMax")).getValue();
						
						routeObj.put("routeTag", routeTag);
						routeObj.put("title", title);
						routeObj.put("color", color);
						routeObj.put("oppositeColor", oppositeColor);
						routeObj.put("latMin", latMin);
						routeObj.put("latMax", latMax);
						routeObj.put("lngMin", lngMin);
						routeObj.put("lngMax", lngMax);
					}
					
					if (startElm.getName().getLocalPart().equals("stop")) {
						String title = startElm.getAttributeByName(new QName("title")).getValue();
						String tag = startElm.getAttributeByName(new QName("tag")).getValue();
						String lat = startElm.getAttributeByName(new QName("lat")).getValue();
						String lng = startElm.getAttributeByName(new QName("lon")).getValue();
						String stopID = startElm.getAttributeByName(new QName("stopId")).getValue();
						
						JSONObject stop = new JSONObject();
						stop.put("title", title);
						stop.put("tag", tag);
						stop.put("lat", lat);
						stop.put("lng", lng);
						stop.put("stopID", stopID);
						
						stopArray.add(stop);
					}
					
					if (startElm.getName().getLocalPart().equals("direction")) {
						String useForUI = startElm.getAttributeByName(new QName("title")).getValue();
						if(useForUI.equals("true")) {
							String title = startElm.getAttributeByName(new QName("title")).getValue();
							String tag = startElm.getAttributeByName(new QName("tag")).getValue();
						}
					}
					
					
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
