package de.legoshi.parkourpluginv1.gui;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.AsyncMySQL;
import de.legoshi.parkourpluginv1.util.mapinformation.MapJudges;
import de.legoshi.parkourpluginv1.util.mapinformation.MapMetaData;
import de.legoshi.parkourpluginv1.util.mapinformation.MapObject;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerMap;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerStatus;
import de.themoep.inventorygui.*;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class MapSelectGUI {

			public void guiShowAllCourses(Player player, String search, int pages) {

						String[] guiSetup = {
								"ggggggggk",
								"ggggggggh",
								"ggggggggh",
								"ggggggggh",
								"ggggggggf"
						};

						InventoryGui gui = new InventoryGui(Main.getInstance(), player, "Map Selection", guiSetup);
						AsyncMySQL mySQL = Main.getInstance().mySQL;

						GuiElementGroup group = new GuiElementGroup('g');
						gui.addElement((new StaticGuiElement('h', new ItemStack(Material.STAINED_GLASS_PANE, 1), click -> {
									return true;
						}, " ")));

						player.closeInventory();
						gui.show(player);

						mySQL.query("SELECT * FROM maps WHERE mapstatus = '" + search + "' LIMIT "+(40*pages)+", 41;", new Consumer<ResultSet>() {

									@Override
									public void accept(ResultSet resultSet) {

												try {
															if(resultSet.next()) {

																		resultSet.last();
																		gui.addElement(upElement(search));
																		gui.addElement(downElement(search, resultSet.getRow()));
																		resultSet.beforeFirst();

																		while (resultSet.next()) {

																					group.addElement(mapElements(resultSet)); //adding Elements to the Selector

																		}

																		gui.addElement(group);
																		gui.show(player);

															}

												} catch (SQLException throwables) {
															throwables.printStackTrace();
												}

									}

						});

			}

			public StaticGuiElement mapElements(ResultSet resultSet) throws SQLException {

						//loads all MapObjects from the DB into the MapObjectManager
						Main instance = Main.getInstance();
						MapObject mapObject = instance.mapObjectMananger.loadMapFromDB(resultSet);

						//creates the block that is displaed in the GUI depending on the difficulty
						MapJudges mapJudges = mapObject.getMapJudges();
						double difficulty = mapJudges.getDifficulty();
						ItemStack material = calculateWool(difficulty);

						MapMetaData mapMetaData = mapObject.getMapMetaData();
						String title = mapMetaData.getName();
						int mapId = mapObject.getID();

						StaticGuiElement staticGuiElement;
						staticGuiElement = new StaticGuiElement('g', new ItemStack(material),
								click -> {

											Player player = (Player) click.getEvent().getWhoClicked();
											player.closeInventory();
											File playerW = new File("./ParkourMapsPlayers/" + mapId + ".zip");

											new Thread(new Runnable() {

														@Override
														public void run() {

																	if (playerW.exists()) {

																				instance.worldSaver.delayedUnzipWorld(playerW); //if not loaded, load world

																	}

																	Bukkit.getScheduler().runTask(instance, new Runnable() {

																				@Override
																				public void run() {

																							World world = instance.worldSaver.loadWorld(String.valueOf(mapId));

																							Location mapSpawn = mapMetaData.getSpawn();
																							mapSpawn.setWorld(world);
																							mapObject.getMapMetaData().setSpawn(mapSpawn); //updates the Mapspawn to current world
																							instance.playerManager.playerObjectHashMap.get(player).getPlayerMap().setMapObject(mapObject); //saves mapObject in Player

																							instance.playerManager.playerMapJoin(player); //sets player up for mapjoin

																							instance.mapObjectMananger.firstPlayerMapLoad(player, mapObject); //adding mapattempt into db
																							instance.mapObjectMananger.loadHighestPP(player, mapObject); //loading highest ppPlay in mabobject

																							PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);
																							instance.scoreboardHelper.initializeInMapSB(playerObject);
																							instance.tabTagCreator.updateRank(player);

																				}
																	});

														}

											}).start();

											return true; // returning true will cancel the click event and stop taking the item

								},
								"" + ChatColor.RESET + ChatColor.BOLD + title,
								"" + ChatColor.RESET + ChatColor.GRAY + "Builder: " + ChatColor.GOLD + mapMetaData.getBuilder() + "\n" +
										"" + ChatColor.RESET + ChatColor.GRAY + "Maptype: " + ChatColor.GOLD + mapMetaData.getMapType() + "\n" +
										ChatColor.RESET + ChatColor.GRAY + "Difficulty: " + instance.playerTag.difficultyString(difficulty) + ChatColor.DARK_GRAY + " (" + difficulty + ")\n" +
										ChatColor.RESET + ChatColor.GRAY + "MapID: " + ChatColor.GOLD + mapId +
										"\n\n" +
										ChatColor.RESET + ChatColor.GRAY + ChatColor.GRAY + "------[ Map-Judge ]------\n" +
										ChatColor.RESET + ChatColor.GRAY + "Precision: " + ChatColor.GOLD + mapJudges.getPrecision() + "\n" +
										ChatColor.RESET + ChatColor.GRAY + "Min-Fails: " + ChatColor.GOLD + instance.performanceCalculator.calcMinFails(mapObject) + "\n" +
										ChatColor.RESET + ChatColor.GRAY + "Min-Time: " + ChatColor.GOLD + mapJudges.getMinTime()
						);

						return staticGuiElement;

			}

			public StaticGuiElement downElement(String search, int size) {

						StaticGuiElement staticGuiElement;
						staticGuiElement = new StaticGuiElement('f', new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 2),
								click -> {

											if(size == 41) {

														Player player = (Player) click.getEvent().getWhoClicked();
														PlayerStatus playerStatus = Main.getInstance().playerManager.playerObjectHashMap.get(player).getPlayerStatus();
														guiShowAllCourses(player, search, playerStatus.getPage() + 1);
														Main.getInstance().playerManager.playerObjectHashMap.get(player).getPlayerStatus().setPage(playerStatus.getPage() + 1);

											}

											return true;

								}
								, "DOWN");

						return staticGuiElement;

			}

			public StaticGuiElement upElement(String search) {

						StaticGuiElement staticGuiElement;
						staticGuiElement = new StaticGuiElement('k', new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 2),
								click -> {

											Player player = (Player) click.getEvent().getWhoClicked();
											PlayerStatus playerStatus = Main.getInstance().playerManager.playerObjectHashMap.get(player).getPlayerStatus();

											if(playerStatus.getPage() > 0) {

														guiShowAllCourses(player, search, playerStatus.getPage() - 1);
														Main.getInstance().playerManager.playerObjectHashMap.get(player).getPlayerStatus().setPage(playerStatus.getPage() - 1);

											}

											return true;

								}
								, "UP");

						return staticGuiElement;

			}

			public ItemStack calculateWool(double difficulty) {

						ItemStack material = null;

						if (difficulty <= 1.99) material = new ItemStack(Material.WOOL, 1, (short) 5);
						else if (difficulty <= 2.69) material = new ItemStack(Material.WOOL, 1, (short) 3);
						else if (difficulty <= 3.99) material = new ItemStack(Material.WOOL, 1, (short) 4);
						else if (difficulty <= 5.29) material = new ItemStack(Material.WOOL, 1, (short) 6);
						else if (difficulty <= 6.49) material = new ItemStack(Material.WOOL, 1, (short) 10);
						else if (difficulty >= 6.50) material = new ItemStack(Material.WOOL, 1, (short) 15);

						return material;

			}

			public Timer timer(Player player) {

						Timer timer = new Timer();
						Main instance = Main.getInstance();
						PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);
						PlayerStatus playerStatus = playerObject.getPlayerStatus();
						PlayerMap playerMap = playerObject.getPlayerMap();

						timer.scheduleAtFixedRate(new TimerTask() {

									@Override
									public void run() {

												//outputs the time and current pp of player over hotbar
												double acc = 0.00;
												double currentPP = 0.00;

												double diff = playerObject.getPlayerMap().getMapObject().getMapJudges().getDifficulty();
												double cp = playerObject.getPlayerMap().getMapObject().getMapJudges().getCpcount();

												if(playerMap.getMapObject().getMapMetaData().getMapType().equals("speedrun")) {
															acc = instance.performanceCalculator.calcSpeedAcc(playerObject);
															currentPP = instance.performanceCalculator.calcSpeedPP(acc, diff);
												} else if (playerMap.getMapObject().getMapMetaData().getMapType().equals("hallway")) {
															acc = instance.performanceCalculator.calcHallwayAcc(playerObject);
															currentPP = instance.performanceCalculator.calcHallwayPP(acc, diff, cp);
												}

												Scoreboard scoreboard = player.getScoreboard();

												if (!(playerStatus.isJumpmode())) {

															timer.cancel();
															//scoreboard.getTeam("ppscoremap").setPrefix(ChatColor.WHITE + "0 pp");
															//scoreboard.getTeam("acc").setPrefix("" + ChatColor.WHITE + "60.0%");

												}

												double currentTime = playerMap.getTimeRelative();
												int currentFails = playerMap.getFailsrelative();

												if(acc > 60 && playerStatus.isJumpmode()) {
															scoreboard.getTeam("ppscoremap").setPrefix("" + ChatColor.WHITE + String.format("%.2f", currentPP) + "pp");
															scoreboard.getTeam("acc").setPrefix("" + ChatColor.WHITE + String.format("%.2f", acc) + "%");
															scoreboard.getTeam("time").setPrefix("" + ChatColor.WHITE + String.format("%.3f", currentTime) + "s ");
															scoreboard.getTeam("time").setSuffix(ChatColor.GRAY + " (" +  playerMap.getMapObject().getMapJudges().getMinTime() + "s)");
															scoreboard.getTeam("mapfail").setPrefix("" + ChatColor.WHITE + currentFails +
																	ChatColor.GRAY + " (" +instance.performanceCalculator.calcMinFails(playerMap.getMapObject()) + ")");
												} else if (playerStatus.isJumpmode()) {
															scoreboard.getTeam("ppscoremap").setPrefix("" + ChatColor.WHITE + "0 pp");
															scoreboard.getTeam("acc").setPrefix("" + ChatColor.WHITE + "60.00 %");
															scoreboard.getTeam("time").setPrefix("" + ChatColor.WHITE + String.format("%.3f", currentTime) + "s ");
															scoreboard.getTeam("time").setSuffix(ChatColor.GRAY + " (" + instance.performanceCalculator.calcMinFails(playerMap.getMapObject()) + "s)");
															scoreboard.getTeam("mapfail").setPrefix("" + ChatColor.WHITE + currentFails +
																	ChatColor.GRAY + " (" + playerMap.getMapObject().getMapJudges().getMinFails() + ")");
												}

												playerMap.setTimeRelative(playerMap.getTimeRelative() + 0.05);

									}

						}, 200, 50);

						return timer;

			}

}
