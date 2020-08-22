package de.legoshi.parkourpluginv1.commands;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.manager.MapObjectMananger;
import de.legoshi.parkourpluginv1.util.AsyncMySQL;
import de.legoshi.parkourpluginv1.util.FW;
import de.legoshi.parkourpluginv1.util.MapObject;
import de.legoshi.parkourpluginv1.util.PlayerObject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;

public class SetMapNameCommand implements CommandExecutor {

			@Override
			public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

						if (!(sender instanceof Player)) return false;
						Player player = ((Player) sender).getPlayer();
						Main instance = Main.getInstance();
						PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);
						FW file = new FW("./ParkourBuild", player.getUniqueId().toString() + ".yml");
						AsyncMySQL mySQL = instance.mySQL;

						if(!(playerObject.isBuildCourse())) {

									player.sendMessage("You dont have a Map!");
									return false;

						}

						String mapname = file.getString("mapname");
						if (!(player.getWorld().getName().equals(mapname))) {

									player.sendMessage("You are not inside your Map!");
									return false;

						}

						if(!(args.length == 1)) {

									player.sendMessage("enter /setmapname <mapname> to set name of map");
									return false;

						}

						int mapid = Integer.parseInt(mapname);

						mySQL.update("UPDATE maps SET mapname = '" + args[0] + "' WHERE mapid = " + mapid);

						MapObjectMananger mapObjectMananger = instance.mapObjectMananger;
						ArrayList<MapObject> mapObjectArrayList = mapObjectMananger.getMapObjectArrayList();
						mapObjectArrayList.sort(Comparator.comparing(MapObject::getID));

						int index;

						player.sendMessage("size: " + mapObjectArrayList.size());

						for(index = 0; index < mapObjectArrayList.size(); index++) {

									if(file.getString("mapname").equals(Integer.toString(mapObjectArrayList.get(index).getID()))) {

												Main.instance.mapObjectMananger.getMapObjectArrayList().get(index).setName(args[0]);

									}

						}

						player.sendMessage("Mapname set!");
						return true;

			}

}
