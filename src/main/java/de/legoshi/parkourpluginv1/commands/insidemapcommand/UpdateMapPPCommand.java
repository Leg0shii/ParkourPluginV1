package de.legoshi.parkourpluginv1.commands.insidemapcommand;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.AsyncMySQL;
import de.legoshi.parkourpluginv1.util.Message;
import de.legoshi.parkourpluginv1.util.mapinformation.MapObject;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public class UpdateMapPPCommand implements CommandExecutor {

			@Override
			public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

						if(!(sender instanceof Player)) return false;
						if(!sender.getName().equals("Leg0shi_")) {
									sender.sendMessage(Message.ERR_NoPermission.getMessage());
									return false;
						}

						Player player = ((Player) sender).getPlayer();
						Main instance = Main.getInstance();
						AsyncMySQL mySQL = instance.mySQL;
						PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);
						MapObject mapObject = playerObject.getPlayerMap().getMapObject();

						int mapid = mapObject.getID();

						mySQL.query("SELECT * FROM clears WHERE mapid = " + mapid + " AND cleared = 1;", new Consumer<ResultSet>() {

									@Override
									public void accept(ResultSet resultSet) {

												try {

															if(resultSet.next()) {

																		do {

																					double fails = resultSet.getDouble("pfails");
																					double time = resultSet.getDouble("ptime");
																					double diff = mapObject.getMapJudges().getDifficulty();
																					double cp = mapObject.getMapJudges().getCpcount();
																					double acc;
																					double currentPP;
																					String uuid = resultSet.getString("playeruuid");

																					if(mapObject.getMapMetaData().getMapType().equals("speedrun")) {
																								acc = instance.performanceCalculator.calcSpeedAcc(mapObject, fails, time);
																								currentPP = instance.performanceCalculator.calcSpeedPP(acc, diff, mapObject.getMapJudges().getMinTime());
																								mySQL.update("UPDATE clears SET ppcountc = " + currentPP + " WHERE playeruuid = '" + uuid + "' AND mapid = "+mapid+";");
																								mySQL.update("UPDATE clears SET accuracy = " + acc + " WHERE playeruuid = '" + uuid + "' AND mapid = "+mapid+";");

																					} else if (mapObject.getMapMetaData().getMapType().equals("hallway")) {
																								acc = instance.performanceCalculator.calcHallwayAcc(mapObject, fails, time);
																								currentPP = instance.performanceCalculator.calcHallwayPP(acc, diff, cp);
																								mySQL.update("UPDATE clears SET ppcountc = " + currentPP + " WHERE playeruuid = '" + uuid + "' AND mapid = "+mapid+";");
																								mySQL.update("UPDATE clears SET accuracy = " + acc + " WHERE playeruuid = '" + uuid + "' AND mapid = "+mapid+";");

																					}

																		} while(resultSet.next());

																		player.sendMessage("Done with updating clears.");
																		updateAllScores();

															} else {

																		player.sendMessage("No players to convert");

															}

												} catch (SQLException throwables) { throwables.printStackTrace(); }

									}

						});

						return false;

			}

			public void updateAllScores() {

						AsyncMySQL mySQL = Main.getInstance().mySQL;

						mySQL.query("SELECT playername FROM tablename", new Consumer<ResultSet>() {

									@Override
									public void accept(ResultSet resultSet) {

												try {
															if(resultSet.next()) {

																		do {

																					String playername = resultSet.getString("playername");

																					mySQL.query("SELECT ppcountc FROM clears WHERE playername = '" + resultSet.getString("playername") + "' ORDER BY ppcountc DESC;", new Consumer<ResultSet>() {

																								@Override
																								public void accept(ResultSet resultSet) {

																											try {
																														if(resultSet.next()) {

																																	int index = 0;
																																	double totalPP = 0;

																																	do {

																																				totalPP = resultSet.getDouble("ppcountc") * Math.pow(0.95, index) + totalPP;
																																				index++;

																																	} while(resultSet.next());

																																	mySQL.update("UPDATE tablename SET ppcountp = "+ totalPP +" WHERE playername = '" + playername + "';");
																																	if(Bukkit.getServer().getPlayer(playername) != null) {
																																				Player p1 = Bukkit.getPlayer(playername);
																																				Main.getInstance().playerManager.playerObjectHashMap.get(p1).getPlayerPlayStats().setPpcount(totalPP);
																																	}
																																	Bukkit.getConsoleSender().sendMessage("Player: " + playername + " is Done.");

																														} else {

																																	Bukkit.getConsoleSender().sendMessage("Player: " + playername + " no Scores.");

																														}
																											} catch (SQLException throwables) { throwables.printStackTrace(); }

																								}

																					});

																		} while(resultSet.next());

															}

												} catch (SQLException throwables) { throwables.printStackTrace(); }

									}

						});

			}

}
