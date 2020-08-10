package de.legoshi.parkourpluginv1.listener;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.manager.CheckpointManager;
import de.legoshi.parkourpluginv1.util.*;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;

public class PlayerStepPressureplate {

    public void onPressureplatStep(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Main instance = Main.getInstance();
        PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);

        if (playerObject.isJumpmode() && event.getAction() == Action.PHYSICAL) {

            CheckpointObject checkpointObject = instance.checkpointManager.checkpointObjectHashMap.get(player);
            boolean isJumpmode = playerObject.isJumpmode();
            AsyncMySQL mySQL = instance.mySQL;
            Material checkPress = event.getClickedBlock().getType();

            World world = Bukkit.getWorld("world");
            Location location = new Location(world, -616, 4, 9);


            //activates when player steps on CP
            if (checkPress.equals(Material.IRON_PLATE) && isJumpmode) { setCheckpoint(event); }

            //activates when player steps on Goal
            if (event.getClickedBlock().getType().equals(Material.GOLD_PLATE)) {

                double ppFromMap;
                int failsGained;
                double timeGained;

                //player jumpmode to false and tp back to spawn
                playerObject.setJumpmode(false);
                checkpointObject.setLocation(new Location(player.getWorld(), 0, 0, 0));

                //player.sendMessage("You got teleported! From: " + player.getDisplayName());
                player.teleport(location);
                instance.inventory.createSpawnInventory(player);

                //calculates pp and saves player that played the map
                ppFromMap = calculatePPFromMap(playerObject);
                failsGained = playerObject.getFailsrelative();
                timeGained = playerObject.getTimerelative();

                //prints resultscreen
                showResultScreen(player, ppFromMap, failsGained, timeGained);

                //setting Updates into PlayerObject
                playerObject.setFailscount(failsGained + playerObject.getFailscount());

                //gets informations from clears list
                mySQL.query("SELECT ppcountc FROM clears " +
                    "WHERE mapid = " + playerObject.getMapObject().getID() + " AND playeruuid = '" + playerObject.getUuid() + "';", new Consumer<ResultSet>() {

                    @Override
                    public void accept(ResultSet resultSet) {

                        ArrayList<Double> ppList;
                        UUID playerUUID;
                        int mapID;

                        try {

                            if (resultSet.next()) {

                                if (ppFromMap > resultSet.getDouble("ppcountc")) {

                                    playerUUID = player.getUniqueId();
                                    mapID = playerObject.getMapObject().getID();

                                    instance.mySQLManager.saveClearToDB(failsGained, timeGained, ppFromMap, playerUUID, mapID);
                                    anncounceFastestTime(playerObject.getMapObject(), player, ppFromMap);
                                    setNewHighestPPScoreOnMap(playerObject.getMapObject(), ppFromMap);

                                    ppList = instance.playerManager.updatePPScoreList(playerObject, ppFromMap);
                                    instance.playerManager.updatePlayerPPScore(playerObject, ppList, ppFromMap);

                                    mySQL.update("UPDATE tablename SET ppcountp = " + playerObject.getPpcount() + " WHERE playeruuid = '" + player.getUniqueId() + "';");

                                    instance.playerManager.updateRankOfAllOnlinePlayer();
                                    instance.scoreboardHelper.updatePPScoreOnScoreBoard(player, playerObject.getPpcount());

                                }

                            }

                        } catch (SQLException throwables) { throwables.printStackTrace(); }

                    }

                });

            }

        }

    }

    public void anncounceFastestTime(MapObject map, Player player, double ppGained) {

        if (ppGained > map.getHighestPP()) {

            for(Player all : Bukkit.getOnlinePlayers()) {

                all.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "!" + ChatColor.RESET
                    + Message.MSG_ANNOUNCEMENT_FAST.getRawMessage().replace("{player}", player.getDisplayName())
                    .replace("{mapname}", map.getName())
                    .replace("{ppscore}", String.format("%.1f", ppGained)));

            }

        }

    }

    public void setNewHighestPPScoreOnMap(MapObject map, double ppGained) {

        Main instance = Main.getInstance();
        ArrayList<MapObject> mapObjectArrayList = instance.mapObjectMananger.getMapObjectArrayList();

        mapObjectArrayList.sort(Comparator.comparing(MapObject::getID));
        mapObjectArrayList.get(map.getID()-1).setHighestPP(ppGained);

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

    public double calculatePPFromMap(PlayerObject playerObject) {

        double minFails = playerObject.getMapObject().getMinFails();
        double minTime = playerObject.getMapObject().getMinTime();
        double difficulty = playerObject.getMapObject().getDifficulty();

        int failsGained = playerObject.getFailsrelative();
        double timeGained = playerObject.getTimerelative();

        double timeMultiplier = ( (minTime+1) / (timeGained+1) );
        double failsMultiplier = ( (minFails+1) / (failsGained+1) );

        String ppGainedString = String.format("%.2f", (difficulty * timeMultiplier * failsMultiplier)).replace(",", ".");

        return Double.parseDouble(ppGainedString);

    }

    public void showResultScreen(Player player, double ppGained, double failsGained, double timeGained) {

        player.sendMessage(Message.Prefix.getRawMessage() + "You beat the Course! Here is your Result: ");
        player.sendMessage(Message.Prefix.getRawMessage() + "PP-Score: " + ppGained);
        player.sendMessage(Message.Prefix.getRawMessage() + "Fails: " + failsGained);
        player.sendMessage(Message.Prefix.getRawMessage() + "Course beaten in: " + String.format("%.3f",timeGained) + "s");

    }

}
