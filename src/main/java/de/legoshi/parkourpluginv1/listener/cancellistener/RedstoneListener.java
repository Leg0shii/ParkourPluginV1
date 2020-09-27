package de.legoshi.parkourpluginv1.listener.cancellistener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonEvent;
import org.bukkit.event.block.BlockRedstoneEvent;

public class RedstoneListener implements Listener {

			@EventHandler
			public void onRedstone(BlockRedstoneEvent event) {

						event.setNewCurrent(0);

			}

}
