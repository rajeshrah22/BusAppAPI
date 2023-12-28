package app.routes;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.lang.StringBuilder;

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
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.json.simple.*;

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
public class GetRouteConfig extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static CacheManager cacheManager= null;

       
    public GetRouteConfig() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//gets session and its cache manager attribute
		HttpSession session = request.getSession();
		cacheManager = (CacheManager) session.getAttribute("cacheManager");
		
		System.out.println("routeConfigServletCalled");
		
		PrintWriter out = response.getWriter();
		String agencyTag = request.getParameter("agencyTag");
		String routeTag = request.getParameter("routeTag");
		
		String cacheKey = agencyTag+"_"+routeTag;
		
		//key values in the cache are stored in the format: [AGENCY TAG]_[ROUTE TAG].
		//in words, the keys are consisted of the string agencyTag concatenated to "_" and to the string routeTag
		Cache<String, JSONObject> routeCache = cacheManager.getCache("routeCache", String.class, JSONObject.class);
		
		//if cache doesn't exist, create cache.
		if (routeCache == null) {
			routeCache = cacheManager.createCache("routeCache", 
	    		    CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, JSONObject.class, ResourcePoolsBuilder.heap(30)));
		}
		
		if (routeCache.containsKey(cacheKey)){
			out.println(routeCache.get(cacheKey).toJSONString());
			return;
		}
		
		//System.out.println("did not pull from cache");
		
		StringBuilder htmlText = new StringBuilder();
		
		htmlText.append("<table>");  //start table
		htmlText.append("<TR>");
		htmlText.append("<TD>");
		htmlText.append("Direction");
		htmlText.append("</TD>");
		htmlText.append("<TD>");
		htmlText.append("Tag");
		htmlText.append("</TD>");
		htmlText.append("<TD>");
		htmlText.append("Show stops");
		htmlText.append("</TD>");
		htmlText.append("</TR>");
		
		try {
			URL url = new URL("https://retro.umoiq.com/service/publicXMLFeed?command=routeConfig&a=" + agencyTag + "&r=" + routeTag);
			InputStream stream = url.openStream();
			
			//StaxParser Setup
			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
			XMLEventReader reader = xmlInputFactory.createXMLEventReader(stream);
			
			JSONObject storageJSON = new JSONObject();
			JSONObject routeObj = new JSONObject();
			JSONArray stopArray = new JSONArray();
			JSONArray directionArray = new JSONArray();
			JSONArray pathArray = new JSONArray();
			
			while(reader.hasNext()) {
				XMLEvent nextEvent = reader.nextEvent();
				if (nextEvent.isStartElement()) {
					StartElement startElm = nextEvent.asStartElement();
					String startElementName = startElm.getName().getLocalPart();
					
					//System.out.println("Start Element: " + startElementName);
					//if element is not a route, then skip
					if (startElementName.equals("route")) {
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
						routeObj.put("latMin", Double.parseDouble(latMin));
						routeObj.put("latMax", Double.parseDouble(latMax));
						routeObj.put("lngMin", Double.parseDouble(lngMin));
						routeObj.put("lngMax", Double.parseDouble(lngMax));
					}
					
					if (startElm.getName().getLocalPart().equals("stop") && startElm.getAttributeByName(new QName("title")) != null) {
						String title = startElm.getAttributeByName(new QName("title")).getValue();
						String tag = startElm.getAttributeByName(new QName("tag")).getValue();
						String lat = startElm.getAttributeByName(new QName("lat")).getValue();
						String lng = startElm.getAttributeByName(new QName("lon")).getValue();
						Attribute stopID = startElm.getAttributeByName(new QName("stopId"));
						
						JSONObject stop = new JSONObject();
						
						//stop object key, values
						stop.put("title", title);
						stop.put("tag", tag);
						stop.put("lat", Double.parseDouble(lat));
						stop.put("lng", Double.parseDouble(lng));
						if (stopID != null) {
							stop.put("stopID", stopID.getValue());
						}
						
						
						stopArray.add(stop);
					}
					
					//reads in info for direction and puts it into an object. Puts its list of stops in an array.
					if (startElm.getName().getLocalPart().equals("direction")) {
						String useForUI = startElm.getAttributeByName(new QName("useForUI")).getValue();
						if(useForUI.equals("true")) {
							htmlText.append("<tr>");
							
							JSONObject direction = new JSONObject();
							JSONArray stopList = new JSONArray();
							
							String title = startElm.getAttributeByName(new QName("title")).getValue();
							String tag = startElm.getAttributeByName(new QName("tag")).getValue();
							
							System.out.println("test: " + tag);
							
							htmlText.append("<TD>" + title + "</TD> <TD>" + tag + "</TD>");
							htmlText.append("<TD><input type=\"button\" value=\"Show Stops\" onClick=\"getDirectionInfo('" + agencyTag + "', '" + routeTag + "', '" + tag + "')\"></TD>");
							
							htmlText.append("</tr>");
							//reads in the stops in the direction
							while(true) {
								XMLEvent element = reader.nextEvent();
								
								if (element.isEndElement()) {
									EndElement endElm = element.asEndElement();
									if (endElm.getName().getLocalPart().equals("direction")) {
										break;
									}
								}
								
								if (element.isStartElement()) {
									JSONObject stop = new JSONObject();
									
									StartElement elm = element.asStartElement();
									String tag1 = elm.getAttributeByName(new QName("tag")).getValue();
									
									stop.put("tag", tag1);
									stopList.add(stop);
								}
							}
							
							//direction object key, values
							direction.put("title", title);
							direction.put("tag", tag);
							direction.put("stopList", stopList);
							
							directionArray.add(direction);
						}
					}
					
					//read in path info when reader reaches path tag
					if (startElm.getName().getLocalPart().equals("path")) {
						JSONObject path = new JSONObject();
						JSONArray pointArray = new JSONArray();
						
						while(true) {
							XMLEvent element = reader.nextEvent();
							//System.out.println("INNER_LOOP");
							
							//inner loop breaks when it reaches end of current path
							if (element.isEndElement()) {
								EndElement endElm = element.asEndElement();
								if (endElm.getName().getLocalPart().equals("path")) {
									break;
								}
							}
							
							if (element.isStartElement()) {
								JSONObject point = new JSONObject();
							
								StartElement elm = element.asStartElement();
								//System.out.println("----- inner: " + elm.getName().getLocalPart());
								if (elm.getName().getLocalPart().equals("tag")) {
									String pathID = elm.getAttributeByName(new QName("id")).getValue();
									
									path.put("pathID", pathID);
								}
								
								if (elm.getName().getLocalPart().equals("point")) {
								
									//System.out.println("164: " + elm.getName().getLocalPart());
									String lat = elm.getAttributeByName(new QName("lat")).getValue();
									String lng = elm.getAttributeByName(new QName("lon")).getValue();
									
									point.put("lat", Double.parseDouble(lat));
									point.put("lng", Double.parseDouble(lng));
									
									pointArray.add(point);
								}
							}
						}
						
						path.put("pointArray", pointArray);
						
						pathArray.add(path);
					}
				}
			}
			
			htmlText.append("</table>");
			
			
			storageJSON.put("route", routeObj);
			storageJSON.put("stopList", stopArray);
			storageJSON.put("directionArray", directionArray);
			storageJSON.put("pathArray", pathArray);
			storageJSON.put("htmlText", htmlText.toString());
			
			routeCache.put(cacheKey, storageJSON);
			
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("htmlText", storageJSON.get("htmlText"));
			
			out.println(resultJSON.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
