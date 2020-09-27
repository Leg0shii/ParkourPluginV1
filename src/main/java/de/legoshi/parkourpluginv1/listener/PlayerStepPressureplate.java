package de.legoshi.parkourpluginv1.listener;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.manager.CheckpointManager;
import de.legoshi.parkourpluginv1.util.*;
import de.legoshi.parkourpluginv1.util.mapinformation.MapJudges;
import de.legoshi.parkourpluginv1.util.mapinformation.MapObject;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerMap;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerPlayStats;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerStatus;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;

public class PlayerStepPressureplate {

    public void onPressureplatStep(PlayerInteractEvent event) throws IOException {

        Player player = event.getPlayer();
        Main instance = Main.getInstance();
        PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);
        PlayerStatus playerStatus = playerObject.getPlayerStatus();
        PlayerPlayStats playerPlayStats = playerObject.getPlayerPlayStats();
        PlayerMap playerMap = playerObject.getPlayerMap();
        boolean isJumpmode = playerStatus.isJumpmode();

        if (isJumpmode && event.getAction() == Action.PHYSICAL) {

            CheckpointObject checkpointObject = instance.checkpointManager.checkpointObjectHashMap.get(player);
            AsyncMySQL mySQL = instance.mySQL;
            Material checkPress = event.getClickedBlock().getType();

            World world = Bukkit.getWorld("world");
            Location location = new Location(world, -619, 5, 10, -160, 5);


            //activates when player steps on CP
            if (checkPress.equals(Material.IRON_PLATE) && isJumpmode) { setCheckpoint(event); }

            //activates when player steps on Goal
            if (event.getClickedBlock().getType().equals(Material.GOLD_PLATE)) {

                World playerWorld = player.getWorld();

                //player jumpmode to false and tp back to spawn
                playerObject.getPlayerStatus().setJumpmode(false);
                checkpointObject.setLocation(new Location(player.getWorld(), 0, 0, 0));

                //player.sendMessage("You got teleported! From: " + player.getDisplayName());
                player.teleport(location);
                instance.inventory.createSpawnInventory(player);
                instance.scoreboardHelper.initializeScoreboard(player);
                instance.scoreboardHelper.setSpawnScoreboardValue(playerObject);

                int i = 0;

                for(Player all : Bukkit.getOnlinePlayers()) { //checks if players are still in the world

                    if(all.getWorld().equals(playerWorld)) {

                        i++;

                    }

                }

                if(i == 0) instance.worldSaver.zipWorld(playerWorld);

                //calculates pp and saves player that played the map
                double diff = playerObject.getPlayerMap().getMapObject().getMapJudges().getDifficulty();
                double cp = playerObject.getPlayerMap().getMapObject().getMapJudges().getCpcount();

                int failsGained = playerMap.getFailsrelative();
                double timeGained = playerMap.getTimeRelative();

                //setting Updates into PlayerObject
                playerPlayStats.setFailscount(failsGained + playerPlayStats.getFailscount());

                //gets informations from clears list
                mySQL.query("SELECT * FROM clears, maps " +
                    "WHERE clears.mapid = maps.mapid " +
                    "AND clears.mapid = " + playerMap.getMapObject().getID() + " " +
                    "AND clears.playeruuid = '" + playerObject.getPlayer().getUniqueId().toString() + "';", new Consumer<ResultSet>() {

                    @Override
                    public void accept(ResultSet resultSet) {

                        ArrayList<Double> ppList;
                        UUID playerUUID;
                        int mapID;

                        try {

                            if (resultSet.next()) {

                                double acc = 0;
                                double ppFromMap = 0;
                                if(playerMap.getMapObject().getMapMetaData().getMapType().equals("speedrun")) {
                                    acc = instance.performanceCalculator.calcSpeedAcc(playerObject);
                                    ppFromMap = instance.performanceCalculator.calcSpeedPP(acc, diff);
                                } else if (playerMap.getMapObject().getMapMetaData().getMapType().equals("hallway")) {
                                    acc = instance.performanceCalculator.calcHallwayAcc(playerObject);
                                    ppFromMap = instance.performanceCalculator.calcHallwayPP(acc, diff, cp);
                                }
                                findMapRank(playerObject, ppFromMap); //calcs maprank of player in that map

                                //prints resultscreen
                                showResultScreen(
                                    acc,
                                    player,
                                    ppFromMap,
                                    resultSet.getDouble("ppcountc"),
                                    failsGained,
                                    resultSet.getInt("pfails"),
                                    timeGained,
                                    resultSet.getDouble("ptime"),
                                    resultSet.getString("mapname"),
                                    resultSet.getDouble("accuracy")
                                    );

                                if (ppFromMap > resultSet.getDouble("ppcountc")) {

                                    playerUUID = player.getUniqueId();
                                    mapID = playerMap.getMapObject().getID();

                                    instance.mySQLManager.saveClearToDB(failsGained, timeGained, ppFromMap, playerUUID, mapID, acc);
                                    anncounceFastestTime(playerMap.getMapObject(), player, ppFromMap);

                                    if(ppFromMap > playerMap.getMapObject().getMapJudges().getHighestPP()) {

                                        playerObject.getPlayerMap().getMapObject().getMapJudges().setHighestPP(ppFromMap);

                                    }

                                    ppList = instance.playerManager.updatePPScoreList(playerObject, ppFromMap);
                                    instance.playerManager.updatePlayerPPScore(playerObject, ppList, ppFromMap);

                                    mySQL.update("UPDATE tablename SET ppcountp = " + playerPlayStats.getPpcount() + " WHERE playeruuid = '" + player.getUniqueId() + "';");

                                    instance.playerManager.updateRankOfAllOnlinePlayer();
                                    instance.scoreboardHelper.updatePPScoreOnScoreBoard(player, playerPlayStats.getPpcount());

                                    Timer timer = new Timer();
                                    timer.scheduleAtFixedRate(new TimerTask() {

                                        @Override
                                        public void run() {

                                                for(Player all : Bukkit.getOnlinePlayers()) instance.tabTagCreator.updateRank(all);

                                            timer.cancel();

                                        }

                                    }, 3000, 1);

                                }

                            }

                        } catch (SQLException throwables) { throwables.printStackTrace(); }

                    }

                });

            }

        }

    }

    public void anncounceFastestTime(MapObject map, Player player, double ppGained) {

        if (ppGained > map.getMapJudges().getHighestPP()) {

            for(Player all : Bukkit.getOnlinePlayers()) {

                all.sendMessage("" + Message.MSG_ANNOUNCEMENT_FAST.getMessage()
                    .replace("{player}", player.getDisplayName())
                    .replace("{mapname}", map.getMapMetaData().getName())
                    .replace("{ppscore}", String.format("%.2f", ppGained)));

            }

        }

    }

    public void setCheckpoint(PlayerInteractEvent event) {

        Location checkpoint = event.getClickedBlock().getLocation();
        Player player = event.getPlayer();
        Main instance = Main.getInstance();
        checkpoint.setX(checkpoint.getX() + 0.5);
        checkpoint.setZ(checkpoint.getZ() + 0.5);
        CheckpointObject checkpointObject = instance.checkpointManager.checkpointObjectHashMap.get(player);
        CheckpointManager checkpointManager = instance.checkpointManager;

        if (!(checkpointObject.getLocation().equals(checkpoint))) {

            //saves Position of Player when stepped on cp
            checkpointManager.checkpointObjectHashMap.get(player).setLocation(checkpoint);
            player.sendMessage(Message.MSG_SetCP.getMessage());

        }

    }

    public void showResultScreen(double acc, Player player, double ppGained, double ppOld,
                                 int failsGained, int failsOld, double timeGained, double timeOld,
                                 String mapname, double oldacc) {

            String prefixPP = "" + ChatColor.GRAY;
            String prefixTime = "" + ChatColor.GRAY;
            String prefixFails = "" + ChatColor.GRAY;
            String prefixAcc = "" + ChatColor.GRAY;
            String rank = Main.getInstance().performanceCalculator.calcMapRank(acc);
            String mrankString = "-" + ChatColor.GRAY;

            if(ppGained >= ppOld) prefixPP = "" + ChatColor.GREEN;
            if(timeGained <= timeOld) prefixTime = "" + ChatColor.GREEN;
            if(failsGained <= failsOld) prefixFails = "" + ChatColor.GREEN;
            if(acc >= oldacc)  prefixAcc = "" + ChatColor.GREEN;

            if(ppGained < ppOld) prefixPP = "" + ChatColor.RED;
            if(timeGained > timeOld) prefixTime = "" + ChatColor.RED;
            if(failsGained > failsOld) prefixFails = "" + ChatColor.RED;
            if(acc < oldacc)  prefixAcc = "" + ChatColor.RED;

            player.sendMessage(Message.MSG_RESULT_HEADER.getRawMessage().replace("{mapname}", mapname));
            player.sendMessage(Message.MSG_RESULT_SCREEN.getRawMessage()
                .replace("{performance}", rank)
                .replace("{rank}", mrankString)
                .replace("{newacc}", (prefixAcc + String.format("%.2f",acc)))
                .replace("{oldacc}", String.format("%.2f", oldacc))
                .replace("{newpp}", (prefixPP + String.format("%.2f",ppGained)))
                .replace("{oldpp}", String.format("%.2f",ppOld))
                .replace("{newTime}", (prefixTime + String.format("%.3f",timeGained)))
                .replace("{oldTime}", String.format("%.3f",timeOld))
                .replace("{newFails}", (prefixFails + failsGained))
                .replace("{oldFails}", Integer.toString(failsOld)));
            player.sendMessage(Message.MSG_RESUT_FOOTER.getRawMessage());

    }

    public void findMapRank(PlayerObject playerObject, double ppGained) {



    }

}
