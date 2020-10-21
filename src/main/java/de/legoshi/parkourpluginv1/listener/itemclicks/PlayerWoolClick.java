package de.legoshi.parkourpluginv1.listener.itemclicks;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerWoolClick {

			public void onWoolClick(PlayerInteractEvent event){

						if(event.hasItem()) {

									Main instance = Main.getInstance();
									Player player = event.getPlayer();
									ItemStack redWool = instance.itemCreator.createItem(Material.WOOL, 1, ChatColor.RESET + "Stop", (byte)14);
									ItemStack greenWool = instance.itemCreator.createItem(Material.WOOL, 1, ChatColor.RESET + "Start", (byte)5);
									PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);

									if(greenWool.equals(event.getItem())) {

												event.setCancelled(true);
												playerObject.getPlayerStatus().setReplayStart(true);
												player.getInventory().setItem(4, redWool);

									} else if(redWool.equals(event.getItem())) {

												event.setCancelled(true);
												playerObject.getPlayerStatus().setReplayStart(false);
												player.getInventory().setItem(4, greenWool);

									}

						}

			}

}
