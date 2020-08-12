package de.legoshi.parkourpluginv1.util;

import de.legoshi.parkourpluginv1.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;

public class TabTagCreator {

			public void updateRank(Player player) {

						Main instance = Main.getInstance();
						Scoreboard scoreboard = player.getScoreboard();

						for(Player all : Bukkit.getOnlinePlayers()) {

									PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);
									PlayerTag playerTag = instance.playerTag.getPlayerTag(playerObject);

									String team = playerTag.getTeam();

									if(scoreboard.getTeam(team) == null) {

												scoreboard.registerNewTeam(team);

									}

									scoreboard.getTeam(team).setPrefix(playerTag.getPrefix());
									scoreboard.getTeam(team).addPlayer(all);

						}

			}

}
