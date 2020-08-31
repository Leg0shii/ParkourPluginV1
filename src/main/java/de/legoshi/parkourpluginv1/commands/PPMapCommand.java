package de.legoshi.parkourpluginv1.commands;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.manager.MapObjectMananger;
import de.legoshi.parkourpluginv1.util.AsyncMySQL;
import de.legoshi.parkourpluginv1.util.Message;
import de.legoshi.parkourpluginv1.util.mapinformation.MapObject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;

public class PPMapCommand implements CommandExecutor {

			public Player player;

			@Override
			public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

						if(!(sender instanceof Player)) return false;
						this.player = ((Player) sender).getPlayer();
						if(!(args.length >= 2)) {

									player.sendMessage(Message.ERR_PPMAP.getMessage());
									return false;

						}

						String mapidS = args[1];
						int mapid;

						try {

									mapid = Integer.parseInt(mapidS);

						} catch (NumberFormatException ignored) {

									player.sendMessage(Message.ERR_NOTANUMBER.getMessage());
									return false;

						}

						if(!(player.isOp())) {

									player.sendMessage(Message.ERR_NoPermission.getMessage());
									return false;

						}

						// ...., spawn

						switch (args[0]) {

									case "name" : //sets mapname
												if(args.length == 3) { setName(mapid, args[2]); }
												return false;

									case "status" : //sets mapstatus
												if(args.length == 3) { setStatus(mapid, args[2]); }
												return false;

									case "difficulty" : //sets mapdiff
												if(args.length == 3) { setDifficulty(player, mapid, args[2]);}
												return false;

									default :
												return false;

						}

			}

			public void setName(int mapid, String newName) {

						AsyncMySQL mySQL = Main.getInstance().mySQL;
						mySQL.update("UPDATE maps SET mapname = '"+newName+"' WHERE mapid = " + mapid);
						updateMapObject(mapid, newName, "name");
						player.sendMessage(Message.MSG_PPMAP_SET_NAME.getMessage().replace("{mapname}", newName));

			}

			public void setStatus(int mapid, String newStatus) {

						AsyncMySQL mySQL = Main.getInstance().mySQL;
						mySQL.update("UPDATE maps SET mapstatus = '"+newStatus+"' WHERE mapid = " + mapid);
						updateMapObject(mapid, newStatus, "status");
						player.sendMessage(Message.MSG_PPMAP_SET_STATUS.getMessage().replace("{mapstatus}", newStatus));

			}

			public void setDifficulty(Player player, int mapid, String newDiff) {

						double diff;

						try {

									diff = Integer.parseInt(newDiff);

						} catch (NumberFormatException ignored) {

									player.sendMessage(Message.ERR_NOTANUMBER.getMessage());
									return;

						}

						AsyncMySQL mySQL = Main.getInstance().mySQL;
						mySQL.update("UPDATE maps SET difficulty = "+diff+" WHERE mapid = " + mapid);
						updateMapObject(mapid, newDiff, "difficulty");
						player.sendMessage(Message.MSG_PPMAP_SET_DIFFICULTY.getMessage().replace("{difficulty}", newDiff));

			}

			public void updateMapObject(int mapid, String newEntry, String object) {

						Main instance = Main.getInstance();
						MapObjectMananger mapObjectMananger = instance.mapObjectMananger;
						ArrayList<MapObject> mapObjectArrayList = mapObjectMananger.getMapObjectArrayList();
						mapObjectArrayList.sort(Comparator.comparing(MapObject::getID));

						int index;
						for(index = 0; index < mapObjectArrayList.size(); index++) {

									if(String.valueOf(mapid).equals(Integer.toString(mapObjectArrayList.get(index).getID()))) {

												switch (object) {

															case "name" :
																		Main.instance.mapObjectMananger.getMapObjectArrayList().get(index).getMapMetaData().setName(newEntry);
																		return;
															case "status" :
																		Main.instance.mapObjectMananger.getMapObjectArrayList().get(index).getMapMetaData().setMapstatus(newEntry);
																		return;
															case "difficulty":
																		Main.instance.mapObjectMananger.getMapObjectArrayList().get(index).getMapJudges().setDifficulty(Double.parseDouble(newEntry));
																		return;
															default :
																		return;

												}

									}

						}

			}

}
