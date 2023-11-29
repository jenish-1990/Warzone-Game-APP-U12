package warzone.model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 *	define of the BenevolentStrategy
 */
	public class BenevolentStrategy extends PlayerStrategy {

	/**
	 * Armies to deploy
	 */
	private int d_armiesToDeploy;

	/**
	 * constructor of BenevolentStrategy
	 * @param p_player given Player
	 */
	BenevolentStrategy(Player p_player){
		super(p_player);
		d_armiesToDeploy=p_player. getArmiesToDeploy();
	}
	
	/**
	 * Get the conquered country which has minimum army number
	 * @return the weakest conquered country
	 */
	protected Country getWeakestConqueredCountry() {
		Country l_weakestCountry=null;			
		int l_minArmyNum=Integer.MAX_VALUE;
		for(Country l_c:d_player.getConqueredCountries().values()) {
			if(l_c.getArmyNumber()<l_minArmyNum) {
				l_minArmyNum=l_c.getArmyNumber();
				l_weakestCountry=l_c;
			}
		}
		return l_weakestCountry;
	}
	
	/**
	 * implementation of createOrder
	 * @return Order
	 */
	public Order createOrder() {

		Order l_order=null;

		if(!d_player.getIsAlive())
			return null;

		//deploy to weakest country
		if(d_armiesToDeploy>0) {
			Country l_weakestCountry=getWeakestConqueredCountry();			
			l_order = new DeployOrder(d_player, l_weakestCountry, d_armiesToDeploy);
			d_armiesToDeploy-=d_armiesToDeploy;
			return l_order;
		}
		//move armies to reinforce its weaker country
		//sort by army number with ascending order
		List<Object> l_conqueredCountries=Arrays.asList(this.d_player.getConqueredCountries().values().toArray());
		Collections.sort(l_conqueredCountries,new Comparator<Object>() {
			@Override
			public int compare(Object l_obj1,Object l_obj2) {
				Country l_c1=(Country) l_obj1;
				Country l_c2=(Country) l_obj2;
				if (l_c1.getArmyNumber()>l_c2.getArmyNumber()) {
					return 1;
				}else if (l_c1.getArmyNumber()<l_c2.getArmyNumber()) {
					return -1;
				}else {
					return 0;
				}
			}
		});
		//move army to weaker country
		for(Object l_obj:l_conqueredCountries) {
			Country l_countryTo=(Country)l_obj;
			for(Country l_countryFrom:l_countryTo.getNeighbors().values()) {
				if(l_countryFrom.getArmyNumber()>l_countryTo.getArmyNumber() && l_countryFrom.getOwner() == l_countryTo.getOwner()) {
					int diff=l_countryFrom.getArmyNumber()-l_countryTo.getArmyNumber();
					if(diff>1) {
						l_order = new AdvanceOrder(this.d_player, l_countryFrom, l_countryTo , diff/2);
						return l_order;
					}
				}
			}
		}
		d_player.setHasFinisedIssueOrder(true);
		return null;
	}
}
