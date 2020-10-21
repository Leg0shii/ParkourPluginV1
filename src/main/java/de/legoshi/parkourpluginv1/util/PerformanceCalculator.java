package de.legoshi.parkourpluginv1.util;

import de.legoshi.parkourpluginv1.util.mapinformation.MapObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class PerformanceCalculator {

			public double calcSpeedAcc(MapObject mapObject, double playerfails, double playertime) {

						double diff = mapObject.getMapJudges().getDifficulty();
						double maptime = mapObject.getMapJudges().getMinTime();
						double mapfails = Math.floor((diff / 3.0)) + 1;
						double prec = mapObject.getMapJudges().getPrecision();

						double failsP = 1;
						double acc;
						double timeP = 1;

						if (playertime > maptime) {
									timeP = 1 - (500 * Math.log10(playertime / maptime)) / 100;
						}
						if (playerfails > mapfails) {
									failsP = 1 - (20 * Math.log10(playerfails / mapfails)) / 100;
						}

						acc = ((2 - (timeP + failsP)) * 100);
						acc = 100 - (Math.pow(prec, 3) / 50.0) * acc;
						if(acc < 0) { acc = 0; }

						return acc;

			}

			public int calcMinFails(MapObject mapObject) {

						double minfails;
						double diff = mapObject.getMapJudges().getDifficulty();
						double cp = mapObject.getMapJudges().getCpcount();

						if(mapObject.getMapMetaData().getMapType().equals("hallway")) {
									minfails = diff < 1.5 ? 0.0831 * diff * cp : cp * ((Math.pow(diff, 2) - diff) / 6);
						} else {
									minfails = Math.floor((diff / 3.0)) + 1;
						}

						return (int) Math.ceil(minfails);

			}

			public double calcSpeedPP(double acc, double diff, double maptime) {

						double totalPP = 0;

						double length = 0.5 + 2 * (Math.atan(maptime / 45.0)) / Math.PI;
						double fcValue = 1;
						double bpp;

						if (acc > 95) {
									fcValue = 1 + (diff / 28.0);
						}

						if (acc >= 60 && !(acc > 100)) {

									bpp = 13 * (Math.pow(diff, 2) / 2) * fcValue * length;
									double reducePercent = -0.00376469 * Math.pow(acc, 3) + 0.85207548 * Math.pow(acc, 2) - 64.52755982 * acc + 1696.691182;
									totalPP = bpp * ((100 - reducePercent) / 100);

						}

						return totalPP;

			}

			public double calcHallwayAcc(MapObject mapObject, double playerfails, double playertime) {

						double prec = mapObject.getMapJudges().getPrecision();
						double cp = mapObject.getMapJudges().getCpcount();

						double diff = mapObject.getMapJudges().getDifficulty();
						double maptime = mapObject.getMapJudges().getMinTime();
						double mapfails = diff < 1.5 ? 0.0831 * diff * cp : cp * ((Math.pow(diff, 2) - diff) / 6);

						double acc;
						double timeP = 1;
						double failsP = 1;

						if (maptime < playertime) { timeP = 1 - (4.32809 * Math.log10(playertime / maptime)) / 100; } //time
						if (mapfails < playerfails) { failsP = 1 - (7.21348 * Math.log10(playerfails / mapfails)) / 100; } //fails

						acc = ((2 - (timeP + failsP)) * 100);
						acc = 100 - (Math.pow(prec, 3) / 50.0) * acc;
						if(acc < 0) { acc = 0; }

						return acc;

			}



						public double calcHallwayPP (double acc, double diff, double cp) {

									double fcValue = 1;
									double totalPP;
									double bpp;
									double length = 0.5 + 2 * (Math.atan(cp / 5.0)) / Math.PI;

									if (acc > 95) { fcValue = 1 + (diff / 28.0); }
									if (acc >= 60 && !(acc > 100)) {

												bpp = 13 * (Math.pow(diff, 2) / 2) * fcValue * length;
												double reducePercent = -0.00376469 * Math.pow(acc, 3) + 0.85207548 * Math.pow(acc, 2) - 64.52755982 * acc + 1696.691182;
												totalPP = bpp * ((100 - reducePercent) / 100);

									} else { totalPP = 0; }

									return totalPP;

						}

						public String calcMapRank (double acc) {

									String rank;

									if (acc >= 100) { rank = "" + ChatColor.GOLD + ChatColor.BOLD + "SS";
									} else if (acc >= 95 && acc <= 99.99999) { rank = "" + ChatColor.GOLD + ChatColor.BOLD + "S";
									} else if (acc >= 90 && acc < 95) { rank = "" + ChatColor.GREEN + ChatColor.BOLD + "A";
									} else if (acc >= 80 && acc < 90) { rank = "" + ChatColor.BLUE + ChatColor.BOLD + "B";
									} else if (acc >= 70 && acc < 80) { rank = "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "C";
									} else { rank = "" + ChatColor.RED + ChatColor.BOLD + "D";
									}

									return rank;

						}

			}
