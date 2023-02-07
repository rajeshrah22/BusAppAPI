package test.utility;
import java.io.Serializable;
import java.util.ArrayList;

public class Agency implements Serializable{
	private static final long serialVersionUID = 7408368444236537313L;
	
	private String tag;
	private String region;
	private String title;
	private String shortTitle;
	private String latLng;  //JSON String
	private ArrayList<Route> routeList = new ArrayList<Route>();
	
	//lattitude/longitude bounds
	private double north;
	private double south;
	private double west;
	private double east;
	
	//constructor
	public Agency(String tag, String region, String title, String shortTitle) {
		this.tag = tag;
		this.region = region;
		this.title = title;
		this.shortTitle = shortTitle;
	}

	//getters and setters
	public String getTag() {return tag;}
	public void setTag(String tag) {this.tag = tag;}
	public String getRegion() {return region;}
	public void setRegion(String region) {this.region = region;}
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	public String getShortTitle() {return shortTitle;}
	public void setShortTitle(String shortTitle) {this.shortTitle = shortTitle;}
	public double getNorth() {return north;}
	public void setNorth(double north) {this.north = north;}
	public double getSouth() {return south;}
	public void setSouth(double south) {this.south = south;}
	public double getWest() {return west;}
	public void setWest(double west) {this.west = west;}
	public double getEast() {return east;}
	public void setEast(double east) {this.east = east;}
	public String getLatLng() {return latLng;}
	public void setLatLng(String json) {this.latLng = json;}
	public ArrayList<Route> getRouteList() {return this.routeList;}

	public void addRoute(Route route) {
		routeList.add(route);
	}
	
	
	public String toString() {
		return "tag: " + tag;
	}
	
	
	
	

}
