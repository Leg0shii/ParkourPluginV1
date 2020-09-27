package de.legoshi.parkourpluginv1.listener.itemclicks;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.Message;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerMap;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerPlayStats;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerStatus;
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
            PlayerStatus playerStatus = playerObject.getPlayerStatus();
            PlayerMap playerMap = playerObject.getPlayerMap();
            PlayerPlayStats playerPlayStats = playerObject.getPlayerPlayStats();

            //cancels double clicks
            if(playerStatus.isDyeClick()) {  return; }
            else playerStatus.setDyeClick(true);

            //saves Fails into Playerobject after reset
            playerPlayStats.setFailscount((playerMap.getFailsrelative() + playerPlayStats.getFailscount()));

            //sets fails and time to 0 again
            playerMap.setFailsrelative(0);
            playerMap.setTimeRelative(0);

            //teleports player to start
            instance.checkpointManager.checkpointObjectHashMap.get(player).setLocation(playerMap.getMapObject().getMapMetaData().getSpawn());
            player.teleport(playerMap.getMapObject().getMapMetaData().getSpawn());
            player.sendMessage(Message.Prefix.getRawMessage() + "Restarted");

            //cancles clickevent and closes Inventory
            instance.playerInteractManager.getRedDyeClick().timerRedDyeClick(player);
            event.setCancelled(true);
            player.closeInventory();

        }

    }

}
