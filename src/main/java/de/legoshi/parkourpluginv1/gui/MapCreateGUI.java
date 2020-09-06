package de.legoshi.parkourpluginv1.gui;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.AsyncMySQL;
import de.legoshi.parkourpluginv1.util.FW;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerStatus;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public class MapCreateGUI {

			public InventoryGui guiCreateMap(Player player) {

						String[] guiSetup = {"    i    "};
						String guiTitle = "Slot Select";

						Main instance = Main.getInstance();
						AsyncMySQL mySQL = instance.mySQL;

						FW fw = new FW("./ParkourBuild", player.getUniqueId().toString() + ".yml");

						InventoryGui gui = new InventoryGui(instance, player, guiTitle, guiSetup);
						gui.setFiller(new ItemStack(Material.STAINED_GLASS_PANE, 1));

						gui.addElement(new StaticGuiElement('i',
								new ItemStack(Material.WOOL),
								click -> {

											player.closeInventory();

											PlayerStatus playerStatus = instance.playerManager.playerObjectHashMap.get(player).getPlayerStatus();
											playerStatus.setBuildCourse(true);
											playerStatus.setBuildmode(true);

											fw.setValue("hasCourse", true);
											fw.save();

											mySQL.query("SELECT mapid FROM maps ORDER BY mapid DESC", new Consumer<ResultSet>() {

														@Override
														public void accept(ResultSet resultSet) {

																	try {

																				int mapid;
																				if (resultSet.next()) {
																							mapid = (resultSet.getInt("mapid") + 1);
																				} else {
																							mapid = 1;
																				}

																				World newWorld = instance.worldSaver.createNewWorld(String.valueOf(mapid)); //creates world

																				//saves new map into mapfile of player
																				fw.setValue("mapname", mapid);
																				fw.save();

																				//inserting map into database
																				String buildername = player.getName();
																				String worldName = String.valueOf(mapid);
																				Location spawn = instance.standardSpawn;
																				spawn.setWorld(newWorld);
																				double x = spawn.getX();
																				double y = spawn.getY();
																				double z = spawn.getZ();

																				mySQL.update("INSERT INTO maps (mapname, mapstatus, builder, maptype, difficulty, minfails, mintime, x, y, z, world) VALUES " +
																						"('-','wip' ,'" + buildername + "' ,'' ,0 ,0 ,0 ,"+x+" ,"+y+" ,"+z+" , '" + worldName + "');");

																				instance.checkpointManager.checkpointObjectHashMap.get(player).setLocation(spawn); //sets cp

																				player.teleport(spawn); //tps player to map
																				player.sendMessage(ChatColor.RED + "New World Created");
																				instance.inventory.builderInventory(player);
																				player.setGameMode(GameMode.CREATIVE);

																	} catch (SQLException | IOException throwables) {
																				throwables.printStackTrace();
																	}

														}

											});

											return true; // returning true will cancel the click event and stop taking the item

								},

								"Empty Slot",
								"Click to generate a new Buildworld!"
						));

						return gui;

			}

}
