package de.legoshi.parkourpluginv1.manager;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.AsyncMySQL;
import de.legoshi.parkourpluginv1.util.mapinformation.MapJudges;
import de.legoshi.parkourpluginv1.util.mapinformation.MapMetaData;
import de.legoshi.parkourpluginv1.util.mapinformation.MapObject;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.MapMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class MapObjectMananger {

    ArrayList<MapObject> mapObjectArrayList;

    public MapObjectMananger() {

       this.mapObjectArrayList = new ArrayList<>();

    }

    public ArrayList<MapObject> getMapObjectArrayList() {
        return mapObjectArrayList;
    }

    public void setMapObjectArrayList(ArrayList<MapObject> mapObjectArrayList) {
        this.mapObjectArrayList = mapObjectArrayList;
    }

    public void getAllMapsFromDB() {

        AsyncMySQL mySQL = Main.getInstance().mySQL;
        ArrayList<MapObject> mapObjectList = new ArrayList<>();

        mySQL.query("SELECT * FROM maps ORDER BY difficulty", new Consumer<ResultSet>() {

            @Override
            public void accept(ResultSet resultSet) {

                while (true) {
                    try {
                        if (!resultSet.next()) {

                            Main.getInstance().mapObjectMananger.setMapObjectArrayList(mapObjectList);
                            return;
                        }

                        //loads all MapObjects from the DB into the MapObjectManager
                        World world = Bukkit.getWorld(resultSet.getString("world"));
                        Location mapSpawn = new Location(
                            world,
                            resultSet.getDouble("x"),
                            resultSet.getDouble("y"),
                            resultSet.getDouble("z")
                        );

                        MapJudges mapJudges = new MapJudges(
                            0,
                            resultSet.getDouble("difficulty"),
                            resultSet.getInt("minFails"),
                            resultSet.getDouble("minTime"));
                        MapMetaData mapMetaData = new MapMetaData(
                            mapSpawn,
                            resultSet.getString("mapstatus"),
                            resultSet.getString("builder"),
                            resultSet.getString("mapname"),
                            resultSet.getString("maptype")
                        );
                        MapObject mapObject = new MapObject(
                            resultSet.getInt("mapid"),
                            mapMetaData,
                            mapJudges
                        );

                        //saves the highestpp of a course into the DB
                        mySQL.query("SELECT ppcountc FROM clears WHERE mapID = "+ mapObject.getID() +" ORDER BY ppcountc DESC", new Consumer<ResultSet>() {

                            @Override
                            public void accept(ResultSet resultSet) {

                                try {

                                    if(resultSet.next()) {

                                        mapObject.getMapJudges().setHighestPP(resultSet.getDouble("ppcountc"));
                                        Bukkit.getConsoleSender().sendMessage("Map: " + mapObject.getID() + " PP: " + mapObject.getMapJudges().getHighestPP());

                                    }

                                } catch (SQLException throwables) { throwables.printStackTrace(); }

                                mapObjectList.add(mapObject);

                            }

                        });

                    } catch (SQLException throwables) { throwables.printStackTrace(); }

                }

            }

        });

        Bukkit.getConsoleSender().sendMessage("Loaded all Maps");

    }

    public void firstPlayerMapLoad(Player player, MapObject maps) {

        Main instance = Main.getInstance();
        AsyncMySQL mySQL = instance.mySQL;
        PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);

        mySQL.query("SELECT playeruuid, mapid FROM clears WHERE playeruuid = '" + player.getUniqueId().toString() + "' AND mapid = " + maps.getID() + ";",
            new Consumer<ResultSet>() {

                @Override
                public void accept(ResultSet resultSet) {

                    try {

                        if(!(resultSet.next())) {

                            mySQL.update("INSERT INTO clears (playeruuid, playername, mapid, cleared, ppcountc, ptime, pfails) VALUES " +
                                "('"+playerObject.getPlayer().getUniqueId().toString()+"', '"+ player.getName() +"', '"+maps.getID()+"', false, 0.0, 0, 0.0);");

                        }

                    } catch (SQLException throwables) {

                        throwables.printStackTrace();

                    }

                }

            });

    }

}

