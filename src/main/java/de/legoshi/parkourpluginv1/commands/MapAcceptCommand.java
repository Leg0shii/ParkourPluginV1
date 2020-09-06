package de.legoshi.parkourpluginv1.commands;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.FW;
import de.legoshi.parkourpluginv1.util.Inventory;
import de.legoshi.parkourpluginv1.util.Message;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MapAcceptCommand implements CommandExecutor {

			@Override
			public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

						if(!(sender instanceof Player)) {

									sender.sendMessage(Message.ERR_NoPlayer.getMessage());
									return false;

						}

						Player player = ((Player) sender).getPlayer();

						if(args.length != 1) {

									player.sendMessage(Message.ERR_ACCEPT.getMessage());
									return false;

						}

						int index = 0;
						Player acceptPlayer = null;

						for(Player all : Bukkit.getOnlinePlayers()) {

									if(all.getName().equals(args[0])) {

												index++;
												acceptPlayer = all;

									}

						}

						if(index == 0) {

									player.sendMessage(Message.ERR_ACCEPT_PLAYER_LEFT.getMessage());
									return false;

						}

						if(!(player.getWorld().getName().equals("world"))) {

									player.sendMessage(Message.ERR_ACCEPT_NOT_LOBBY.getMessage());
									return false;

						}

						if(acceptPlayer.getName().equals(player.getName())) {

									player.sendMessage(Message.ERR_NOT_YOURSELF.getMessage());
									return false;

						}

						Bukkit.getConsoleSender().sendMessage("world: " + acceptPlayer.getWorld().getName());

						if(acceptPlayer.getWorld().getName().equals("world")) {

									player.sendMessage(Message.ERR_ACCEPT_PLAYER_NOT_WORLD.getMessage());
									return false;

						}

						FW fw = new FW("./ParkourBuild", acceptPlayer.getUniqueId().toString() + ".yml");

						if(fw.getString("invited").equals(player.getName())) {

									Main instance = Main.getInstance();
									PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);

									playerObject.getPlayerStatus().setBuildmode(true);

									instance.inventory.builderInventory(player);
									player.teleport(acceptPlayer.getLocation());
									player.setGameMode(GameMode.CREATIVE);

						} else {

									player.sendMessage(Message.ERR_ACCEPT_NOT_INV.getMessage());

						}

						return false;

			}

}
