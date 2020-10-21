package de.legoshi.parkourpluginv1.listener.cancellistener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;

public class IceMeltListener implements Listener {

			@EventHandler
			public void onMelt(BlockFadeEvent event) {

						event.setCancelled(true);

			}

}
