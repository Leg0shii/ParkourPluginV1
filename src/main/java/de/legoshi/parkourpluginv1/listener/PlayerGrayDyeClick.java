package de.legoshi.parkourpluginv1.listener;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.FW;
import de.legoshi.parkourpluginv1.util.Message;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerMap;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerPlayStats;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerStatus;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;

public class PlayerGrayDyeClick {

    public void onGrayClick(PlayerInteractEvent event) throws IOException {

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

                World playerWorld = player.getWorld();
                player.teleport(instance.spawn);
                int i = 0;

                for(Player all : Bukkit.getOnlinePlayers()) { //checks if players are still in the world

                    if(all.getWorld().equals(playerWorld)) {

                        i++;

                    }

                }

                if(i == 0) instance.worldSaver.zipWorld(playerWorld);

                player.sendMessage(Message.Prefix.getRawMessage() + "You left the Map");
                instance.inventory.createSpawnInventory(player);
                instance.checkpointManager.checkpointObjectHashMap.get(player).setLocation(instance.spawn);
                event.setCancelled(true);

            } else if(event.getItem().equals(grayDye) && playerStatus.isBuildmode()) {

                playerStatus.setBuildmode(false);
                player.setGameMode(GameMode.ADVENTURE);
                World playerWorld = player.getWorld();

                FW fw = new FW("./ParkourBuild", player.getUniqueId().toString() + ".yml");

                if(fw.getInt("mapname") == Integer.parseInt(player.getWorld().getName())) {

                    instance.worldSaver.moveAllPlayer(playerWorld);
                    instance.worldSaver.zipWorld(playerWorld);
                    instance.checkpointManager.checkpointObjectHashMap.get(player).setLocation(instance.spawn);

                    player.closeInventory();
                    player.sendMessage(Message.Prefix.getRawMessage() + "World Saved!");
                    event.setCancelled(true);
                    return;

                }

                instance.inventory.createSpawnInventory(player);
                player.closeInventory();
                instance.checkpointManager.checkpointObjectHashMap.get(player).setLocation(instance.spawn);
                player.teleport(instance.spawn);

                event.setCancelled(true);

            }

        }

    }

}
