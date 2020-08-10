package de.legoshi.parkourpluginv1.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockDestroyListener implements Listener {

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event) {

        Player player = event.getPlayer();

        if(!(player.hasPermission("permission.destroy"))) {
            event.setCancelled(true);
        }

    }
}
