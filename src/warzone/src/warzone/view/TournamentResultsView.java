package warzone.view;

import warzone.model.TournamentContext;

/**
 * This class will print the result of tournament
 *
 */
public class TournamentResultsView {

	/**
	 * This method will print the result of tournament
	 * @param p_tournamentContext the tournament context
	 */
	public static void printTournamentResults(TournamentContext p_tournamentContext) {
		
		System.out.println("**************************************************************************");
        System.out.println("**                          Tournament Results                          **");
        System.out.println("**************************************************************************\n");
        
        System.out.println("M: " + p_tournamentContext.getMapFiles().toString());
        System.out.println("P: " + p_tournamentContext.getPlayerStrategies().toString());
        System.out.println("G: " + p_tournamentContext.getNumberOfGames());
        System.out.println("D: " + p_tournamentContext.getMaxTurns() + "\n");
        
        System.out.format("%15s", "");
        
        for(int i = 1; i <= p_tournamentContext.getMapFiles().size(); i++) {
        	
        	System.out.format("%15s", "Game " + i);
        }
        
        System.out.println();
        
        for(int i = 0; i < p_tournamentContext.getResults().length; i++) {
        	
        	System.out.format("%15s", p_tournamentContext.getMapFiles().get(i));
        	
        	for(int j = 0; j < p_tournamentContext.getResults()[0].length; j++) {
        	
        		System.out.format("%15s", p_tournamentContext.getResults()[i][j]);
        	}
        	
        	System.out.println();
        }
	}
}
