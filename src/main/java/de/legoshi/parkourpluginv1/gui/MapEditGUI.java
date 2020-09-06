package de.legoshi.parkourpluginv1.gui;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.AsyncMySQL;
import de.legoshi.parkourpluginv1.util.FW;
import de.legoshi.parkourpluginv1.util.mapinformation.MapObject;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerStatus;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public class MapEditGUI {

			public InventoryGui guiEditMap(Player player) {

						String[] guiSetupInMap = {"  s i t  "};
						String guiTitleInMap = "Map Settings";
						Main instance = Main.getInstance();
						PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);

						InventoryGui guiInMap = new InventoryGui(instance, player, guiTitleInMap, guiSetupInMap);
						guiInMap.setFiller(new ItemStack(Material.STAINED_GLASS_PANE, 1));

						FW fw = new FW("./ParkourBuild", player.getUniqueId().toString() + ".yml");

						guiInMap.addElement(new StaticGuiElement('s',
								new ItemStack(Material.NAME_TAG),
								click1 -> {

											player.sendMessage("/ppmap name <mapid> <yourmapname>");

											player.closeInventory();
											return true;

								}, "MapName",
								"Click here to change the Mapname!"));

						guiInMap.addElement(new StaticGuiElement('t',
								new ItemStack(Material.DIAMOND_SWORD),
								click1 -> {

											player.closeInventory();

											guiUploadMap(player).show(player);
											return true;

								}, "Upload",
								"Upload your course by clicking here!"));

						instance.mySQL.query("SELECT * FROM maps WHERE mapid = " + Integer.parseInt(fw.getString("mapname")) + ";", new Consumer<ResultSet>() {

									@Override
									public void accept(ResultSet resultSet) {

												MapObject mapObject;

												try {
															resultSet.next();
															mapObject = instance.mapObjectMananger.loadMapFromDB(resultSet);
												}
												catch (SQLException throwables) {
															throwables.printStackTrace();
															return;
												}

												guiInMap.addElement(new StaticGuiElement('i',
														new ItemStack(Material.WOOL),
														click1 -> {

																	player.closeInventory();
																	player.sendMessage("Joining your course...");
																	player.setGameMode(GameMode.CREATIVE);

																	File playerW = new File("./ParkourMapsPlayers/" + fw.getString("mapname") + ".zip");

																	instance.worldSaver.unzipWorld(playerW);
																	World world = instance.worldSaver.loadWorld(String.valueOf(mapObject.getID()));

																	Location mapSpawn = mapObject.getMapMetaData().getSpawn();
																	mapSpawn.setWorld(world);
																	player.teleport(mapSpawn);
																	instance.checkpointManager.checkpointObjectHashMap.get(player).setLocation(mapSpawn);
																	playerObject.getPlayerStatus().setBuildmode(true);
																	instance.inventory.builderInventory(player);
																	return true;

														}, "" + mapObject.getMapMetaData().getName(),
														"Mapid: " + mapObject.getID(),
														"Join your course by clicking here!"));

												guiInMap.show(player);

									}

						});

						return guiInMap;

			}

			public InventoryGui guiUploadMap(Player player) {

						String[] guiSetup = {" gg   rr "};
						FW file = new FW("./ParkourBuild", player.getUniqueId().toString() + ".yml");
						Main instance = Main.getInstance();
						PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);
						PlayerStatus playerStatus = playerObject.getPlayerStatus();
						AsyncMySQL mySQL = instance.mySQL;

						InventoryGui gui = new InventoryGui(instance, player, "Upload?", guiSetup);

						gui.addElement(new StaticGuiElement('g',
								new ItemStack(Material.WOOL, 1, (short) 3),
								click -> {

											player.closeInventory();

											playerStatus.setBuildCourse(false);
											int mapid = Integer.parseInt(file.getString("mapname"));
											String mapname = file.getString("mapname");

											mySQL.update("UPDATE maps SET mapstatus = 'unranked' WHERE mapid = " + mapid + ";");
											mySQL.update("UPDATE maps SET world = " + mapname + " WHERE mapid = " + mapid + ";");
											mySQL.update("UPDATE maps SET builder = '" + player.getName() + "' WHERE mapid = " + mapid + ";");

											file.setValue("hasCourse", false);
											file.setValue("mapname", "");
											file.save();

											player.sendMessage("Successfully uploaded Map!");
											return true;

								}, "Uploading Map?",
								"Upload now!"
						));

						gui.addElement(new StaticGuiElement('r',
								new ItemStack(Material.WOOL, 1, (short) 14),
								click -> {

											player.sendMessage("Cancelled Upload Attempt");
											player.closeInventory();
											return true;

								}, "Uploading Map?",
								"Upload now!"
						));

						return gui;

			}

			public InventoryGui guiStatusSelect(Player player) {

						String[] guiSetup = {"  g   r  "};
						InventoryGui gui = new InventoryGui(Main.getInstance(), player, "MapStatus", guiSetup);

						gui.addElement(new StaticGuiElement('g',
								new ItemStack(Material.WOOL, 1, (short) 13),
								click -> {

											player.closeInventory();
											Main.getInstance().mapSelectGUI.guiShowAllCourses(player, "ranked", 0);
											return true;

								}, "" + ChatColor.RESET + ChatColor.GREEN + ChatColor.BOLD + "Ranked Maps"
						));

						gui.addElement(new StaticGuiElement('r',
								new ItemStack(Material.WOOL, 1, (short) 14),
								click -> {

											player.closeInventory();
											Main.getInstance().mapSelectGUI.guiShowAllCourses(player, "unranked", 0);
											return true;

								}, "" + ChatColor.RESET + ChatColor.RED + ChatColor.BOLD + "Unranked Maps"
						));

						return gui;

			}

}
