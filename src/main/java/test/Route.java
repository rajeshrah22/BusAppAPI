package test;

import java.io.Serializable;

public class Route implements Serializable {
	private String title;
	private String tag;
	
	public Route(String title, String tag) {
		this.title = title;
		this.tag = tag;
	}

	//getters and setters
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
