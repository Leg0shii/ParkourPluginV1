package de.legoshi.parkourpluginv1.listener;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.Message;
import de.legoshi.parkourpluginv1.util.PlayerObject;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerCyanDyeClick {

    public void onCyanDyeInventoryClick(PlayerInteractEvent event) {

        //creates two Items that are compared (item that got lcicked with purpledye)
        ItemStack purpleDye = new ItemStack(Material.INK_SACK, 1, (short) 6);
        Player player = event.getPlayer();
        ItemStack currentItem = event.getItem();
        Main instance = Main.getInstance();
        ItemMeta metaPurpleDye = purpleDye.getItemMeta();


        metaPurpleDye.setDisplayName(ChatColor.RESET + "Restart");
        purpleDye.setItemMeta(metaPurpleDye);

        if(currentItem != null && currentItem.equals(purpleDye)) {

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
