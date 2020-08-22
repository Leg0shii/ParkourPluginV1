package de.legoshi.parkourpluginv1.listener;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.Message;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerMap;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerPlayStats;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerStatus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerGrayDyeClick {

    public void onGrayClick(PlayerInteractEvent event) {

        if (!(event.getAction() == Action.PHYSICAL) && event.hasItem()) {

            ItemStack grayDye = new ItemStack(Material.INK_SACK, 1, (short) 5);
            ItemMeta metaGrayDye = grayDye.getItemMeta();
            Player player = event.getPlayer();
            Main instance = Main.getInstance();

            metaGrayDye.setDisplayName(ChatColor.RESET + "Back to Spawn");
            grayDye.setItemMeta(metaGrayDye);
            PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);
            PlayerStatus playerStatus = playerObject.getPlayerStatus();
            PlayerPlayStats playerPlayStats = playerObject.getPlayerPlayStats();
            PlayerMap playerMap = playerObject.getPlayerMap();

            //prevents calling function with offhand
            if(event.getHand() == EquipmentSlot.OFF_HAND) return;

            if (event.getItem().equals(grayDye) && !(playerStatus.isBuildmode())) {

                playerPlayStats.setFailscount(playerMap.getFailsrelative() + playerPlayStats.getFailscount());
                playerMap.setFailsrelative(0);
                playerStatus.setJumpmode(false);
                playerStatus.setBuildmode(false);

                player.teleport(new Location(player.getWorld(), -616, 4, 9));
                player.sendMessage(Message.Prefix.getRawMessage() + "You left the Map");
                instance.inventory.createSpawnInventory(player);
                event.setCancelled(true);

            } else if(event.getItem().equals(grayDye) && playerStatus.isBuildmode()) {

                playerStatus.setBuildmode(false);
                player.getWorld().save();
                player.teleport(new Location(Bukkit.getWorld("world"), -616, 4, 9));
                player.sendMessage(Message.Prefix.getRawMessage() + "World Saved!");
                instance.inventory.createSpawnInventory(player);
                player.closeInventory();
                event.setCancelled(true);

            }

        }

    }

}
