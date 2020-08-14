package de.legoshi.parkourpluginv1.listener;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.Message;
import de.legoshi.parkourpluginv1.util.PlayerObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        Main instance = Main.getInstance();
        PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);

        //cancels timer that might be running when leaving from course
        playerObject.getTimer().cancel();

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
