/**
 * 
 */
package com.risk6441.models;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Map.Entry;

import com.risk6441.controller.PlayGameController;
import com.risk6441.controller.TournamentController;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;

import javafx.scene.control.TextArea;

/**
 * This class is a model for tournament which is called from
 * {@link TournamentController} TournamentContrller.
 * 
 * @author Deep
 *
 */
public class TournamentModel extends Observable implements Observer {

	
	HashMap<String, HashMap<String, String>> results = new HashMap<>();
	public TextArea txA;
	/**
	 * This method starts the tournament.
	 * @param playerList The playerlist for the tournament.
	 * @param map The map for the game
	 * @param numberOfTurn Number of turns before the draw.
	 * @param numberOfGames Number of games on the map.
	 * @param gameNumber The current game number.
	 * @param txtAreaConsole to display resultss.
	 */
	public void startTournament(List<Player> playerList, Map map, int numberOfTurn, int numberOfGames, int gameNumber,
			TextArea txtAreaConsole) {
		PlayGameController controller = new PlayGameController(map);
		controller.loadControllerForTournament(playerList, numberOfTurn, gameNumber, txtAreaConsole);
		controller.addObserver(this);

		// create map entry in results
		if (!results.containsKey(map.getMapData().get("image"))) {
			results.put(map.getMapData().get("image"), new HashMap<String, String>());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		String str = (String) arg;
		System.out.println("update called because of object change " + str);
		String gameNumber = str.substring(str.length()-1, str.length());
		if (str.contains("gameOver")) {
			PlayGameController pgc = ((PlayGameController)o);
			String key = pgc.getMap().getMapData().get("image");
			HashMap<String, String> results2 = results.get(key);

			if(pgc.getPlayerList().size()==1) {
				Player winningPlayer = pgc.getPlayerList().get(0);
				System.out.println("Putting "+gameNumber);
				results2.put("Game " + gameNumber, winningPlayer.getName()+" - "+winningPlayer.getPlayerStrategy());
			} else {
				results2.put("Game " + gameNumber, "Draw");
			}
				
			results.put(key, results2);	
				
			System.out.println("Game Over For Game " + gameNumber);
			
			setChanged();
			notifyObservers();
			
		}

	}

	/**
	 * This method returns the tournament results.
	 * @return results of tournament.
	 */
	public HashMap<String, HashMap<String, String>> getTournamentResults() {
		return results;
	}
}
