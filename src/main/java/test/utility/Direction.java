package test.utility;

import java.io.Serializable;
import java.util.ArrayList;

public class Direction implements Serializable{
	private static final long serialVersionUID = 5270566107494050439L;
	
	private String tag;
	private String title;
	private ArrayList<Stop> stopList = new ArrayList<>();
	
	public Direction(String tag, String title) {
		this.tag = tag;
		this.title = title;
	}

	//getters and setters
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

	public ArrayList<Stop> getStopList() {
		return stopList;
	}

	public boolean add(Stop e) {
		return stopList.add(e);
	}
	
	
	

}
