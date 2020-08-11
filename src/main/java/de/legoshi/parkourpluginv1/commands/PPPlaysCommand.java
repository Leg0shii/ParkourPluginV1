package de.legoshi.parkourpluginv1.commands;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.AsyncMySQL;
import de.legoshi.parkourpluginv1.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public class PPPlaysCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) return false;

        Player player = ((Player) sender).getPlayer();

        // /ppplays <player>

        if(args.length == 0) {

            showTopPlays(player, player);

        } else if (args.length == 1) {

            if(!(Bukkit.getPlayer(args[0]) == null)) {

                showTopPlays(player, Bukkit.getPlayer(args[0]));

            } else {

                player.sendMessage(Message.Prefix.getRawMessage() + "Player not online :(");

            }

        }



        return false;

    }

    private void showTopPlays(Player player, Player playerInQuestion) {

        Main instance = Main.getInstance();
        AsyncMySQL mySQL = instance.mySQL;

        mySQL.query("SELECT * FROM clears, maps WHERE clears.mapid = maps.mapid AND playername = '" + playerInQuestion.getName() + "' ORDER BY clears.ppcountc DESC;",
            new Consumer<ResultSet>() {

            @Override
            public void accept(ResultSet resultSet) {

                int index = 2;
                String mapName;
                int fails;
                double ppcount;
                double time;

                try {

                    if(resultSet.next() && resultSet.getBoolean("cleared")) {

                        mapName = resultSet.getString("mapname");
                        fails = resultSet.getInt("pfails");
                        ppcount = resultSet.getDouble("ppcountc");
                        time = resultSet.getDouble("ptime");

                        player.sendMessage("Top Plays from: " + playerInQuestion.getName());
                        player.sendMessage("| 1. Map: " + mapName + " Fails: " + fails + " PP: " + ppcount + " time: " + time);


                        while(resultSet.next() && index < 10 && resultSet.getBoolean("cleared")) {

                            mapName = resultSet.getString("mapname");
                            fails = resultSet.getInt("pfails");
                            ppcount = resultSet.getDouble("ppcountc");
                            time = resultSet.getDouble("ptime");

                            player.sendMessage("| " + index + ". Map: " + mapName + " Fails: " + fails + " PP: " + ppcount + " time: " + time);
                            index++;

                        }

                    } else {

                        player.sendMessage(Message.Prefix.getRawMessage() + "No Stats available");

                    }

                } catch (SQLException throwables) { throwables.printStackTrace(); }

            }

        });

    }

}
