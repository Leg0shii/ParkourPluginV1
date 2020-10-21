package de.legoshi.parkourpluginv1.commands;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.manager.MapObjectMananger;
import de.legoshi.parkourpluginv1.util.AsyncMySQL;
import de.legoshi.parkourpluginv1.util.FW;
import de.legoshi.parkourpluginv1.util.Message;
import de.legoshi.parkourpluginv1.util.mapinformation.MapObject;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;

public class PPMapCommand implements CommandExecutor {

			public Player player;

			@Override
			public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

						if(!(sender instanceof Player)) return false;
						this.player = ((Player) sender).getPlayer();

						String mapidS;
						int mapid;

						FW fw = new FW("./ParkourBuild", player.getUniqueId().toString() + ".yml");
						String playermapidS = fw.getString("mapname");
						int playermapid;

						if(!(args.length == 4)) {

									player.sendMessage(Message.ERR_PPMAP.getMessage());
									return false;

						}

						mapidS = args[1];

						try {

									mapid = Integer.parseInt(mapidS);

						} catch (NumberFormatException ignored) {

									player.sendMessage(Message.ERR_NOTANUMBER.getMessage());
									return false;

						}

						try {

									playermapid = Integer.parseInt(playermapidS);

						} catch (NumberFormatException ignored) {

									player.sendMessage(Message.Prefix.getRawMessage() + "You dont have a Map currently");
									return false;

						}

						// ...., spawn

						switch (args[0]) {

									case "name" : //sets mapname
												if(args.length == 3 && playermapid == mapid) { setName(mapid, args[2]); }
												else { player.sendMessage(Message.ERR_NoPermission.getMessage()); }
												return false;

									case "status" : //sets mapstatus
												if(args.length == 3 && player.isOp() && playermapid == mapid) { setStatus(mapid, args[2]); }
												else { player.sendMessage(Message.ERR_NoPermission.getMessage()); }
												return false;

									case "difficulty" : //sets mapdiff
												if(args.length == 3 && player.isOp() && playermapid == mapid) { setDifficulty(player, mapid, args[2]);}
												else { player.sendMessage(Message.ERR_NoPermission.getMessage()); }
												return false;

									default :
												return false;

						}

			}

			public void setName(int mapid, String newName) {

						AsyncMySQL mySQL = Main.getInstance().mySQL;
						mySQL.update("UPDATE maps SET mapname = '"+newName+"' WHERE mapid = " + mapid);
						player.sendMessage(Message.MSG_PPMAP_SET_NAME.getMessage().replace("{mapname}", newName));

			}

			public void setStatus(int mapid, String newStatus) {

						AsyncMySQL mySQL = Main.getInstance().mySQL;
						mySQL.update("UPDATE maps SET mapstatus = '"+newStatus+"' WHERE mapid = " + mapid);
						player.sendMessage(Message.MSG_PPMAP_SET_STATUS.getMessage().replace("{mapstatus}", newStatus));
						File file = new File("./ParkourReplays/"+mapid);
						file.mkdir();

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
						player.sendMessage(Message.MSG_PPMAP_SET_DIFFICULTY.getMessage().replace("{difficulty}", newDiff));

			}

}
