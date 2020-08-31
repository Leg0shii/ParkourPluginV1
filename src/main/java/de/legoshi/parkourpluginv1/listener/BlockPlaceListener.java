package de.legoshi.parkourpluginv1.listener;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        Player player = event.getPlayer();
        PlayerObject playerObject = Main.getInstance().playerManager.playerObjectHashMap.get(player);

        if(playerObject.getPlayerStatus().isBuildmode() || player.isOp()) {

            event.setCancelled(false);
            return;

        }

        event.setCancelled(true);

    }

}
