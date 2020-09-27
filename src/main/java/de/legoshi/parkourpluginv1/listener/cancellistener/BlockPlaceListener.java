package de.legoshi.parkourpluginv1.listener.cancellistener;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerStatus;
import net.minecraft.server.v1_12_R1.Block;
import net.minecraft.server.v1_12_R1.BlockEnderPortal;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.PortalCreateEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        Player player = event.getPlayer();

        //no fire no tnt
        if( event.getBlockPlaced().getType().equals(Material.FIRE) ||
            event.getBlockPlaced().getType().equals(Material.TNT) ||
            event.getBlockPlaced().getType().equals(Material.BARRIER) ||
            event.getBlockPlaced().getType().equals(Material.COMMAND) ||
            event.getBlockPlaced().getType().equals(Material.OBSERVER) ||
            event.getBlockPlaced().getType().equals(Material.PISTON_STICKY_BASE)) {

            event.getPlayer().sendMessage("You cant place this Block.");
            event.setCancelled(true);
            return;

        }

        PlayerStatus playerStatus = Main.getInstance().playerManager.playerObjectHashMap.get(player).getPlayerStatus();

        if(player.getWorld().getName().equals("world") || !playerStatus.isBuildmode()) {

            player.sendMessage("Not at spawn!");
            event.setCancelled(true);

        }

    }

}
