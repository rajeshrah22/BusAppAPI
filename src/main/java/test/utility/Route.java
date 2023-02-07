package test.utility;

import java.io.Serializable;
import java.util.ArrayList;

public class Route implements Serializable {
	private static final long serialVersionUID = -1062071274498740524L;
	
	private String title;
	private String tag;
	private int latMin;
	private int latMax;
	private int lngMin;
	private int lngMax;
	private ArrayList<Direction> directionList = new ArrayList<>();
	
	public Route(String title, String tag, int ltM, int ltMx, int lngM, int lngMx) {
		this.title = title;
		this.tag = tag;
		this.latMin = ltM;
		this.latMax = ltMx;
		this.lngMin = lngM;
		this.lngMax = lngMx;
	}

	//getters and setters
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
	
	
}
