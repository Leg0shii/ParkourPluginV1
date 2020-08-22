package de.legoshi.parkourpluginv1.listener;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.PlayerObject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

       /*if(!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        PlayerObject playerObject = Main.getInstance().playerManager.playerObjectHashMap.get(player);

        if(player.hasPermission("permission.build") || playerObject.isBuildmode()) {

            return;

        }

        event.setCancelled(true); */

    }

}
