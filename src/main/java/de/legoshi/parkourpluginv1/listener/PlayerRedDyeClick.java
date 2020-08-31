package de.legoshi.parkourpluginv1.listener;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerMap;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerStatus;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Timer;
import java.util.TimerTask;

public class PlayerRedDyeClick {

    public void onReDyeClick(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        PlayerObject playerObject = Main.getInstance().playerManager.playerObjectHashMap.get(player);
        PlayerStatus playerStatus = playerObject.getPlayerStatus();
        PlayerMap playerMap = playerObject.getPlayerMap();
        Action action = event.getAction();
        ItemStack clickedItem = event.getItem();
        Main instance = Main.getInstance();

        boolean isJumpmode = playerStatus.isJumpmode();

        if(isJumpmode) {
            if ((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) && event.hasItem()) {

                ItemStack redDye = new ItemStack(Material.INK_SACK, 1, (short) 1);
                ItemMeta metaRedDye = redDye.getItemMeta();
                metaRedDye.setDisplayName(ChatColor.RESET + "Checkpoint");
                redDye.setItemMeta(metaRedDye);

                if(clickedItem.equals(redDye) && clickedItem.getItemMeta().getDisplayName().equals(ChatColor.RESET + "Checkpoint")) {

                    if(playerStatus.isDyeClick()) {  return; }
                    else playerStatus.setDyeClick(true);

                    //prevents calling function with offhand
                    if(event.getHand() == EquipmentSlot.OFF_HAND) return;

                    playerMap.setFailsrelative(playerMap.getFailsrelative() + 1);
                    player.teleport(Main.getInstance().checkpointManager.checkpointObjectHashMap.get(player).getLocation());
                    instance.scoreboardHelper.updateFailsOnScoreBoard(player, (playerObject.getPlayerPlayStats().getFailscount()+playerMap.getFailsrelative()));
                    timerRedDyeClick(player);

                }

            }

        }

        if(playerStatus.isBuildmode()) {

            ItemStack redDye = new ItemStack(Material.INK_SACK, 1, (short) 1);
            ItemMeta metaRedDye = redDye.getItemMeta();
            metaRedDye.setDisplayName(ChatColor.RESET + "Checkpoint");
            redDye.setItemMeta(metaRedDye);

            if ((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) && event.hasItem()) {

                if(clickedItem.equals(redDye) && clickedItem.getItemMeta().getDisplayName().equals(ChatColor.RESET + "Checkpoint")) {

                    player.teleport(instance.checkpointManager.checkpointObjectHashMap.get(player).getLocation());
                    event.setCancelled(true);

                }

            } else if((action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) && event.hasItem()) {

                if(clickedItem.equals(redDye) && clickedItem.getItemMeta().getDisplayName().equals(ChatColor.RESET + "Checkpoint")) {

                    player.sendMessage("Position saved: " + player.getLocale());
                    instance.checkpointManager.checkpointObjectHashMap.get(player).setLocation(player.getLocation());
                    event.setCancelled(true);

                }

            }

        }

    }

    public void timerRedDyeClick(Player player) {

        Timer timer = new Timer();
        PlayerObject playerObject = Main.getInstance().playerManager.playerObjectHashMap.get(player);

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {

                playerObject.getPlayerStatus().setDyeClick(false);
                timer.cancel();

            }

        }, 50, 50);

    }

}
