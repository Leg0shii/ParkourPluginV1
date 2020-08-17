package de.legoshi.parkourpluginv1.manager;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.AsyncMySQL;
import de.legoshi.parkourpluginv1.util.MapObject;
import de.legoshi.parkourpluginv1.util.PPMapObject;
import de.legoshi.parkourpluginv1.util.PlayerObject;
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

        mySQL.query("SELECT * FROM tablename WHERE playeruuid = '"+player.getUniqueId()+"';", new Consumer<ResultSet>() {

            @Override
            public void accept(ResultSet resultSet) {

                try {

                    if(resultSet.next()) {

                        double pp = resultSet.getDouble("ppcountp");
                        //int score = resultSet.getInt("scorecount");
                        long playtime = resultSet.getLong("playtime");
                        int fails = resultSet.getInt("failcount");

                        playerObject.setPpcount(pp);
                        //playerObject.setScorecount(score);
                        playerObject.setPlaytime(playtime);
                        playerObject.setFailscount(fails);

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
                        int id = resultSet.getInt("mapid");
                        double pp = resultSet.getDouble("ppcountc");
                        MapObject mapObject = new MapObject("", id, 0, 0, 0, 0, null, "");
                        PPMapObject ppMapObject = new PPMapObject(pp, mapObject);
                        ppMapObjects.add(ppMapObject);

                        while (resultSet.next()) {

                            id = resultSet.getInt("mapid");
                            pp = resultSet.getDouble("ppcountc");
                            mapObject = new MapObject("", id, 0, 0, 0, 0, null, "");
                            ppMapObject = new PPMapObject(pp, mapObject);
                            ppMapObjects.add(ppMapObject);

                        }

                        instance.playerManager.playerObjectHashMap.get(player).setPpScoreList(ppMapObjects);

                    }

                } catch (SQLException throwables) { throwables.printStackTrace(); }

            }

        });

    }

    public void calculateRanking(Player player) {

        Main instance = Main.getInstance();
        AsyncMySQL mySQL = instance.mySQL;
        PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);

        mySQL.query("SELECT * FROM tablename ORDER BY ppcountp DESC", new Consumer<ResultSet>() {

            @Override
            public void accept(ResultSet resultSet) {

                try {
                    if(resultSet.next()) {

                        playerObject.setRank(1);
                        int index = 1;

                        for(; resultSet.next(); index++) {

                            if(resultSet.getString("playeruuid").equals(player.getUniqueId().toString())) {

                                playerObject.setRank(index+1);

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

        playerObject.setRank(0);
        playerObject.setFailscount(0);
        playerObject.setPpcount(0);
        playerObject.setPlaytime(0);
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

        playerObject.setPpcount(totalPP);
        instance.scoreboardHelper.updatePPScoreOnScoreBoard(playerObject.getPlayer(), totalPP);

    }

    public void updatePlaytimeOfPlayer(PlayerObject playerObject) {

        long systemTimeNow = new Date().getTime();
        long systemTimeLastUpdate = playerObject.getPlaytimeSave();
        long playTime = playerObject.getPlaytime();
        long newPlayTime = (systemTimeNow - systemTimeLastUpdate) + playTime;
        Main instance = Main.getInstance();

        playerObject.setPlaytime(newPlayTime);
        playerObject.setPlaytimeSave(systemTimeNow);

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

                instance.scoreboardHelper.updateRankOnScoreBoard(player, playerObject.getRank());
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
        int k = 0;
        for(; k < playerObject.getPpScoreList().size(); k++) {

            if(playerObject.getPpScoreList().get(k).getMapObject().getID() == playerObject.getMapObject().getID()) {

                playerObject.getPpScoreList().get(k).setPp(ppGained);
                checking = false;

            }

            ppList.add(playerObject.getPpScoreList().get(k).getPp());

        }

        if(checking) {

            playerObject.getPpScoreList().add(new PPMapObject(ppGained, playerObject.getMapObject()));
            ppList.add(ppGained);

        }

        Collections.sort(ppList);
        Collections.reverse(ppList);

        return ppList;

    }

}
