package de.legoshi.parkourpluginv1.manager;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.AsyncMySQL;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerPlayStats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;
import java.util.function.Consumer;

public class MySQLManager {

    AsyncMySQL mySQL;

    public AsyncMySQL initializeTables() {

        mySQL = connectToDB();

        if(mySQL != null) {

            Bukkit.getConsoleSender().sendMessage("DB Verbunden");
            //create table for playerdata
            mySQL.update("CREATE TABLE IF NOT EXISTS tablename (playeruuid VARCHAR(255) NOT NULL" +
                ", playername VARCHAR(255), ppcountp DOUBLE(12, 4), scorecount INT(255), playtime BIGINT(19), failcount INT(255), PRIMARY KEY(playeruuid));");
            //create table for mapdata
            mySQL.update("CREATE TABLE IF NOT EXISTS maps (mapid INT(255) AUTO_INCREMENT" +
                ", mapname VARCHAR(255), maptype VARCHAR(255), builder VARCHAR(255), mapstatus VARCHAR(255), difficulty DOUBLE(12, 4), minFails INT(255), minTime DOUBLE(12, 4)" +
                ", prec DOUBLE(12, 4), cpcount INT(255), x DOUBLE(12, 4), y DOUBLE(12, 4), z DOUBLE(12, 4), yaw DOUBLE(12, 4), pitch DOUBLE(12, 4), world VARCHAR(255), PRIMARY KEY(mapid));");
            //create table for clears
            mySQL.update("CREATE TABLE IF NOT EXISTS clears (feldID INT(255) AUTO_INCREMENT, mapid VARCHAR(255), playeruuid VARCHAR(255), cleared BOOLEAN" +
                ", playername VARCHAR(255), accuracy DOUBLE(12, 4), pfails INT(255), ptime DOUBLE(12, 4), ppcountc DOUBLE(12, 4), PRIMARY KEY(feldID));");

        }

        return mySQL;

    }

    public AsyncMySQL connectToDB() {

        Main instance = Main.getInstance();

        try {

            mySQL = new AsyncMySQL(instance, "51.89.3.40", 3306, "root", "qexGGHZfFzWyKYE", "serverpro_db");
            //mySQL = new AsyncMySQL(instance, "localhost", 3306, "root", "root", "testdb");
            return mySQL;

        } catch (SQLException | ClassNotFoundException throwables) { throwables.printStackTrace(); }

        return null;

    }

    public void savingPlayerDataToDB(Player player) {

        PlayerObject playerObject = Main.getInstance().playerManager.playerObjectHashMap.get(player);
        PlayerPlayStats playerPlayStats = playerObject.getPlayerPlayStats();
        UUID playerUUID = player.getUniqueId();

        mySQL.update("UPDATE tablename SET ppcountp = "+ playerPlayStats.getPpcount() +" WHERE playeruuid = '" + playerUUID + "';");
        mySQL.update("UPDATE tablename SET failcount = "+ playerPlayStats.getFailscount() +" WHERE playeruuid = '" + playerUUID + "';");
        mySQL.update("UPDATE tablename SET playtime = "+
            ((new Date().getTime() - playerPlayStats.getPlaytimeSave()) + playerPlayStats.getPlaytime() +" WHERE playeruuid = '" + playerUUID + "';"));

    }

    public void savingAllPlayerDataToDB() {

        AsyncMySQL mySQL = Main.getInstance().mySQL;

        for(Player all : Bukkit.getOnlinePlayers()) {

            PlayerObject playerObject = Main.getInstance().playerManager.playerObjectHashMap.get(all);
            PlayerPlayStats playerPlayStats = playerObject.getPlayerPlayStats();

            mySQL.update("UPDATE tablename SET ppcountp = " + playerPlayStats.getPpcount() + " WHERE playeruuid = '" + all.getUniqueId() + "';");
            mySQL.update("UPDATE tablename SET failcount = " + playerPlayStats.getFailscount() + " WHERE playeruuid = '" + all.getUniqueId() + "';");
            mySQL.update("UPDATE tablename SET playtime = " +
                (((new Date().getTime() - playerPlayStats.getPlaytimeSave()) + playerPlayStats.getPlaytime()) + " WHERE playeruuid = '" + all.getUniqueId() + "';"));

        }

    }

    public void saveClearToDB(int failsGained, double timeGained, double ppFromMap, UUID playerUUID, int mapID, double acc) {

        mySQL.update("UPDATE clears SET ptime = " + timeGained + " WHERE mapid = " + mapID + " AND playeruuid = '" + playerUUID + "';");
        mySQL.update("UPDATE clears SET pfails = " + failsGained + " WHERE mapid = " + mapID + " AND playeruuid = '" + playerUUID + "';");
        mySQL.update("UPDATE clears SET ppcountc = " + ppFromMap + " WHERE mapid = " + mapID + " AND playeruuid = '" + playerUUID + "';");
        mySQL.update("UPDATE clears SET cleared = true WHERE mapid = " + mapID + " AND playeruuid = '" + playerUUID + "';");
        mySQL.update("UPDATE clears SET accuracy = "+acc+" WHERE mapid = " + mapID + " AND playeruuid = '" + playerUUID + "';");

    }

    public int getPages(ResultSet resultSet) throws SQLException {

        int page = 0;

        if(resultSet.next()) {

            resultSet.last();
            page = (int) Math.ceil((double) resultSet.getRow()/10.0);

        } else {

            return 1;

        }

        resultSet.first();
        return page;

    }

    public void keepMySQLbusy() {

        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {

            @Override
            public void run() {

                Main instance = Main.getInstance();
                AsyncMySQL mySQL = instance.mySQL;

                mySQL.query("SELECT ppcountc FROM clears WHERE playername = 'Leg0shi_';", new Consumer<ResultSet>() {

                    @Override
                    public void accept(ResultSet resultSet) {

                    }

                });

            }

        }, 0, (300*20));

    }

}
