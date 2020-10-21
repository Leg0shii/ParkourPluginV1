package de.legoshi.parkourpluginv1.commands;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.FW;
import de.legoshi.parkourpluginv1.util.Message;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ReplayCommand implements CommandExecutor {

			@Override
			public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

						if(!(sender instanceof Player)) { return false; }

						if(args.length != 2) { return false; }

						Player player = ((Player) sender).getPlayer();
						int mapid;

						try {

									mapid = Integer.parseInt(args[0]);

						} catch (NumberFormatException ignored) {

									sender.sendMessage(Message.ERR_NOTANUMBER.getMessage());
									return false;

						}

						if(!player.getWorld().getName().equals("world")) {

									sender.sendMessage("You are not at spawn");
									return false;

						}

						OfflinePlayer op = Bukkit.getOfflinePlayer(args[1]);
						if (op.hasPlayedBefore()) { UUID uuid = op.getUniqueId(); }
						else {

									sender.sendMessage("Player hasnt played here yet.");
									return false;

						}

						File zippedWorld = new File("./ParkourMapsPlayers/"+mapid+".zip");

						if(!zippedWorld.exists()) {

									player.sendMessage("Map does not exist / or is currently loaded");
									return false;

						}

						FW fwnew = new FW("./ParkourReplays/"+mapid, mapid + "_" + op.getUniqueId().toString() +".yml");
						if(!fwnew.exist()) {

									player.sendMessage("Replay does not exist");
									return false;

						}

						//wait for Worldunzip
						ExecutorService executorService = Executors.newSingleThreadExecutor();
						Future<Integer> task = executorService.submit(() -> {
								ZipUtil.unpack(zippedWorld, new File("./" + zippedWorld.getName().substring(0, zippedWorld.getName().length()-4) + "_" + player.getName()));
								return 1;
						});
						try { task.get(); }
						catch (InterruptedException | ExecutionException e) { e.printStackTrace(); }
						executorService.shutdown();

						Main instance = Main.getInstance();
						World world = Main.getInstance().worldSaver.loadWorld(String.valueOf(mapid) + "_" + player.getName());
						PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);
						playerObject.getPlayerStatus().setReplaymode(true);

						player.sendMessage("Joining Replay...");
						instance.inventory.replayInventory(player);
						Location playerSpawn = fwnew.getLocation(player, 0);
						player.teleport(playerSpawn);

						instance.replay.playReplay(playerObject, op, mapid);

						return false;

			}

}
