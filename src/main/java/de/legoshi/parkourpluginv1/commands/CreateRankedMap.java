package de.legoshi.parkourpluginv1.commands;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.AsyncMySQL;
import de.legoshi.parkourpluginv1.util.mapinformation.MapJudges;
import de.legoshi.parkourpluginv1.util.mapinformation.MapMetaData;
import de.legoshi.parkourpluginv1.util.mapinformation.MapObject;
import de.legoshi.parkourpluginv1.util.Message;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CreateRankedMap implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

                if(!(sender instanceof Player)) return true;

                Player player = ((Player) sender).getPlayer();
                AsyncMySQL mySQL = Main.getInstance().mySQL;
                double difficulty;

                //if Input has all arguments
                if(args.length == 6 && player.getName().equals("Leg0shi_")) {

                        //checks if numbers in difficulty/minTime/minFails negative
                        try {

                                if (Double.parseDouble(args[1]) > 0) {

                                        difficulty = Double.parseDouble(args[1]);

                                } else {

                                        sender.sendMessage(Message.ERR_noNegative.getMessage());
                                        return true;

                                }
                                //if player enters not a number
                        } catch (NumberFormatException num) {

                                player.sendMessage(Message.ERR_WrongInput.getMessage());
                                return true;

                        }

                        String name = args[0];
                        int minFails = Integer.parseInt(args[2]);
                        double minTime = Double.parseDouble(args[3]);
                        String maptype = args[4];
                        Location spawn = player.getLocation();

                        double x = spawn.getX();
                        double y = spawn.getY();
                        double z = spawn.getZ();
                        String world = spawn.getWorld().getName();
                        String builder = args[5];

                        mySQL.update("INSERT INTO maps (mapname, builder, maptype, difficulty, minfails, mintime, x, y, z, world) VALUES " +
                            "('"+name+"','"+builder+"' , '"+maptype+"', "+difficulty+", "+minFails+", "+minTime+", '"+x+"', '"+y+"', '"+z+"', '"+world+"');");

                        sender.sendMessage(Message.MSG_successfulCreatedMap.getMessage());

                } else {

                        sender.sendMessage(Message.ERR_createRankedMap.getMessage());
                        return true;

                }

                return false;

        }

}
