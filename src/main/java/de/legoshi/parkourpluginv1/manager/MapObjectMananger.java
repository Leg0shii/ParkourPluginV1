package de.legoshi.parkourpluginv1.manager;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.AsyncMySQL;
import de.legoshi.parkourpluginv1.util.mapinformation.MapJudges;
import de.legoshi.parkourpluginv1.util.mapinformation.MapMetaData;
import de.legoshi.parkourpluginv1.util.mapinformation.MapObject;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class MapObjectMananger {

			public void firstPlayerMapLoad(Player player, MapObject maps) {

						Main instance = Main.getInstance();
						AsyncMySQL mySQL = instance.mySQL;
						PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);

						mySQL.query("SELECT playeruuid, mapid FROM clears WHERE playeruuid = '" + player.getUniqueId().toString() + "' AND mapid = " + maps.getID() + ";",
								new Consumer<ResultSet>() {

											@Override
											public void accept(ResultSet resultSet) {

														try {

																	if (!(resultSet.next())) {

																				mySQL.update("INSERT INTO clears (playeruuid, playername, mapid, cleared, ppcountc, ptime, pfails, accuracy) VALUES " +
																						"('" + playerObject.getPlayer().getUniqueId().toString() + "', '" + player.getName() + "', '" + maps.getID() + "', false, 0.0, 0, 0.0, 0);");

																	}

														} catch (SQLException throwables) {

																	throwables.printStackTrace();

														}

											}

								});

			}

			public void loadHighestPP(Player player, MapObject mapObject) {

						Main instance = Main.getInstance();
						AsyncMySQL mySQL = instance.mySQL;

						//saves the highestpp of a course into the DB
						mySQL.query("SELECT ppcountc FROM clears WHERE mapID = " + mapObject.getID() + " ORDER BY ppcountc DESC", new Consumer<ResultSet>() {

									@Override
									public void accept(ResultSet resultSet) {

												try {

															if (resultSet.next()) {

																		instance.playerManager.playerObjectHashMap.get(player).getPlayerMap().getMapObject().getMapJudges().setHighestPP(resultSet.getDouble("ppcountc"));

															}

												} catch (SQLException throwables) { throwables.printStackTrace(); }

									}

						});

			}

			public MapObject loadMapFromDB(ResultSet resultSet) throws SQLException {

						Location mapSpawn = new Location(
								null,
								resultSet.getDouble("x"),
								resultSet.getDouble("y"),
								resultSet.getDouble("z"),
								resultSet.getFloat("yaw"),
								resultSet.getFloat("pitch"));
						MapJudges mapJudges = new MapJudges(
								0,
								resultSet.getDouble("difficulty"),
								resultSet.getInt("minFails"),
								resultSet.getDouble("minTime"),
								resultSet.getDouble("prec"),
								resultSet.getInt("cpcount"));
						MapMetaData mapMetaData = new MapMetaData(
								mapSpawn,
								resultSet.getString("mapstatus"),
								resultSet.getString("builder"),
								resultSet.getString("mapname"),
								resultSet.getString("maptype"));
						MapObject mapObject = new MapObject(
								resultSet.getInt("mapid"),
								mapMetaData,
								mapJudges);

						return mapObject;

			}

}

