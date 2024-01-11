package app.service;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import app.model.Agency;
import app.model.Route;

@SuppressWarnings("unchecked")
public class XmlApiService {

    private XMLInputFactory xmlInputFactory;

    public XmlApiService() {
        this.xmlInputFactory = XMLInputFactory.newInstance();
    }

    public ArrayList<Agency> getAgencyList() {
    	//fill agencyList with info from 
		ArrayList<Agency> agencyList = new ArrayList<>();
		try {
			//gets inputStream from URL
			URL url = new URL("https://retro.umoiq.com/service/publicXMLFeed?command=agencyList");
			InputStream stream = url.openStream();
			
			//StaxParser Setup
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
		
		return agencyList;
    }

    public ArrayList<Route> getRoutes(String agencyTag) {
    	ArrayList<Route> routeList = new ArrayList<>();
		
		System.out.println("agencyTag: " + agencyTag);
		try {
			//gets inputStream from URL
			URL url = new URL("https://retro.umoiq.com/service/publicXMLFeed?command=routeList&a=" + agencyTag);
			InputStream stream = url.openStream();
			
			//StaxParser Setup
			XMLEventReader reader = xmlInputFactory.createXMLEventReader(stream);
			
			//read XML from nextBus
			while (reader.hasNext()) {
				//System.out.println("nextEvent");
				
				XMLEvent nextEvent = reader.nextEvent();
				if (nextEvent.isStartElement()) {
					StartElement startElm = nextEvent.asStartElement();
					//if element is not an agency, then skip
					if (!startElm.getName().getLocalPart().equals("route")) {
						continue;
					}
					
					//get values from xml
					String routeTag = startElm.getAttributeByName(new QName("tag")).getValue();
					String title = startElm.getAttributeByName(new QName("title")).getValue();
					
					
					routeList.add(new Route(title, routeTag, null, null, 0,0,0,0));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return routeList;
    }
    
    public JSONObject getRouteConfig(String agencyTag, String routeTag) {
		JSONObject storageJSON = new JSONObject();
		try {
			URL url = new URL("https://retro.umoiq.com/service/publicXMLFeed?command=routeConfig&a=" + agencyTag + "&r=" + routeTag);
			InputStream stream = url.openStream();
			
			//StaxParser Setup
			XMLEventReader reader = xmlInputFactory.createXMLEventReader(stream);
			
			JSONObject routeObj = new JSONObject();
			JSONArray stopArray = new JSONArray();
			JSONArray directionArray = new JSONArray();
			JSONArray pathArray = new JSONArray();
			
			while(reader.hasNext()) {
				XMLEvent nextEvent = reader.nextEvent();
				if (nextEvent.isStartElement()) {
					StartElement startElm = nextEvent.asStartElement();
					String startElementName = startElm.getName().getLocalPart();
					
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
							JSONObject direction = new JSONObject();
							JSONArray stopList = new JSONArray();
							
							String title = startElm.getAttributeByName(new QName("title")).getValue();
							String tag = startElm.getAttributeByName(new QName("tag")).getValue();
							
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
			storageJSON.put("route", routeObj);
			storageJSON.put("stopList", stopArray);
			storageJSON.put("directionArray", directionArray);
			storageJSON.put("pathArray", pathArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return storageJSON;
    }
}