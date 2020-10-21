package de.legoshi.parkourpluginv1.commands.insidemapcommand;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.AsyncMySQL;
import de.legoshi.parkourpluginv1.util.FW;
import de.legoshi.parkourpluginv1.util.Message;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawn implements CommandExecutor {

			@Override
			public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

						if(!(sender instanceof Player)) {

									sender.sendMessage(Message.ERR_NoPlayer.getMessage());
									return false;

						}

						Player player = ((Player) sender).getPlayer();
						FW fw = new FW("./ParkourBuild", player.getUniqueId().toString() + ".yml");

						player.sendMessage("" + fw.getBoolean("hasCourse"));
						player.sendMessage("" + fw.getString("mapname"));
						player.sendMessage("" + player.getWorld().getName());

						if(!(fw.getBoolean("hasCourse") && player.getWorld().getName().equals(fw.getString("mapname")))) {

									sender.sendMessage(Message.ERR_NoPermission.getMessage());
									return false;

						}

						Main instance = Main.getInstance();
						AsyncMySQL mySQL = instance.mySQL;
						int mapid = Integer.parseInt(fw.getString("mapname"));
						Location location = player.getLocation();
						double x = location.getX();
						double y = location.getY();
						double z = location.getZ();
						double yaw = location.getYaw();
						double pitch = location.getPitch();

						instance.checkpointManager.checkpointObjectHashMap.get(player).setLocation(player.getLocation());
						mySQL.update("UPDATE maps SET x = '"+x+"' WHERE mapid = " + mapid);
						mySQL.update("UPDATE maps SET y = '"+y+"' WHERE mapid = " + mapid);
						mySQL.update("UPDATE maps SET z = '"+z+"' WHERE mapid = " + mapid);
						mySQL.update("UPDATE maps SET yaw = '"+yaw+"' WHERE mapid = " + mapid);
						mySQL.update("UPDATE maps SET pitch = '"+pitch+"' WHERE mapid = " + mapid);
						player.sendMessage(Message.MSG_SUCC_SET_SPAWN.getMessage());

						return false;

			}

}
