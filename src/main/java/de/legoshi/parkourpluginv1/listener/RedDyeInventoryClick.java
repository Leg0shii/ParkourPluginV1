package de.legoshi.parkourpluginv1.listener;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.Message;
import de.legoshi.parkourpluginv1.util.PlayerObject;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RedDyeInventoryClick implements Listener{

    @EventHandler
    public void onRedDyeInventoryClick(InventoryClickEvent event) {

        //creates two Items that are compared (item that got lcicked with reddye)
        ItemStack redDye = new ItemStack(Material.INK_SACK, 1, (short) 1);
        ItemStack currentItem = event.getCurrentItem();
        Main instance = Main.getInstance();
        ItemMeta metaRedDye = redDye.getItemMeta();
        Player player = (Player) event.getWhoClicked();

        metaRedDye.setDisplayName("Checkpoint");
        redDye.setItemMeta(metaRedDye);

        if(currentItem != null && currentItem.equals(redDye)) {

            PlayerObject playerObject = Main.getInstance().playerManager.playerObjectHashMap.get(player);

            //cancels double clicks
            if(playerObject.isDyeClick()) {  return; }
            else playerObject.setDyeClick(true);

            //saves Fails into Playerobject after reset
            playerObject.setFailscount((playerObject.getFailsrelative() + playerObject.getFailscount()));

            //sets fails and time to 0 again
            playerObject.setFailsrelative(0);
            playerObject.setTimerelative(0);

            //teleports player to start
            player.teleport(playerObject.getMapObject().getSpawn());
            player.sendMessage(Message.Prefix.getRawMessage() + "Restarted");

            //cancles clickevent and closes Inventory
            instance.playerInteractManager.getRedDyeClick().timerRedDyeClick(player);
            event.setCancelled(true);
            player.closeInventory();

        }

    }

}
