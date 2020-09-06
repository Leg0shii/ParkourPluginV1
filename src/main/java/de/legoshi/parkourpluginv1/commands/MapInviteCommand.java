package de.legoshi.parkourpluginv1.commands;

import de.legoshi.parkourpluginv1.util.FW;
import de.legoshi.parkourpluginv1.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class MapInviteCommand implements CommandExecutor {

			@Override
			public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

						if(!(sender instanceof Player)) {

									sender.sendMessage(Message.ERR_NoPlayer.getMessage());
									return false;

						}

						Player player = ((Player) sender).getPlayer();

						if(args.length != 1) {

								player.sendMessage(Message.ERR_INVITE.getMessage());
								return false;

						}

						int index = 0;
						Player invitedPlayer = null;

						for(Player all : Bukkit.getOnlinePlayers()) {

									if(all.getName().equals(args[0])) {

												index++;
												invitedPlayer = all;

									}

						}

						if(index == 0) { //is invited player online

									player.sendMessage(Message.ERR_INVITE_NOT_ONLINE.getMessage().replace("{playername}", args[0]));
									return false;

						}

						if(invitedPlayer.getName().equals(player.getName())) {

									player.sendMessage(Message.ERR_NOT_YOURSELF.getMessage());
									return false;

						}

						if(!(invitedPlayer.getWorld().getName().equals("world"))) { //Is invited player in lobby

									player.sendMessage(Message.ERR_INVITE_NOT_MAINWORLD.getMessage().replace("{playername}", args[0]));
									return false;

						}

						FW fw = new FW("./ParkourBuild", player.getUniqueId().toString() + ".yml");

						if(!player.getWorld().getName().equals(fw.getString("mapname"))) { //is player in map

									player.sendMessage(Message.ERR_INVITE_NOT_MAP.getMessage());
									return false;

						}

						fw.setValue("invited", invitedPlayer.getName());
						fw.save();
						player.sendMessage(Message.MSG_SUCC_INVITE.getMessage().replace("{playername}", invitedPlayer.getName()));
						invitedPlayer.sendMessage(Message.MSG_INVITE_PLAYER.getMessage().replace("{playername}", player.getName()));

						return false;

			}

}
