package de.legoshi.parkourpluginv1.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDropListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {

        event.setCancelled(true);

    }

}
