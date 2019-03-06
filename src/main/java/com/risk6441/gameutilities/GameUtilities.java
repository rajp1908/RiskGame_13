package com.risk6441.gameutilities;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Country;
import com.risk6441.exception.InvalidMap;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author Raj
 *
 */
public class GameUtilities {
	static int count=0;
	public static TextArea txtMsgArea = null;
	public static boolean isTestMode = false;
	
	/**
	 * This method writes a log in Text Area.
	 * @param string String that is to be written in textArea.
	 * @param textAreaMsg TextArea object {@link javafx.scene.control.TextArea}
	 */
	public static void addLogFromText(String string, TextArea textAreaMsg) {
		Platform.runLater(() -> textAreaMsg.appendText(string));
	}
	
	/**
	 * This method writes a log in Text Area.
	 * @param string String that is to be written in textArea.
	 */
	public static void addLogFromText(String string) {
//		try {
			
		if(!isTestMode)
			Platform.runLater(() -> txtMsgArea.appendText(string));	
//		}
//		catch(Exception e) {}
	}
	
	/**
	 * This method allocates armies to the players.
	 * @param map
	 * 			map object
	 * @param players
	 * 		  		list of the player
	 * @param textAres
	 * 				  textArea object
	 * @throws InvalidMapException Throws IOException if there is an issue while reading a map file.
	 */
	public static void allocateCountryToPlayer(Map map, List<Player> players, TextArea textAres) throws InvalidMap {

		List<Country> allCountriesList = getCountryList(map);

		
		int playerNo = 0;
		for (int i = 0; i < allCountriesList.size(); i++) {
			if(playerNo == players.size())
				playerNo = 0;

			Country country = allCountriesList.get(i);
			
			if(i >= ((allCountriesList.size() / players.size()) * players.size())) {
				Random r = new Random();
				
				int randomNo = r.nextInt(players.size());
				System.err.println("Error 2 "+randomNo);
				Player player = players.get(randomNo);
				country.setPlayer(player);
				country.setArmy(country.getArmy() + 1);
				player.setArmies(player.getArmies() - 1);
				player.getAssignedCountry().add(country);
				addLogFromText(
						country.getName() + " is assigned to " + player.getName() + " ! \n");
				
      			continue;
			}
			
			Player player = players.get(playerNo++);
			country.setPlayer(player);
			country.setArmy(country.getArmy() + 1);

			player.getAssignedCountry().add(country);
			addLogFromText(
					country.getName() + " is assigned to " + player.getName() + " ! \n");
			
		}
	}

	/**
	 * This method returns the country list.
	 * @param map Current map object.
	 * @return Country list.
	 */
	public static List<Country> getCountryList(Map map) {
		List<Country> allcountriesList = new ArrayList<Country>();
		if (map.getContinents() != null) {
			for (Continent continent : map.getContinents()) {
				if (continent != null && continent.getCountries() != null) {
					for (Country country : continent.getCountries()) {
						allcountriesList.add(country);
					}
				}
			}
		}
		System.out.println("Total No of Countries : "+allcountriesList.size());
		return allcountriesList;
	}
	
	/**
	 * This method checks whether the fortification phase is completed or not.
	 * @param numberOfPlayer
	 * 			 count of active number of players in the game
	 * @return
	 * 		  returns true if the fortification phase is completed
	 */
	public static boolean checkFortificationPhase(int numberOfPlayer)
	{
		count++;
		if(count==numberOfPlayer)
			return true;
		else
			return false;
	}
	
	public static void clearCheckBoxes(CheckBox... checkboxes) {
		for (CheckBox checkbox : checkboxes) {
			checkbox.setSelected(false);
			checkbox.setText("");
		}
	}
	
	/**
	 * This method is used to set visible false of pane.
	 * 
	 * @param panes
	 *            list of panes to be disabled
	 */
	public static void disableViewPane(Pane... panes) {
		for (Pane pane : panes) {
			pane.setVisible(false);
		}
	}
	
	/**
	 * This method is used to set visible true of pane.
	 * 
	 * @param panes
	 *            list of panes to be enabled
	 */
	public static void enablePane(Pane... panes) {
		for (Pane pane : panes) {
			pane.setVisible(true);
		}
	}
	
	/**
	 * This method is used to close screen.
	 * @param button Button to exit windows.
	 */
	public static void exitWindows(Button button) {
		try {
			Stage stage = (Stage) button.getScene().getWindow();
			stage.close();
		}catch (Exception e) {
			System.out.println(e.getMessage()+" Error Message");
		}
	}

	/**
	 * This method gives the list of adjacent countries for fortification.
	 * @param country Country whose adjacent countries need to be found.
	 * @param map The current map.
	 * @param currentPlayer The object of current player.
	 * @return list of countries.
	 */
	public static List<Country> getAdjecentCountryForFortifiction(Country country,Map map,Player currentPlayer) {
		List<Country> reachableCountryList = new ArrayList<Country>();
		List<Country> allCountries = GameUtilities.getCountryList(map);
		
		bfsCountry(country,reachableCountryList,country,currentPlayer);
		
		for(Country t : allCountries) {
			t.setProcessed(false);
		}	
		return reachableCountryList;
	}
	
	/**
	 * This method checks whether all the countries are traversed.
	 * @param country Start country.
	 * @param reachableCountryList All reachable countries from the starting country.
	 * @param root The root country.
	 * @param currentPlayer The object of current player.
	 */
	public static  void bfsCountry(Country country, List<Country> reachableCountryList, Country root, Player currentPlayer) {

		if(country.isProcessed() == true) {
			return;
		}
		
		country.setProcessed(true);
		if(!country.equals(root)){
				reachableCountryList.add(country);
			}
		for(Country t : country.getAdjacentCountries()){
			if(t.isProcessed() == false && t.getPlayer().equals(currentPlayer)){
				bfsCountry(t,reachableCountryList,root,currentPlayer);
			}
		}		
	}

}
