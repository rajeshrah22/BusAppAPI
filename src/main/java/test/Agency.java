package test;

public class Agency {
	private String tag;
	private String region;
	private String title;
	private String shortTitle;
	
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
	
	
	
	

}
