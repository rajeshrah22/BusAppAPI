package app.model;

import java.io.Serializable;

public class Route implements Serializable {
	private static final long serialVersionUID = -1062071274498740524L;
	
	private String title;
	private String tag;
	private String color;
	private String oppositeColor;
	private int latMin;
	private int latMax;
	private int lngMin;
	private int lngMax;
	
	public Route(String title, String tag, String color, String oppositeColor, int ltM, int ltMx, int lngM, int lngMx) {
		this.title = title;
		this.tag = tag;
		this.color = color;
		this.oppositeColor = oppositeColor;
		this.latMin = ltM;
		this.latMax = ltMx;
		this.lngMin = lngM;
		this.lngMax = lngMx;
	}

	//getters and setters
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getOppositeColor() {
		return oppositeColor;
	}

	public void setOppositeColor(String oppositeColor) {
		this.oppositeColor = oppositeColor;
	}
	
	public int getLatMin() {
		return latMin;
	}

	public void setLatMin(int latMin) {
		this.latMin = latMin;
	}

	public int getLatMax() {
		return latMax;
	}

	public void setLatMax(int latMax) {
		this.latMax = latMax;
	}

	public int getLngMin() {
		return lngMin;
	}

	public void setLngMin(int lngMin) {
		this.lngMin = lngMin;
	}

	public int getLngMax() {
		return lngMax;
	}

	public void setLngMax(int lngMax) {
		this.lngMax = lngMax;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String toString() {
		return "tag: " + tag + ", title: " + title;
	}
	
	
}
