package de.legoshi.parkourpluginv1.listener.cancellistener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockSpreadEvent;

public class BlockGrowListener implements Listener {

			@EventHandler
			public void onVineGrow(BlockGrowEvent event) {

						event.setCancelled(true);

			}

			@EventHandler
			public void onGrow(BlockSpreadEvent event) {

						event.setCancelled(true);

			}

}
