package de.legoshi.parkourpluginv1.util;
import de.legoshi.parkourpluginv1.Main;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.zeroturnaround.zip.ZipUtil;

import java.io.*;

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

			public void moveAllPlayer(World world) {

						for(Player all : Bukkit.getOnlinePlayers()) {

									if(all.getWorld().equals(world)) {

												all.setGameMode(GameMode.ADVENTURE);
												all.teleport(Main.instance.spawn);

									}

						}

			}

}
