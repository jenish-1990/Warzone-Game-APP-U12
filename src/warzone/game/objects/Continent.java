package warzone.game.objects;

//Not sure if it makes sense to have a continent class or not. We will need to discuss
public class Continent {
	
	private int continentID;
	private String continentName;
	
	public int getContinentID() {
		return continentID;
	}
	
	public void setContinentID(int continentID) {
		this.continentID = continentID;
	}
	
	public String getContinentName() {
		return continentName;
	}
	
	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}	
}
