package de.legoshi.parkourpluginv1.listener.cancellistener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

public class PortalCreateListener implements Listener {

			@EventHandler
			public void onCreate(PortalCreateEvent event) {

						event.setCancelled(true);

			}

}
