package warzone.model;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 *	define of the RandomStrategy
 */
public class RandomStrategy extends PlayerStrategy {	
	
	/**
	 * constructor of RandomStrategy
	 * @param p_player given Player
	 */
	RandomStrategy(Player p_player){
		super(p_player); 
	}
	
	/**
	 *  a random number
	 */
	private static Random l_rand = new Random();

	/**
	 * Get a random country owned by the player
	 * @return random country owned by the player
	 */
	protected Country getRandomUnconqueredCountry() {
		int l_idx=l_rand.nextInt(GameContext.getGameContext().getCountries().size());
		Country l_randomCountry=(Country) GameContext.getGameContext().getCountries().values().toArray()[l_idx];
		while(l_randomCountry.getOwner().equals(d_player)){
			l_idx=l_rand.nextInt(GameContext.getGameContext().getCountries().size());
			l_randomCountry=(Country) GameContext.getGameContext().getCountries().values().toArray()[l_idx];
		}
		return l_randomCountry;
	}

	/**
	 * Get a random country owned by the player
	 * @return random country owned by the player
	 */
	protected Country getRandomConqueredCountry() {
		int l_idx=l_rand.nextInt(d_player.getConqueredCountries().size());
		Country l_randomCountry=(Country) d_player.getConqueredCountries().values().toArray()[l_idx];
		return l_randomCountry;
	}
	
	/**
	 * Choose a neighbor country for current country randomly
	 * @param p_currentCountry Current country
	 * @return neighbor country if exist else null
	 */
	protected Country getRandomNeighbor(Country p_currentCountry) {
		if(p_currentCountry.getNeighbors().size()==0)
			return null;
		int l_idx=l_rand.nextInt(p_currentCountry.getNeighbors().size());
		Country l_randNeighbor=(Country) p_currentCountry.getNeighbors().values().toArray()[l_idx];
		return l_randNeighbor;
	}

	/**
	 * get random player
	 * @return random player
	 */
	protected Player getRandomPlayer(){
		int l_idx=l_rand.nextInt(GameContext.getGameContext().getPlayers().size());
		Player l_player = (Player) GameContext.getGameContext().getPlayers().values().toArray()[l_idx];
		while(l_player.equals(d_player)){
			l_idx=l_rand.nextInt(GameContext.getGameContext().getPlayers().size());
			l_player = (Player) GameContext.getGameContext().getPlayers().values().toArray()[l_idx];
		}
		return l_player;
	}
	
	/**
	 *	Creates and order. 
	 *	The Random player can either deploy or advance, determined randomly. .
	 *	@return Order
	 */
	public Order createOrder() {
		Order l_order = null;		
		if(!d_player.getIsAlive())
			return null;

		int l_randomAction = l_rand.nextInt(3);

		switch(l_randomAction){
			case 1:
				l_order=new DeployOrder(d_player,getRandomConqueredCountry(),l_rand.nextInt(10));
				break;
			case 2:
				Country l_randomConqueredCountry=getRandomConqueredCountry();
				Country l_randomNeighbor=getRandomNeighbor(l_randomConqueredCountry);
				if(l_randomNeighbor!=null) {
					int l_num=l_rand.nextInt(l_randomConqueredCountry.getArmyNumber()+10);
					l_order=new AdvanceOrder(d_player,l_randomConqueredCountry,l_randomNeighbor,l_num);
				}
				break;
			case 3:
				if(d_player.getCards().size() <= 0)
					return null;
				int l_randomCardIdx = l_rand.nextInt(d_player.getCards().size());
				Card l_card = d_player.getCards().get(l_randomCardIdx);
				switch(l_card){
					case BLOCKADE:
						l_order = new BlockadeOrder(d_player, getRandomConqueredCountry());
						break;
					case BOMB:
						l_order = new BombOrder(d_player, getRandomUnconqueredCountry());
						break;
					case AIRLIFT:
						l_order = new AirliftOrder(d_player, getRandomConqueredCountry(), getRandomConqueredCountry(),l_rand.nextInt(10));
						break;
					case NEGOTIATE:
						l_order = new NegotiateOrder(d_player, getRandomPlayer());
						break;
				}
				break;
		}
		return l_order;
	}
}
