package warzone.game.objects;

public class Country {
	
	private int countryID;
	private String countryName;
	//private int continentID; //Not sure if this makes sense or not. We will need to discuss.
	
	public int getCountryID() {
		return countryID;
	}
	
	public void setCountryID(int countryID) {
		this.countryID = countryID;
	}
	
	public String getCountryName() {
		return countryName;
	}
	
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}	
}
