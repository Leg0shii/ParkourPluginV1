package de.legoshi.parkourpluginv1.util;

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

						return new PlayerTag("[" + playerObject.getRank() + "] ", Integer.toString(playerObject.getRank()));

			}

}
