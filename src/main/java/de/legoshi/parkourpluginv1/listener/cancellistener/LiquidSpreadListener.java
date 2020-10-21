package de.legoshi.parkourpluginv1.listener.cancellistener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class LiquidSpreadListener implements Listener {

			@EventHandler
			public void onSpread(BlockFromToEvent event) {

						event.setCancelled(true);

			}

}
