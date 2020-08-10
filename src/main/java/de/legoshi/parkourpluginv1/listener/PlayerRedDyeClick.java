package de.legoshi.parkourpluginv1.listener;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.PlayerObject;
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
        Action action = event.getAction();
        ItemStack clickedItem = event.getItem();
        Main instance = Main.getInstance();

        boolean isJumpmode = playerObject.isJumpmode();

        if(isJumpmode) {
            if ((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) && event.hasItem()) {

                ItemStack redDye = new ItemStack(Material.INK_SACK, 1, (short) 1);
                ItemMeta metaRedDye = redDye.getItemMeta();
                metaRedDye.setDisplayName("Checkpoint");
                redDye.setItemMeta(metaRedDye);

                if(clickedItem.equals(redDye) && clickedItem.getItemMeta().getDisplayName().equals("Checkpoint")) {

                    if(playerObject.isDyeClick()) {  return; }
                    else playerObject.setDyeClick(true);

                    //prevents calling function with offhand
                    if(event.getHand() == EquipmentSlot.OFF_HAND) return;

                    playerObject.setFailsrelative(playerObject.getFailsrelative() + 1);
                    player.teleport(Main.getInstance().checkpointManager.checkpointObjectHashMap.get(player).getLocation());
                    instance.scoreboardHelper.updateFailsOnScoreBoard(player, (playerObject.getFailscount()+playerObject.getFailsrelative()));
                    timerRedDyeClick(player);

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

                playerObject.setDyeClick(false);
                timer.cancel();

            }

        }, 50, 50);

    }

}
