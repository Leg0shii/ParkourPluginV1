package de.legoshi.parkourpluginv1.util;
import de.legoshi.parkourpluginv1.Main;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.zeroturnaround.zip.ZipUtil;

import java.io.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WorldSaver {

			public void zipWorld(World playerWorld) throws IOException {

						File unzippedWorld = playerWorld.getWorldFolder();
						Bukkit.unloadWorld(playerWorld, true);
						ZipUtil.pack(
								unzippedWorld,
								new File("./ParkourMapsPlayers/"+unzippedWorld.getName()+".zip")
						);

						FileUtils.deleteDirectory(unzippedWorld);

			}

			public int unzipWorld(File zippedWorld) {

						ZipUtil.unpack(
								zippedWorld,
								new File("./" + zippedWorld.getName().substring(0, zippedWorld.getName().length()-4))
						);
						Bukkit.getConsoleSender().sendMessage("New World: " + "./" + zippedWorld.getName().substring(0, zippedWorld.getName().length()-4));
						Bukkit.getConsoleSender().sendMessage("Deleted: " + zippedWorld.delete());

						return 1;

			}

			public void delayedUnzipWorld(File playerW) {

						//wait for Worldunzip
						ExecutorService executorService = Executors.newSingleThreadExecutor();
						Future<Integer> task = executorService.submit(() -> unzipWorld(playerW));
						try {
									task.get();
						} catch (InterruptedException | ExecutionException e) {
									e.printStackTrace();
						}
						executorService.shutdown();

			}

			public void moveAllPlayer(World world) {

						for(Player all : Bukkit.getOnlinePlayers()) {

									if(all.getWorld().equals(world)) {

												all.setGameMode(GameMode.ADVENTURE);
												Main.getInstance().inventory.createSpawnInventory(all);
												Main.getInstance().playerManager.playerObjectHashMap.get(all).getPlayerStatus().setBuildmode(false);
												all.teleport(Main.instance.spawn);

									}

						}

			}

			public World createNewWorld(String name) throws IOException {

						File worldDir = new File("./prw");
						FileUtils.copyDirectory(worldDir, new File(worldDir.getParent(), name));
						//delete uid so file can be loaded
						File uid = new File("./" + name + "/uid.dat");
						uid.delete();

						WorldCreator creator = new WorldCreator(name);

						World world = Bukkit.createWorld(creator);
						world.setGameRuleValue("doDaylightCycle", "false");
						world.setGameRuleValue("doMobSpawning", "false");
						world.setGameRuleValue("doFireTick", "false");
						world.setDifficulty(Difficulty.PEACEFUL);
						world.save();

						return world;

			}

			public World loadWorld(String valueOf) {

						WorldCreator worldCreator = new WorldCreator(valueOf);
						World world = Bukkit.createWorld(worldCreator);

						world.setGameRuleValue("doDaylightCycle", "false");
						world.setGameRuleValue("doMobSpawning", "false");
						world.setGameRuleValue("doFireTick", "false");
						world.setDifficulty(Difficulty.PEACEFUL);
						world.save();

						return world;

			}
}
