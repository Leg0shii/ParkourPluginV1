package de.legoshi.parkourpluginv1.listener;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.PlayerObject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;
import java.util.UUID;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        Main instance = Main.getInstance();
        PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);
        Date now = new Date();

        long timeGained = (now.getTime() - playerObject.getPlaytimeSave()) + playerObject.getPlaytime();
        int failsGained = (playerObject.getFailscount() + playerObject.getFailsrelative());
        double ppGained = playerObject.getPpcount();
        UUID playerUUID = player.getUniqueId();

        //cancels timer that might be running when leaving from course
        playerObject.getTimer().cancel();

        //adds failcount to db when player leaves
        instance.mySQLManager.savingPlayerDataToDB(player);

        //emptying filled hashmaps
        instance.playerManager.playerObjectHashMap.remove(player);
        instance.checkpointManager.checkpointObjectHashMap.remove(player);

    }

}
