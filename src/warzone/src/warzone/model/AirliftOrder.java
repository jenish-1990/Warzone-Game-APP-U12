package warzone.model;

import warzone.view.GenericView;

public class AirliftOrder implements Order{

    private int d_sourceCountryId;
    private int d_targetCountryId;
    private Country d_sourceCountry;
    private Country d_targetCountry;
    private int d_armyNumber;
    private Player d_player;

    /**
     * constructor of airlift order
     * @param p_sourceCountryId source country
     * @param p_tragetCountryId target country
     * @param p_armyNumber army number
     */
    public AirliftOrder(int p_sourceCountryId, int p_tragetCountryId, int p_armyNumber){
        d_sourceCountryId = p_sourceCountryId;
        d_targetCountryId = p_tragetCountryId;
        d_armyNumber = p_armyNumber;
    }

    /**
     * set the player of the order
     * @param p_player the player
     */
    public void setPlayer(Player p_player){
        d_player = p_player;
    }

    /**
     * get the player of the order
     * @return the player
     */
    public Player getPlayer() {
        return  d_player;
    }

    /**
     *  execute airlift order.
     */
    @Override
    public void execute(){
        if(!valid()) return;
        int l_armyInTarget = d_targetCountry.getArmyNumber() + d_armyNumber;
        int l_armyInSource = d_sourceCountry.getArmyNumber() - d_armyNumber;
        d_targetCountry.setArmyNumber(l_armyInTarget);
        d_sourceCountry.setArmyNumber(l_armyInSource);
        GenericView.printSuccess("Success to airlift army.");
        printOrder();
    }

    /**
     * check if the order can be executed
     * @return true if valid
     */
    @Override
    public boolean valid(){
        //check if the player has a airlift card
        if(!d_player.getCards().contains(Card.AIRLIFT)){
            GenericView.printError("Player " + d_player.getName() + " does not have a airlift card");
            return false;
        }
        //check if countries belongs to the player
        if(!d_player.getConqueredCountries().containsKey(d_sourceCountryId) || !d_player.getConqueredCountries().containsKey(d_targetCountryId)){
            GenericView.printError("Source country or target country does not belongs to the player.");
            return false;
        }
        //get the countries
        d_sourceCountry = d_player.getConqueredCountries().get(d_sourceCountryId);
        d_targetCountry = d_player.getConqueredCountries().get(d_targetCountryId);
        //check if army number is more than 0
        if(d_armyNumber <= 0){
            GenericView.printError("The number of airlift army shoud more than 0.");
            return false;
        }
        //check if army number is more that they own
        if(d_sourceCountry.getArmyNumber() < d_armyNumber) {
            GenericView.printError("Player does not have enough army in country "+ d_sourceCountry.getCountryName());
            return false;
        }
        return true;
    }

    /**
     * print the order
     */
    @Override
    public void printOrder(){
        GenericView.println("Airlift order issued by player " + this.d_player.getName());
        GenericView.println("Airlift " + this.d_armyNumber + " from country " + d_sourceCountry.getCountryName() + " to " + this.d_targetCountry.getCountryName());
    }
}
