package de.legoshi.parkourpluginv1.listener;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.Message;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) throws IOException {

        Player player = event.getPlayer();
        World playerWorld = player.getWorld();
        Main instance = Main.getInstance();
        PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);

        //cancels timer that might be running when leaving from course
        playerObject.getPlayerPlayStats().getTimer().cancel();

        if(!(playerWorld.getName().equals("world")) && playerObject.getPlayerStatus().isBuildmode()) {

            player.teleport(instance.spawn);
            instance.worldSaver.moveAllPlayer(playerWorld);
            instance.worldSaver.zipWorld(playerWorld);

        } else if (!(playerWorld.getName().equals("world")) && playerObject.getPlayerStatus().isJumpmode()) {

            player.teleport(instance.spawn);

            int i = 0;

            for(Player all : Bukkit.getOnlinePlayers()) { //checks if players are still in the world

                if(all.getWorld().equals(playerWorld)) {

                    i++;

                }

            }

            if(i == 0) instance.worldSaver.zipWorld(playerWorld);

        }

        //adds all data to db when player leaves
        instance.mySQLManager.savingPlayerDataToDB(player);

        //emptying filled hashmaps
        instance.playerManager.playerObjectHashMap.remove(player);
        instance.checkpointManager.checkpointObjectHashMap.remove(player);
        event.setQuitMessage("");

        for(Player all : Bukkit.getOnlinePlayers()) {

            all.sendMessage(Message.MSG_Leave.getMessage().replace("{Player}", player.getName()));

        }

    }

}
