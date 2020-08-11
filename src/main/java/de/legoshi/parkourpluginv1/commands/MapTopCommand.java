package de.legoshi.parkourpluginv1.commands;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.AsyncMySQL;
import de.legoshi.parkourpluginv1.util.ChatColorHelper;
import de.legoshi.parkourpluginv1.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public class MapTopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = ((Player) sender).getPlayer();

        if(args.length != 2) {

            player.sendMessage("/maptop <mapid> <fails/pp/time>");
            return false;

        }

        int id = Integer.parseInt(args[0]);

        switch (args[1].toLowerCase()) {
            case "fails":

                showMapScores(player, id, "pfails", "Fails", "ASC");
                return false;

            case "pp":

                showMapScores(player, id, "ppcountc", "PP", "DESC");
                return false;

            case "time":

                showMapScores(player, id, "ptime", "Time", "ASC");
                return false;

            default:
                player.sendMessage("/maptop <mapId> <fails/pp/time>");
                return false;


        }

    }

    private void showMapScores(Player player, int id, String object, String objectname, String sort) {

        Main instance = Main.getInstance();
        AsyncMySQL mySQL = instance.mySQL;

        mySQL.query("SELECT "+ object +", playername, cleared FROM clears WHERE mapid = "+ id +" ORDER BY "+ object + " " + sort +";", new Consumer<ResultSet>() {

            @Override
            public void accept(ResultSet resultSet) {

                int index = 2;

                try {

                    if(resultSet.next()) {

                        if (resultSet.getBoolean("cleared")) {

                            player.sendMessage(ChatColorHelper.chat("&d&m                                            "));

                            String title;
                            String color;
                            String suffix;
                            switch (objectname) {
                                case "Fails":
                                    title = " &l{mapname} &d&lTop Fails";
                                    color = "§c";
                                    suffix = " fails";
                                    break;
                                case "PP":
                                    title = " &l{mapname} &d&lTop PP";
                                    color = "§6";
                                    suffix = "pp";
                                    break;
                                default:
                                    title = " &l{mapname} &d&lTop Times";
                                    color = "§a";
                                    suffix = "";
                            }

                            player.sendMessage(ChatColorHelper.chat(title.replace("{mapname}", "MAP"))); // Please implement mapname

                            player.sendMessage(ChatColorHelper.chat("&d&m                                            "));

                            player.sendMessage( " 1. " + Message.MSG_SHOWBESTTENFAILS.getRawMessage()
                                .replace("{player}", resultSet.getString("playername"))
                                .replace("{theme}", objectname)
                                .replace("{score}", Integer.toString(resultSet.getInt(object)))
                                .replace("{color}", color)
                                .replace("{suffix}", suffix));


                            while (resultSet.next() && index < 10 && resultSet.getBoolean("cleared")) {

                                player.sendMessage( " " + index + ". " + Message.MSG_SHOWBESTTENFAILS.getRawMessage()
                                        .replace("{player}", resultSet.getString("playername"))
                                        .replace("{theme}", objectname)
                                        .replace("{score}", Integer.toString(resultSet.getInt(object)))
                                        .replace("{color}", color)
                                        .replace("{suffix}", suffix));
                                index++;

                            }

                            player.sendMessage(ChatColorHelper.chat("&d&m                                            "));

                        } else {

                            player.sendMessage(Message.Prefix.getRawMessage() + "No Stats available");

                        }

                    } else {

                        player.sendMessage(Message.Prefix.getRawMessage() + "No Stats available");

                    }

                } catch (SQLException throwables) { throwables.printStackTrace(); }

            }

        });

    }

}
