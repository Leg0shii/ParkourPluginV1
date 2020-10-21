package de.legoshi.parkourpluginv1.listener.cancellistener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.LeavesDecayEvent;

public class LeafDecayListener implements Listener {

			@EventHandler
			public void onLeafDeca(LeavesDecayEvent event) {

						event.setCancelled(true);

			}

			@EventHandler
			public void onDecay(BlockPhysicsEvent event) {

						event.setCancelled(true);

			}

}
