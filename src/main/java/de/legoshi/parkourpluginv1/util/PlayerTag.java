package de.legoshi.parkourpluginv1.util;

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

						if(rank == 1) prefix = "" + ChatColor.AQUA + ChatColor.BOLD;
						if(rank == 2) prefix = "" + ChatColor.GOLD + ChatColor.BOLD;
						if(rank == 3) prefix = "" + ChatColor.GRAY + ChatColor.BOLD;

						return prefix;

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

						String rank = Integer.toString(playerObject.getRank());
						String color = "";

						color = getPrefix(playerObject.getRank());
						rank = color + rank;

						return new PlayerTag(ChatColor.RESET + "[" + rank + ChatColor.RESET + "] ", Integer.toString(playerObject.getRank()));

			}

}
