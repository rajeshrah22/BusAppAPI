package test;

import java.io.Serializable;

public class Route implements Serializable {
	private String title;
	private String shortTitle;
	private String tag;
	
	public Route(String title, String shortTitle, String tag) {
		this.title = title;
		this.shortTitle = title;
		this.tag = tag;
	}

	//getters and setters
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
}
