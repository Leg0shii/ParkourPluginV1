package de.legoshi.parkourpluginv1.commands;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.AsyncMySQL;
import de.legoshi.parkourpluginv1.util.MapObject;
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
                String name;
                Location spawn;
                double difficulty;
                AsyncMySQL mySQL = Main.getInstance().mySQL;
                int minFails;
                double minTime;

                //if Input has all arguments
                if(args.length == 4 && player.getDisplayName().equals("Leg0shi_")) {

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

                        name = args[0];
                        minFails = Integer.parseInt(args[2]);
                        minTime = Double.parseDouble(args[3]);
                        spawn = player.getLocation();

                        double x = spawn.getX();
                        double y = spawn.getY();
                        double z = spawn.getZ();
                        String world = spawn.getWorld().getName();

                        ArrayList<MapObject> mapObjectArrayList = Main.getInstance().mapObjectMananger.getMapObjectArrayList();

                        Main.getInstance().mapObjectMananger.getMapObjectArrayList().add(new MapObject(name, mapObjectArrayList.size()+1, difficulty,
                            0, minFails, minTime, spawn));

                        mySQL.update("INSERT INTO maps (mapname, difficulty, minfails, mintime, x, y, z, world) VALUES " +
                            "('"+name+"', "+difficulty+", "+minFails+", "+minTime+", '"+x+"', '"+y+"', '"+z+"', '"+world+"');");

                        sender.sendMessage(Message.MSG_successfulCreatedMap.getMessage());

                } else {

                        sender.sendMessage(Message.ERR_createRankedMap.getMessage());
                        return true;

                }

                return false;

        }

}
