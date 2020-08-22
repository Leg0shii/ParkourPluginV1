package de.legoshi.parkourpluginv1.listener;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.PlayerObject;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerPickAxeClick implements Listener {

			public void onPickAxeClick(PlayerInteractEvent event) {

						Player player = event.getPlayer();
						Main instance = Main.getInstance();
						PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);

						ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE, 1);
						ItemMeta pickaxeMeta = pickaxe.getItemMeta();
						pickaxeMeta.setDisplayName(ChatColor.RESET + "Building");
						pickaxe.setItemMeta(pickaxeMeta);

						if (event.hasItem() && event.getItem().isSimilar(pickaxe) && !(playerObject.isBuildmode())) {

									if(playerObject.isBuildCourse()) {

												instance.invGui.guiEditMap(player).show(player);

									} else {

												instance.invGui.guiCreateMap(player).show(player);

									}

						}

			}

}
