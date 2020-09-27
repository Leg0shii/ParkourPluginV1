package de.legoshi.parkourpluginv1.listener.cancellistener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawnListener implements Listener {

			@EventHandler
			public void onEntitySpawn(EntitySpawnEvent entitySpawnEvent) {

						entitySpawnEvent.setCancelled(true);

			}

}
