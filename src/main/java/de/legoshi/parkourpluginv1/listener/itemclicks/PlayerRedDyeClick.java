package de.legoshi.parkourpluginv1.listener.itemclicks;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.Replay;
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
import org.bukkit.scoreboard.Scoreboard;

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

        if(playerStatus.isJumpmode()) {
            if ((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) && event.hasItem()) {

                ItemStack redDye = new ItemStack(Material.INK_SACK, 1, (short) 1);
                ItemMeta metaRedDye = redDye.getItemMeta();
                metaRedDye.setDisplayName(ChatColor.RESET + "Checkpoint");
                redDye.setItemMeta(metaRedDye);



                if(clickedItem.equals(redDye) && clickedItem.getItemMeta().getDisplayName().equals(ChatColor.RESET + "Checkpoint")) {

                    if(playerStatus.isDyeClick()) {  return; }
                    else playerStatus.setDyeClick(true);

                    if(!playerStatus.isHasCP()) {

                        //sets fails and time to 0 again
                        playerMap.setFailsrelative(0);
                        playerMap.setTimeRelative(0);

                        playerObject.getReplay().stopReplayRec();
                        Replay replay = new Replay(instance, playerObject);
                        replay.runTaskTimer(instance, 0, 1L);
                        playerObject.setReplay(replay);

                        //instance.replay.deleteReplayFile(playerObject);
                        //instance.replay.startReplayRecording(playerObject);

                    } else {

                        playerMap.setFailsrelative(playerMap.getFailsrelative() + 1);

                    }

                    player.teleport(instance.checkpointManager.checkpointObjectHashMap.get(player).getLocation());

                    //prevents calling function with offhand
                    if(event.getHand() == EquipmentSlot.OFF_HAND) return;

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
