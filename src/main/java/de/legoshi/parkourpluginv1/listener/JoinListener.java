package de.legoshi.parkourpluginv1.listener;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.*;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerMap;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerPlayStats;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerStatus;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws IOException {

        Player player = event.getPlayer();
        player.setGameMode(GameMode.ADVENTURE);
        World world = Bukkit.getWorld("world");
      
        Location location = new Location(world, -619, 5, 10, -160, 5);

        Main instance = Main.getInstance();
        PlayerObject playerObject = new PlayerObject(player);
        FW fw = new FW("./ParkourBuild/", player.getUniqueId().toString() + ".yml");
        fw.save();

        instance.inventory.createSpawnInventory(player);
        instance.checkpointManager.playerJoin(player);
        instance.playerManager.playerObjectHashMap.put(player, playerObject);

        Date now = new Date();
        playerObject.getPlayerPlayStats().setPlaytimeSave(now.getTime());

        //welcome message
        instance.titelManager.sendTitle(player, "", "Welcome to RPK!", 20);

        Bukkit.getConsoleSender().sendMessage("Initialize new Playerdata");

        if (!(player.hasPlayedBefore())) {

            player.teleport(location);
            player.sendMessage(Message.MSG_Welcome.getMessage());

            event.setJoinMessage(Message.MSG_FirstJoined.getMessage().replace("{Player}", player.getName()));

            instance.mySQL.update("INSERT INTO tablename (playeruuid, playername, ppcountp, playtime, scorecount, failcount) VALUES" +
                "('" + player.getUniqueId().toString() + "', '"+ player.getName() +"' , 0.0, 0.0, 0, 0);");

            instance.playerManager.createPlayerData(player);
            instance.playerManager.calculateRanking(player);

            fw.setValue( "hasCourse", false);
            playerObject.getPlayerStatus().setBuildCourse(false);

            Timer t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {

                    //helpmessage
                    instance.titelManager.sendTitle(player, "", "Help: /pphelp", 60);

                    t.cancel();

                }

            }, 5000, 1);

            for(Player all : Bukkit.getOnlinePlayers()) instance.tabTagCreator.updateRank(all);

            Bukkit.getConsoleSender().sendMessage("New Player created!");
            return;

        }

        //also sets scoreboard
        instance.playerManager.getPlayerData(player);

        player.teleport(location);
        event.setJoinMessage(Message.MSG_Join.getMessage().replace("{Player}", player.getName()));
        instance.playerManager.calculateRanking(player);
        instance.scoreboardHelper.initializeScoreboard(player);
        playerObject.getPlayerStatus().setBuildCourse(fw.getBoolean("hasCourse"));

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {

                PlayerPlayStats playerPlayStats = playerObject.getPlayerPlayStats();

                instance.scoreboardHelper.updatePPScoreOnScoreBoard(player, playerPlayStats.getPpcount());
                instance.scoreboardHelper.updateFailsOnScoreBoard(player, playerPlayStats.getFailscount());
                instance.scoreboardHelper.updatePlaytimeOnScoreBoard(player, playerPlayStats.getPlaytime());
                instance.scoreboardHelper.updateRankOnScoreBoard(player, playerPlayStats.getRank());

                for(Player all : Bukkit.getOnlinePlayers()) instance.tabTagCreator.updateRank(all);

                timer.cancel();

            }

        }, 1500, 1);

        Bukkit.getConsoleSender().sendMessage("Existing Player loaded!");

    }

}
