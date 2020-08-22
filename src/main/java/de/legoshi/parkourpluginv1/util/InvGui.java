package de.legoshi.parkourpluginv1.util;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.manager.MapObjectMananger;
import de.themoep.inventorygui.GuiElementGroup;
import de.themoep.inventorygui.GuiPageElement;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class InvGui {

			public InventoryGui guiEditMap(Player player) {

						String[] guiSetupInMap = {"  s i t  "};
						String guiTitleInMap = "Map Settings";
						Main instance = Main.getInstance();
						PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);

						FW fw = new FW("./ParkourBuild", player.getUniqueId().toString() + ".yml");

						MapObjectMananger mapObjectMananger = instance.mapObjectMananger;
						ArrayList<MapObject> mapObjectArrayList = mapObjectMananger.getMapObjectArrayList();
						mapObjectArrayList.sort(Comparator.comparing(MapObject::getID));
						MapObject mapObject = null;

						for(MapObject maps : mapObjectArrayList) {

									if(fw.getString("mapname").equals(Integer.toString(maps.getID()))) {

												mapObject = maps;

									}

						}
						String mapName = mapObject.getName();
						int mapid = mapObject.getID();

						InventoryGui guiInMap = new InventoryGui(instance, player, guiTitleInMap, guiSetupInMap);
						guiInMap.setFiller(new ItemStack(Material.STAINED_GLASS_PANE, 1));

						guiInMap.addElement(new StaticGuiElement('s',
								new ItemStack(Material.NAME_TAG),
								click1 -> {

											player.sendMessage("/setmapname <name>");

											player.closeInventory();
											return true;

								}, "MapName",
								"Click here to change the Mapname!"));

						guiInMap.addElement(new StaticGuiElement('i',
								new ItemStack(Material.WOOL),
								click1 -> {

											player.sendMessage("Joining your course...");
											WorldCreator worldCreator = new WorldCreator(String.valueOf(mapid));
											World world = worldCreator.createWorld();
											player.teleport(new Location(world, 0, 50, 0));
											playerObject.setBuildmode(true);
											instance.inventory.builderInventory(player);

											player.closeInventory();
											return true;

								}, mapName,
								"Join your course by clicking here!"));

						guiInMap.addElement(new StaticGuiElement('t',
								new ItemStack(Material.DIAMOND_SWORD),
								click1 -> {

											player.closeInventory();

											guiUploadMap(player).show(player);
											return true;

								}, "Upload",
								"Upload your course by clicking here!"));

						return guiInMap;

			}

			public InventoryGui guiCreateMap(Player player) {

						String[] guiSetup = {"    i    "};
						String guiTitle = "Slot Select";

						Main instance = Main.getInstance();
						PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);
						AsyncMySQL mySQL = instance.mySQL;

						FW fw = new FW("./ParkourBuild", player.getUniqueId().toString() + ".yml");

						InventoryGui gui = new InventoryGui(instance, player, guiTitle, guiSetup);
						gui.setFiller(new ItemStack(Material.STAINED_GLASS_PANE, 1));

						gui.addElement(new StaticGuiElement('i',
								new ItemStack(Material.WOOL),
								click -> {

											playerObject.setBuildCourse(true);
											playerObject.setBuildmode(true);
											fw.setValue("hasCourse", true);
											try {
														fw.save();
											} catch (IOException e) {
														e.printStackTrace();
											}

											mySQL.query("SELECT mapid FROM maps ORDER BY mapid DESC", new Consumer<ResultSet>() {

														@Override
														public void accept(ResultSet resultSet) {

																	try {

																				if (resultSet.next()) {

																							MapObject mapObject;
																							int difficulty = 0;
																							int minFails = 0;
																							double minTime = 0;
																							String name = "" + (resultSet.getInt("mapid") + 1);
																							String mapname = "-";
																							World newWorld = createNewWorld(name);
																							Location spawn = new Location(newWorld, 0, 50, 0);
																							int x = 0;
																							int y = 0;
																							int z = 0;
																							String maptype = "";
																							String builder = player.getName();
																							mapObject = new MapObject("-", Integer.parseInt(name), difficulty ,0.0 ,minFails ,minTime ,spawn, maptype, "unranked", builder);

																							fw.setValue("mapname", name);
																							fw.save();

																							Main.getInstance().mapObjectMananger.getMapObjectArrayList().add(mapObject);

																							mySQL.update("INSERT INTO maps (mapname, builder, maptype, difficulty, minfails, mintime, x, y, z, world) VALUES " +
																									"('"+mapname+"', '"+ builder +"', '" + maptype + "', " + difficulty + ", " + minFails + ", " + minTime + ", '" + x + "', '" + y + "', '" + z + "', '" + newWorld.getName() + "');");

																							player.teleport(new Location(newWorld, 0, 50, 0));
																							click.getEvent().getWhoClicked().sendMessage(ChatColor.RED + "New World Created");
																							instance.inventory.builderInventory(player);
																							player.closeInventory();

																				} else {

																							player.sendMessage("No maps build yet - PlayerPickAxeClick l127");

																				}

																				player.closeInventory();

																	} catch (SQLException | IOException throwables) {
																				throwables.printStackTrace();
																	}

														}

											});

											player.closeInventory();
											return true; // returning true will cancel the click event and stop taking the item

								},

								"Empty Slot",
								"Click to generate a new Buildworld!"
						));

						return gui;

			}

			public InventoryGui guiShowAllCourses(Player player) {

						Main instance = Main.getInstance();
						ArrayList<MapObject> mapObjectArrayList = instance.mapObjectMananger.getMapObjectArrayList();

						String[] guiSetup = {
								"ggggggggg",
								"ggggggggg",
								"  fpdnl  "
						};

						InventoryGui gui = new InventoryGui(Main.getInstance(), player, "Map Selection", guiSetup);

						//sorts maps
						mapObjectArrayList.sort(Comparator.comparing(MapObject::getDifficulty));

						// First page
						gui.addElement(new GuiPageElement('f', new ItemStack(Material.ARROW), GuiPageElement.PageAction.FIRST, "Go to first page (current: %page%)"));
						// Previous page
						gui.addElement(new GuiPageElement('p', new ItemStack(Material.SIGN), GuiPageElement.PageAction.PREVIOUS, "Go to previous page (%prevpage%)"));
						// Next page
						gui.addElement(new GuiPageElement('n', new ItemStack(Material.SIGN), GuiPageElement.PageAction.NEXT, "Go to next page (%nextpage%)"));
						// Last page
						gui.addElement(new GuiPageElement('l', new ItemStack(Material.ARROW), GuiPageElement.PageAction.LAST, "Go to last page (%pages%)"));

						GuiElementGroup group = new GuiElementGroup('g');

						for (MapObject maps : mapObjectArrayList) {

									player.sendMessage("" + maps.getMapstatus());

									if (maps.getMapstatus().equals("ranked")) {

												ItemStack material = null;
												PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);
												double difficulty = maps.getDifficulty();
												int mapId = maps.getID();

												//creates the block that is displaed in the GUI depending on the difficulty
												if (difficulty <= 1.99) material = new ItemStack(Material.WOOL, 1, (short) 5);
												else if (difficulty <= 2.69) material = new ItemStack(Material.WOOL, 1, (short) 3);
												else if (difficulty <= 3.99) material = new ItemStack(Material.WOOL, 1, (short) 4);
												else if (difficulty <= 5.29) material = new ItemStack(Material.WOOL, 1, (short) 6);
												else if (difficulty <= 6.49) material = new ItemStack(Material.WOOL, 1, (short) 10);
												else if (difficulty >= 6.50) material = new ItemStack(Material.WOOL, 1, (short) 15);

												String title = maps.getName();
												// Add an element to the group
												// Elements are in the order they got added to the group and don't need to have the same type.
												group.addElement((new StaticGuiElement('e', new ItemStack(material),
														click -> {

																	playerObject.setJumpmode(true);
																	playerObject.setFailsrelative(0);
																	playerObject.setMapObject(maps);
																	playerObject.setTimerelative(0);
																	instance.inventory.createParkourInventory(player);

																	WorldCreator worldCreator = new WorldCreator(Integer.toString(maps.getID()));
																	worldCreator.createWorld();

																	click.getEvent().getWhoClicked().sendMessage(Message.MSG_JOINED_COURSE.getMessage().replace("{mapname}", maps.getName()));
																	click.getEvent().getWhoClicked().teleport(maps.getSpawn());
																	playerObject.setTimer(timer(player));

																	//adding mapattempt into db
																	instance.mapObjectMananger.firstPlayerMapLoad(player, maps);

																	return true; // returning true will cancel the click event and stop taking the item

														},
														"" + ChatColor.RESET + ChatColor.BOLD + title,
														"" + ChatColor.RESET + ChatColor.GRAY + "Builder: " + ChatColor.GOLD + " - \n" +
																"" + ChatColor.RESET + ChatColor.GRAY + "Maptype: " + ChatColor.GOLD + maps.getMapType() + "\n" +
																ChatColor.RESET + ChatColor.GRAY + "Difficulty: " + instance.playerTag.difficultyString(difficulty) + ChatColor.DARK_GRAY + " (" + difficulty + ")\n" +
																ChatColor.RESET + ChatColor.GRAY + "MapID: " + ChatColor.GOLD + mapId +
																"\n\n" +
																ChatColor.RESET + ChatColor.GRAY + ChatColor.GRAY + "------[ Map-Judge ]------\n" +
																ChatColor.RESET + ChatColor.GRAY + "Min-Fails: " + ChatColor.GOLD + maps.getMinFails() + "\n" +
																ChatColor.RESET + ChatColor.GRAY + "Min-Time: " + ChatColor.GOLD + maps.getMinTime()
												)));
									}
									gui.addElement(group);

						}

						return gui;

			}

			public InventoryGui guiUploadMap(Player player) {

						String[] guiSetup = {" gg   rr "};
						FW file = new FW("./ParkourBuild", player.getUniqueId().toString() + ".yml");
						Main instance = Main.getInstance();
						PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);
						AsyncMySQL mySQL = instance.mySQL;

						InventoryGui gui = new InventoryGui(Main.getInstance(), player, "Upload?", guiSetup);

						gui.addElement(new StaticGuiElement('g',
								new ItemStack(Material.WOOL, 1, (short) 3),
								click -> {

											playerObject.setBuildCourse(false);
											mySQL.update("UPDATE maps SET mapstatus = 'unranked' WHERE mapid = " + Integer.parseInt(file.getString("mapname")));
											mySQL.update("UPDATE maps SET world = " + file.getString("mapname") +
													" WHERE mapid = " + Integer.parseInt(file.getString("mapname")));

											file.setValue("hasCourse", false);
											file.setValue("mapname", "");
											try { file.save();
											} catch (IOException e) { e.printStackTrace(); }

											player.sendMessage("Successfully uploaded Map!");
											player.closeInventory();
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

			public World createNewWorld(String name) throws IOException {

						File worldDir = new File("./prw");
						FileUtils.copyDirectory(worldDir, new File(worldDir.getParent(), name));
						//delete uid so file can be loaded
						File uid = new File("./" + name + "/uid.dat");
						uid.delete();

						WorldCreator creator = new WorldCreator(name);

						return Bukkit.createWorld(creator);
			}

			public Timer timer(Player player) {

						Timer timer = new Timer();
						Main instance = Main.getInstance();
						PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);

						timer.scheduleAtFixedRate(new TimerTask() {

									@Override
									public void run() {

												if (!(Main.getInstance().playerManager.playerObjectHashMap.get(player).isJumpmode())) {

															timer.cancel();

												}

												//outputs the time and current pp of player over hotbar
												double currentPP = instance.playerStepPressureplate.calculatePPFromMap(playerObject);
												double currentTime = playerObject.getTimerelative();

												instance.titelManager.sendActionBar(player,
														ChatColor.BLUE + "Time: " + ChatColor.GRAY + String.format("%.3f", currentTime) + ChatColor.BLUE + " || Current PP: " + ChatColor.GRAY + String.format("%.2f", currentPP));

												playerObject.setTimerelative(playerObject.getTimerelative() + 0.05);

									}

						}, 0, 50);

						return timer;

			}

}
