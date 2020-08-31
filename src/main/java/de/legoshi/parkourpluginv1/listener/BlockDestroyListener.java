package de.legoshi.parkourpluginv1.listener;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockDestroyListener implements Listener {

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event) {

        Player player = event.getPlayer();

        PlayerObject playerObject = Main.getInstance().playerManager.playerObjectHashMap.get(player);

        if(player.hasPermission("permission.destroy") || playerObject.getPlayerStatus().isBuildmode()) {

            return;

        }

        event.setCancelled(true);

    }

}
