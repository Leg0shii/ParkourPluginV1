package de.legoshi.parkourpluginv1.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerChestClick {

			public void onChestOpen(PlayerInteractEvent event) {

						if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {

									Block block = event.getClickedBlock();
									if( block.getType().equals(Material.CHEST) ||
											block.getType().equals(Material.TRAPPED_CHEST) ||
											block.getType().equals(Material.ENDER_CHEST) ||
											block.getType().equals(Material.HOPPER) ||
											block.getType().equals(Material.HOPPER_MINECART) ||
											block.getType().equals(Material.STORAGE_MINECART) ||
											block.getType().equals(Material.DROPPER) ||
											block.getType().equals(Material.DISPENSER))  {

												event.setCancelled(true);

									}

						}

			}

}
