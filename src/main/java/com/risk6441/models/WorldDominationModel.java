
package com.risk6441.models;
import java.util.HashMap;
import java.util.Map.Entry;
import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Country;

/**
 * This class handles data for the graph of world domination coverage and military chart.
 * @author Hardik
 */
public class WorldDominationModel {
	
	/**
	 * Show domination of world by players
	 * 
	 * @param map map object
	 * @return player_country_percent returns percentage of player's country in the continent
	 */
	public static HashMap<Player, Double> getWorldDominationData(Map map) {

		HashMap<Player, Double> player_country_count_Map = new HashMap<>();
		Double country_count = 0.0;
		for (Continent cont : map.getContinents()) {
			for (Country ter : cont.getCountries()) {
				country_count++;
				Player player = ter.getPlayer();
				if(player_country_count_Map.containsKey(player)) {
					player_country_count_Map.put(player, player_country_count_Map.get(player)+1);
				} else {
					player_country_count_Map.put(player, Double.valueOf("1"));
				}
			}
		}

		HashMap<Player, Double> player_country_percent = new HashMap<>();
		for(Entry<Player, Double> entry : player_country_count_Map.entrySet()) {
			player_country_percent.put(entry.getKey(), ((entry.getValue()/country_count) * 100));
		}
		return player_country_percent;
	}
	
	/**
	 * Show military distributions
	 * 
	 * @param map map object
	 * @return player_military_count_Map returns the military count in Country
	 */
	public static HashMap<String, Double> getMilitaryDominationData(Map map) {
		HashMap<String, Double> player_military_count_Map = new HashMap<>();
		for (Continent cont : map.getContinents()) {
			for (Country con : cont.getCountries()) {
				Player player = con.getPlayer();
				
				if(player_military_count_Map.containsKey(player.getName())) {
					player_military_count_Map.put(player.getName(), player_military_count_Map.get(player.getName())+con.getArmy());
				} else {
					player_military_count_Map.put(player.getName(), Double.valueOf(con.getArmy()));
				}
			}
		}
		return player_military_count_Map;
	}

}
