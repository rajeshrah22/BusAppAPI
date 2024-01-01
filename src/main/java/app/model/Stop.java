package app.model;

import java.io.Serializable;

public class Stop implements Serializable{
	private static final long serialVersionUID = 5481371484397082633L;
	
	private int lat;
	private int lng;
	private String tag;
	private String title;
	private String stopID;
	
	public Stop(int lat, int lng, String tag, String title, String stopID) {
		this.lat = lat;
		this.lng = lng;
		this.tag = tag;
		this.title = title;
		this.stopID = stopID;
	}
	
	//getters and setters
	public int getLat() {
		return lat;
	}

	public void setLat(int lat) {
		this.lat = lat;
	}

	public int getLng() {
		return lng;
	}

	public void setLng(int lng) {
		this.lng = lng;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStopID() {
		return stopID;
	}

	public void setStopID(String stopID) {
		this.stopID = stopID;
	}

}
