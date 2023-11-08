package warzone.model;

import java.util.ArrayList;
import java.util.List;

public class Continent implements IRender {
	
	private int d_continentID;
	private String d_continentName;
	private String d_color;
	private List<Country> d_countries;
	
	public Continent(int p_ContinentID, String p_ContinentName) {
		d_continentID = p_ContinentID;
		d_continentName = p_ContinentName;
	}
	
	public int getContinentID() {
		return d_continentID;
	}
	
	public void setContinentID(int p_ContinentID) {
		this.d_continentID = p_ContinentID;
	}
	
	public String getContinentName() {
		return d_continentName;
	}
	
	public void setContinentName(String p_ContinentName) {
		this.d_continentName = p_ContinentName;
	}	
	
	@Override
	public void render() {
		// todo: 
		//e.g.  System.out.println("ID" + getContinentID());
	}
}
