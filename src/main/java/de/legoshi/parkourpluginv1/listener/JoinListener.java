package de.legoshi.parkourpluginv1.listener;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        World world = Bukkit.getWorld("world");
        Location location = new Location(world, -616, 4, 9);
        Main instance = Main.getInstance();
        PlayerObject playerObject = new PlayerObject(player.getUniqueId(), 0, 0, 0, 0, 0, 0);

        instance.inventory.createSpawnInventory(player);
        instance.checkpointManager.playerJoin(player);
        instance.playerManager.playerObjectHashMap.put(player, playerObject);

        Date now = new Date();
        playerObject.setPlaytimeSave(now.getTime());


        if (!(player.hasPlayedBefore())) {

            player.teleport(location);

            event.setJoinMessage(Message.MSG_FirstJoined.getMessage().replace("{Player}", player.getName()));
            player.sendMessage(Message.MSG_Welcome.getMessage());

            instance.mySQL.update("INSERT INTO tablename (playeruuid, playername, ppcountp, playtime, scorecount, failcount) VALUES" +
                "('" + player.getUniqueId().toString() + "', '"+ player.getName() +"' , 0.0, 0.0, 0, 0);");

            instance.playerManager.createPlayerData(player);
            instance.playerManager.calculateRanking(player);

            for(Player all : Bukkit.getOnlinePlayers()) instance.tabTagCreator.updateRank(all);

            Bukkit.getConsoleSender().sendMessage("Initialize new Playerdata");
            return;

        }

        //also sets scoreboard
        instance.playerManager.getPlayerData(player);

        player.teleport(location);
        event.setJoinMessage(Message.MSG_Join.getMessage().replace("{Player}", player.getName()));
        player.sendMessage(Message.MSG_Welcome.getMessage());
        instance.playerManager.calculateRanking(player);
        instance.scoreboardHelper.initializeScoreboard(player);

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {

                instance.scoreboardHelper.updatePPScoreOnScoreBoard(player, playerObject.getPpcount());
                instance.scoreboardHelper.updateFailsOnScoreBoard(player, playerObject.getFailscount());
                instance.scoreboardHelper.updatePlaytimeOnScoreBoard(player, playerObject.getPlaytime());
                instance.scoreboardHelper.updateRankOnScoreBoard(player, playerObject.getRank());

                for(Player all : Bukkit.getOnlinePlayers()) instance.tabTagCreator.updateRank(all);

                timer.cancel();

            }

        }, 1500, 1);

    }

}
