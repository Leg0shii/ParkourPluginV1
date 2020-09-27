package de.legoshi.parkourpluginv1.listener.itemclicks;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerGlowstoneClick {

			public void onGlowstoneClick(PlayerInteractEvent event) {

						Action action = event.getAction();
						Player player = event.getPlayer();
						PlayerObject playerObject = Main.getInstance().playerManager.playerObjectHashMap.get(player);

						if ((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) && event.hasItem()) {

									if (event.getItem().getType().equals(Material.GLOWSTONE_DUST)) {

												for (Player all : Bukkit.getOnlinePlayers()) {

															if (!playerObject.getPlayerStatus().isGsClick()) {
																		player.hidePlayer(Main.getInstance(), all);
															} else {
																		player.showPlayer(Main.getInstance(), all);
															}

												}

												playerObject.getPlayerStatus().setGsClick(!playerObject.getPlayerStatus().isGsClick());

									}

						}

			}

}
