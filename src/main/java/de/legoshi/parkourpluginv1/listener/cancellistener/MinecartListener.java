package de.legoshi.parkourpluginv1.listener.cancellistener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;

public class MinecartListener implements Listener {

			@EventHandler
			public void onMinecraftPlacement(VehicleCreateEvent event) {

						event.setCancelled(true);

			}

}
