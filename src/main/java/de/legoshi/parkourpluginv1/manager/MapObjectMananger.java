package de.legoshi.parkourpluginv1.manager;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.AsyncMySQL;
import de.legoshi.parkourpluginv1.util.MapObject;
import de.legoshi.parkourpluginv1.util.PlayerObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

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

                        Location l = new Location(world, resultSet.getDouble("x"),
                            resultSet.getDouble("y"),
                            resultSet.getDouble("z"));

                        MapObject mo = new MapObject(resultSet.getString("mapname"),
                            resultSet.getInt("mapid"),
                            resultSet.getDouble("difficulty"),
                            0,
                            resultSet.getInt("minFails"),
                            resultSet.getDouble("minTime"),
                            l,
                            resultSet.getString("maptype"));

                        //saves the highestpp of a course into the DB
                        mySQL.query("SELECT ppcountc FROM clears WHERE mapID = "+ mo.getID() +" ORDER BY ppcountc DESC", new Consumer<ResultSet>() {

                            @Override
                            public void accept(ResultSet resultSet) {

                                try {

                                    if(resultSet.next()) {

                                        mo.setHighestPP(resultSet.getDouble("ppcountc"));
                                        Bukkit.getConsoleSender().sendMessage("Map: " + mo.getID() + " PP: " + mo.getHighestPP());

                                    }

                                } catch (SQLException throwables) { throwables.printStackTrace(); }

                                mapObjectList.add(mo);

                            }

                        });

                    } catch (SQLException throwables) { throwables.printStackTrace(); }

                }

            }

        });

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
                                "('"+playerObject.getUuid()+"', '"+ player.getName() +"', '"+maps.getID()+"', false, 0.0, 0, 0.0);");

                        }

                    } catch (SQLException throwables) {

                        throwables.printStackTrace();

                    }

                }

            });


    }

}

