package de.legoshi.parkourpluginv1.listener;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerNetherStarClick {

    @EventHandler
    public void onNetherStarClick(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Action action = event.getAction();
        Main instance = Main.getInstance();
        PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);

        if ((action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) && event.hasItem()) {

            if (event.getItem().getType().equals(Material.NETHER_STAR) && !(playerObject.getPlayerStatus().isBuildmode())) {

                if(player.isOp()) {

                    instance.invGui.guiStatusSelect(player).show(player);

                } else {

                    instance.invGui.guiShowAllCourses(player, "ranked");

                }

            }

        }

    }

}
