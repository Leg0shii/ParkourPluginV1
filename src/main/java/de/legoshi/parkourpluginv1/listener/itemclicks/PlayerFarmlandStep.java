package de.legoshi.parkourpluginv1.listener.itemclicks;

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerFarmlandStep {

			public void onFarmland(PlayerInteractEvent event) {

						if (event.getAction().equals(Action.PHYSICAL)) {

									if (event.getClickedBlock().getTypeId() == 60) {

												event.setCancelled(true);

									}

						}

			}

}
