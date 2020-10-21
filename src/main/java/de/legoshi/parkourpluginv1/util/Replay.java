package de.legoshi.parkourpluginv1.util;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.fakeplayer.NPC;
import de.legoshi.parkourpluginv1.util.mapinformation.MapObject;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;

public class Replay extends BukkitRunnable {

			private final JavaPlugin pl;
			private int counter;
			private PlayerObject playerObject;
			private int mapid;
			private FW fwRec;
			private FW fwPlay;

			public Replay(JavaPlugin pl, PlayerObject playerObject) {

						this.pl = pl;
						this.counter = 0;
						this.playerObject = playerObject;
						this.mapid = playerObject.getPlayerMap().getMapObject().getID();
						this.fwRec = new FW("./ParkourReplays/"+mapid,
								playerObject.getPlayer().getUniqueId().toString() +".yml");

						FW fw = new FW("./ParkourReplays/" + mapid, mapid + "_" + playerObject.getPlayer().getUniqueId().toString() + ".yml");
						if(fw.exist()) this.fwPlay = fw;
						else this.fwPlay = null;

			}

			public Replay() {
						this.pl = Main.getInstance();
			}

			@Override
			public void run() {

						if(counter%20 == 0) Bukkit.getConsoleSender().sendMessage("Rec " + playerObject.getPlayer().getName() + ":" + counter);

						if(playerObject.getPlayerStatus().isJumpmode()) {

									if(counter < 6000 && playerObject.getPlayer().isOnline()) {

												this.fwRec.setLocation(playerObject.getPlayer().getLocation(), counter++);

									} else { stopReplayRec(); }

						}

			}

			public void stopReplayRec() {

						this.fwRec.save();
						File fold = new File("./ParkourReplays/" + playerObject.getPlayerMap().getMapObject().getID() +
								"/" + playerObject.getPlayer().getUniqueId().toString() + ".yml");
						fold.delete();
						this.cancel();

			}

			public void saveReplayRec() {

						Bukkit.getConsoleSender().sendMessage("Trying to save replay");
						fwRec.setValue("length", counter);
						this.fwRec.save();

						String playername = playerObject.getPlayer().getUniqueId().toString();
						int mapid = playerObject.getPlayerMap().getMapObject().getID();

						File fold = new File("./ParkourReplays/" + mapid + "/" + playername + ".yml");
						File fnew = new File("./ParkourReplays/" + mapid + "/" + mapid + "_" + playername + ".yml");

						if(fnew.exists()) {

									fnew.delete();
									playerObject.getPlayer().sendMessage("Removed worse play!");

						}

						fold.renameTo(fnew);

						this.cancel();

			}

			public void startReplayRecording(PlayerObject playerObject) {

						MapObject mapObject = playerObject.getPlayerMap().getMapObject();
						int mapid = mapObject.getID();
						Player player = playerObject.getPlayer();
						FW fw = new FW("./ParkourReplays/"+mapid, playerObject.getPlayer().getUniqueId().toString() +".yml");
						final int[] index = {0};
						playerObject.setIndex(0);

						//player.sendMessage("Recording started");

						final int taskID;
						taskID = new BukkitRunnable() {

									@Override
									public void run() {

												fw.setLocation(player.getLocation(), index[0]);
												playerObject.setIndex(index[0]);
												index[0]++;

												if(index[0]%20 == 0) Bukkit.getConsoleSender().sendMessage(player.getName() + ": " + index[0]);
												if(index[0] > 6000 || !player.isOnline() || !playerObject.getPlayerStatus().isJumpmode()) {

															fw.save();
															File fold = new File("./ParkourReplays/" + mapid + "/" + player.getName() + ".yml");
															fold.delete();
															cancel();

												}

									}

						}.runTaskTimer(Main.getInstance(), 0, 1L).getTaskId();

						playerObject.setTaskID(taskID);
						playerObject.setFw(fw);
						//player.sendMessage("Variables Saved");

			}

			public void playReplay(PlayerObject playerObject, OfflinePlayer p, int mapid) {

						Player player = playerObject.getPlayer();
						final int[] index = {0};
						FW fwnew = new FW("./ParkourReplays/"+mapid, mapid + "_" + p.getUniqueId().toString() +".yml");
						NPC npc = new NPC(p.getName(), fwnew.getLocation(player, 0));
						npc.spawn(player);

						//player.sendMessage("Replay started!");

						new BukkitRunnable() {

									@Override
									public void run() {

												if(index[0]%20 == 0) Bukkit.getConsoleSender().sendMessage("Repl " + playerObject.getPlayer().getName() + ":" + index[0]);

												if(playerObject.getPlayerStatus().isReplayStart() && playerObject.getPlayerStatus().isReplaymode() && (fwnew.getInt("length") >= index[0])) {

															npc.teleport(fwnew.getLocation(player, index[0]), player);
															index[0]++;

												} else if (!(playerObject.getPlayerStatus().isReplaymode()) || !(fwnew.getInt("length") >= index[0])){

															//player.sendMessage("Replay finished.");
															npc.destroy(player);
															cancel();

												}

									}

						}.runTaskTimer(Main.getInstance(), 1, 1L);

			}

			public void saveReplayFile(PlayerObject playerObject) {

						Bukkit.getScheduler().cancelTask(playerObject.getTaskID());
						playerObject.getFw().setValue("length", playerObject.getIndex());
						playerObject.getFw().save();

						String playername = playerObject.getPlayer().getUniqueId().toString();
						int mapid = playerObject.getPlayerMap().getMapObject().getID();

						File fold = new File("./ParkourReplays/" + mapid + "/" + playername + ".yml");
						File fnew = new File("./ParkourReplays/" + mapid + "/" + mapid + "_" + playername + ".yml");

						if(fnew.exists()) {

									fnew.delete();
									//playerObject.getPlayer().sendMessage("Removed worse play!");

						}

						fold.renameTo(fnew);

						//playerObject.getPlayer().sendMessage("Saved Replay!");

			}

			public void deleteReplayFile(PlayerObject playerObject) {

						playerObject.getFw().save();

						String playername = playerObject.getPlayer().getUniqueId().toString();
						int mapid = playerObject.getPlayerMap().getMapObject().getID();

						File fold = new File("./ParkourReplays/" + mapid + "/" + playername + ".yml");
						fold.delete();

						//playerObject.getPlayer().sendMessage("Deleted Replay!");

			}

			public FW getFwRec() {
						return fwRec;
			}

			public void setFwRec(FW fwRec) {
						this.fwRec = fwRec;
			}

			public FW getFwPlay() {
						return fwPlay;
			}

			public void setFwPlay(FW fwPlay) {
						this.fwPlay = fwPlay;
			}

			public int getCounter() {
						return counter;
			}

			public void setCounter(int counter) {
						this.counter = counter;
			}

			public PlayerObject getPlayerObject() {
						return playerObject;
			}

			public void setPlayerObject(PlayerObject playerObject) {
						this.playerObject = playerObject;
			}

}
