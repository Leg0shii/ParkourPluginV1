package de.legoshi.parkourpluginv1.listener;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.PlayerObject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        Player player = event.getPlayer();
        player.sendMessage("Block pl");

        PlayerObject playerObject = Main.getInstance().playerManager.playerObjectHashMap.get(player);
        player.sendMessage("Perm: " + playerObject.isBuildmode());

        if(playerObject.isBuildmode()) {

            event.setCancelled(false);
            return;

        }

        event.setCancelled(true);

    }

}
