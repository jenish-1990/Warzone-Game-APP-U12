package warzone.model;

import warzone.view.GenericView;

public class BlockadeOrder extends Order {
	
	/** target country id */
    private int d_targetCountryId;
    
    /** target country */
    private Country d_targetCountry;
    
    /** current player */
    private Player d_player;
    
    /**
     * constructor of blockade order
     * @param p_player current player
     * @param p_targetCountryId target country id
     */
    public BlockadeOrder(Player p_player,int p_targetCountryId) {
        d_targetCountryId = p_targetCountryId;
        d_player=p_player;
    }

    /**
     *  execute blockade order.
     */
	@Override
	public void execute() {
		if(!this.valid())	return;
		//triple the number of armies on one of the current player's territories
		d_targetCountry.setArmyNumber(d_targetCountry.getArmyNumber()*3);
		//remove target country from conquered countries
		d_targetCountry.getOwner().getConqueredCountries().remove(d_targetCountryId);
		//set owner to null
		d_targetCountry.setOwner(null);
	}

    /**
     * check if the order can be executed
     * @return true if valid
     */
	@Override
	public boolean valid() {
		if(!d_player.getIsAlive()) {
			GenericView.printError("Player "+d_player.getName()+" is dead!");
			return false;
		}
		d_targetCountry=d_player.getConqueredCountries().get(d_targetCountryId);
		if(d_targetCountry!=null) {
			return true;
		}else {
			GenericView.printError("Blockade order invalid:target country not belong to current player!");
			return false;
		}
	}

    /**
     * print the order
     */
	@Override
	public void printOrder() {
		GenericView.println("Blockade order issued by player " + this.d_player.getName());
		GenericView.println("Blockade " + this.d_targetCountry.getCountryName());
	}

}
