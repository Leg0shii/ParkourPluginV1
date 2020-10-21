package de.legoshi.parkourpluginv1.util;

import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerPlayStats;
import org.bukkit.ChatColor;

public class PlayerTag {

			private String prefix;
			private String team;

			public PlayerTag(String prefix, String team) {

						this.prefix = prefix;
						this.team = team;

			}

			public PlayerTag() {

						this.prefix = "";
						this.team = "";

			}

			public String getPrefix() {
						return prefix;
			}

			public String getPrefix(int rank) {

						String prefix = "";

						if(rank <= 5) prefix = "" + ChatColor.AQUA + ChatColor.BOLD;
						if(rank <= 10 && rank > 5) prefix = "" + ChatColor.GOLD + ChatColor.BOLD;
						if(rank <= 15 && rank > 10) prefix = "" + ChatColor.GRAY + ChatColor.BOLD;

						return (prefix);

			}

			public void setPrefix(String prefix) {
						this.prefix = prefix;
			}

			public String getTeam() {
						return team;
			}

			public void setTeam(String team) {
						this.team = team;
			}

			public PlayerTag getPlayerTag(final PlayerObject playerObject) {

						PlayerPlayStats playerPlayStats = playerObject.getPlayerPlayStats();
						String rank = Integer.toString(playerPlayStats.getRank());
						int rankNumber = playerPlayStats.getRank();
						String color = "";

						if(playerPlayStats.getPpcount() == 0) {

									rank = "-";
									rankNumber = 1000000;

						} else {

									color = getPrefix(playerPlayStats.getRank());
									rank = color + rank;

						}

						if(playerObject.getPlayer().getName().equals("Leg0shi_")) {
									rank = "" + ChatColor.RED + ChatColor.BOLD + "Staff";
									rankNumber = 0;
						}

						return new PlayerTag(ChatColor.RESET + "[" + rank + ChatColor.RESET + "] ", Integer.toString((100000 + rankNumber)));

			}

			public String fillSpaces(double stringLength, String extendingString) {

						while(extendingString.length() < stringLength) {

									extendingString = extendingString + " ";

						}

						return extendingString;

			}

			public String difficultyString (double difficulty) {

						String diffString = "★★★★★★★★★★";
						diffString = ChatColor.GOLD + diffString.substring(0, (int) difficulty) + ChatColor.RESET + diffString.substring((int) difficulty, 10);

						return  diffString;

			}

}
