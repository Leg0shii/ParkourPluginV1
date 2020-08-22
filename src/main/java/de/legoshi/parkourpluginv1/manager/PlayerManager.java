package de.legoshi.parkourpluginv1.manager;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.*;
import de.legoshi.parkourpluginv1.util.mapinformation.MapJudges;
import de.legoshi.parkourpluginv1.util.mapinformation.MapMetaData;
import de.legoshi.parkourpluginv1.util.mapinformation.MapObject;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerPlayStats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;

public class PlayerManager {

    public HashMap<Player, PlayerObject> playerObjectHashMap;

    public PlayerManager() {

        this.playerObjectHashMap = new HashMap<>();

    }

    public void getPlayerData(Player player) {

        Main instance = Main.getInstance();
        AsyncMySQL mySQL = instance.mySQL;
        PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);
        PlayerPlayStats playerPlayStats = playerObject.getPlayerPlayStats();
        FW file = new FW("./ParkourBuild", player.getUniqueId().toString() + ".yml");
        if(file.getBoolean("hascourse")) playerObject.getPlayerStatus().setBuildCourse(true);

        mySQL.query("SELECT * FROM tablename WHERE playeruuid = '"+player.getUniqueId()+"';", new Consumer<ResultSet>() {

            @Override
            public void accept(ResultSet resultSet) {

                try {

                    if(resultSet.next()) {

                        double pp = resultSet.getDouble("ppcountp");
                        //int score = resultSet.getInt("scorecount");
                        long playtime = resultSet.getLong("playtime");
                        int fails = resultSet.getInt("failcount");

                        playerPlayStats.setPpcount(pp);
                        //playerObject.setScorecount(score);
                        playerPlayStats.setPlaytime(playtime);
                        playerPlayStats.setFailscount(fails);

                        Bukkit.getConsoleSender().sendMessage("Loaded Playerdata from " + player.getName());

                    }

                } catch (SQLException throwables) { throwables.printStackTrace(); }

            }

        });

        mySQL.query("SELECT ppcountc, mapid FROM clears WHERE playeruuid = '" + player.getUniqueId() + "' ORDER BY ppcountc DESC;", new Consumer<ResultSet>() {

            @Override
            public void accept(ResultSet resultSet) {

                try {
                    if(resultSet.next()) {

                        ArrayList<PPMapObject> ppMapObjects = new ArrayList<>();

                        do {

                            int id = resultSet.getInt("mapid");
                            double pp = resultSet.getDouble("ppcountc");
                            MapJudges mapJudges = new MapJudges();
                            MapMetaData mapMetaData = new MapMetaData();
                            MapObject mapObject = new MapObject(id, mapMetaData, mapJudges);
                            PPMapObject ppMapObject = new PPMapObject(pp, mapObject);
                            ppMapObjects.add(ppMapObject);

                        }

                        while (resultSet.next());

                        instance.playerManager.playerObjectHashMap.get(player).getPlayerPlayStats().setPpScoreList(ppMapObjects);

                    }

                } catch (SQLException throwables) { throwables.printStackTrace(); }

            }

        });

    }

    public void calculateRanking(Player player) {

        Main instance = Main.getInstance();
        AsyncMySQL mySQL = instance.mySQL;
        PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);
        PlayerPlayStats playerPlayStats = playerObject.getPlayerPlayStats();

        mySQL.query("SELECT * FROM tablename ORDER BY ppcountp DESC", new Consumer<ResultSet>() {

            @Override
            public void accept(ResultSet resultSet) {

                try {
                    if(resultSet.next()) {

                        playerPlayStats.setRank(1);
                        int index = 1;

                        for(; resultSet.next(); index++) {

                            if(resultSet.getString("playeruuid").equals(player.getUniqueId().toString())) {

                                playerPlayStats.setRank(index+1);

                            }

                        }

                    }
                } catch (SQLException throwables) { throwables.printStackTrace(); }

            }

        });

    }

    public void createPlayerData(Player player) {

        Main instance = Main.getInstance();
        PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);
        PlayerPlayStats playerPlayStats = playerObject.getPlayerPlayStats();

        playerPlayStats.setRank(0);
        playerPlayStats.setFailscount(0);
        playerPlayStats.setPpcount(0);
        playerPlayStats.setPlaytime(0);
        instance.scoreboardHelper.initializeScoreboard(player);

    }

    public void updatePlayerPPScore(PlayerObject playerObject, ArrayList<Double> ppList, double ppFromMap) {

        double totalPP = 0;
        int index = 0;
        Main instance = Main.getInstance();

        if(ppList.size() == 0) { totalPP = ppFromMap; }

        while (index < ppList.size()) {

            totalPP = ppList.get(index) * Math.pow(0.95, index) + totalPP;
            index++;

        }

        playerObject.getPlayerPlayStats().setPpcount(totalPP);
        instance.scoreboardHelper.updatePPScoreOnScoreBoard(playerObject.getPlayer(), totalPP);

    }

    public void updatePlaytimeOfPlayer(PlayerObject playerObject) {

        PlayerPlayStats playerPlayStats = playerObject.getPlayerPlayStats();
        long systemTimeNow = new Date().getTime();
        long systemTimeLastUpdate = playerPlayStats.getPlaytimeSave();
        long playTime = playerPlayStats.getPlaytime();
        long newPlayTime = (systemTimeNow - systemTimeLastUpdate) + playTime;
        Main instance = Main.getInstance();

        playerPlayStats.setPlaytime(newPlayTime);
        playerPlayStats.setPlaytimeSave(systemTimeNow);

        instance.scoreboardHelper.updatePlaytimeOnScoreBoard(playerObject.getPlayer(), newPlayTime);

    }

    public void updateRankOfPlayer(Player player) {

        Main instance = Main.getInstance();
        PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);
        instance.playerManager.calculateRanking(player);
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {

                instance.scoreboardHelper.updateRankOnScoreBoard(player, playerObject.getPlayerPlayStats().getRank());
                timer.cancel();

            }

        }, 1000, 1);

    }

    public void updateRankOfAllOnlinePlayer() {

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {

                for(Player all : Bukkit.getOnlinePlayers()) {

                    updateRankOfPlayer(all);

                }

                timer.cancel();

            }

        }, 1000, 1);

    }

    public ArrayList<Double> updatePPScoreList(PlayerObject playerObject, double ppGained) {

        boolean checking = true;
        ArrayList<Double> ppList = new ArrayList<>();
        ArrayList<PPMapObject> ppMapObjectArrayList = playerObject.getPlayerPlayStats().getPpScoreList();
        int k = 0;
        for(; k < ppMapObjectArrayList.size(); k++) {

            if(ppMapObjectArrayList.get(k).getMapObject().getID() == playerObject.getPlayerMap().getMapObject().getID()) {

                ppMapObjectArrayList.get(k).setPp(ppGained);
                checking = false;

            }

            ppList.add(ppMapObjectArrayList.get(k).getPp());

        }

        if(checking) {

            ppMapObjectArrayList.add(new PPMapObject(ppGained, playerObject.getPlayerMap().getMapObject()));
            ppList.add(ppGained);

        }

        Collections.sort(ppList);
        Collections.reverse(ppList);

        return ppList;

    }

}
