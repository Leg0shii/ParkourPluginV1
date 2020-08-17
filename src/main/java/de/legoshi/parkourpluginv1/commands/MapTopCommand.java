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
        String format;
        String type;
        int id;

        if(args.length < 2 || args.length > 3) {

            player.sendMessage(Message.ERR_MAPTOPCOMMAND.getMessage());
            return false;

        }

        //check if id input is a number
        try {

            id = Integer.parseInt(args[0]);

        }
        catch (NumberFormatException nfe) {

            player.sendMessage(Message.ERR_NOTANUMBER.getMessage());
            return false;

        }

        switch (args[1].toLowerCase()) {
            case "fails":

                type = "Fails";
                format = Message.MSG_MAPTOP_FAILS.getRawMessage();
                showMapScores(player, id, "pfails", "ASC", format, type, args);
                return false;

            case "pp":

                type = "Performance";
                format = Message.MSG_MAPTOP_PP.getRawMessage();
                showMapScores(player, id, "ppcountc", "DESC", format, type, args);
                return false;

            case "time":

                type = "Times";
                format = Message.MSG_MAPTOP_TIMES.getRawMessage();
                showMapScores(player, id, "ptime", "ASC", format, type, args);
                return false;

            default:
                player.sendMessage(Message.ERR_MAPTOPCOMMAND.getMessage());
                return false;


        }

    }

    private void showMapScores(Player player, int id, String object, String sort, String format, String type, String[] args) {

        Main instance = Main.getInstance();
        AsyncMySQL mySQL = instance.mySQL;

        mySQL.query("SELECT maps.mapname, clears." + object + ", clears.cleared, clears.playername FROM clears, maps " +
            "WHERE clears.mapid = maps.mapid AND clears.mapid = "+ id +" AND clears.cleared = 1 ORDER BY clears."+ object + " " + sort +";", new Consumer<ResultSet>() {
            @Override
            public void accept(ResultSet resultSet) {

                int index = 1;

                try {

                    int pageAmount = 1;
                    int totalPageAmount = instance.mySQLManager.getPages(resultSet);
                    int enteredPage = 1;

                    if (args.length == 3) {

                        try {

                            enteredPage = Integer.parseInt(args[2]);

                        }
                        catch (NumberFormatException nfe) {

                            player.sendMessage(Message.ERR_NOTANUMBER.getMessage());
                            return;

                        }

                        if (enteredPage <= totalPageAmount && enteredPage >= 1) {

                            pageAmount = enteredPage;

                        } else {

                            player.sendMessage(Message.ERR_PAGENOTEXIST.getMessage());
                            return;

                        }

                    }

                    resultSet.absolute(((pageAmount-1)*10));
                    if(resultSet.next()) {

                        String value = "";

                        player.sendMessage(ChatColorHelper.chat(Message.MSG_MAPTOP_HEADER.getRawMessage()
                            .replace("{map}", resultSet.getString("mapname"))
                            .replace("{type}", type)));
                        player.sendMessage("\n");

                            do {

                                if(resultSet.getBoolean("cleared")) {

                                    //parses numbers with correct rounding
                                    if (type.equals("Fails")) {
                                        value = String.valueOf(resultSet.getInt(object));
                                    } else if (type.equals("Times")) {
                                        value = String.format("%.3f", resultSet.getDouble(object));
                                    } else if (type.equals("Performance")) {
                                        value = String.format("%.2f", resultSet.getDouble(object)) + "";
                                    }

                                    player.sendMessage(ChatColorHelper.chat(Message.MSG_MAPTOP_FORMAT.getRawMessage()
                                        .replace("{num}", String.valueOf(index))
                                        .replace("{name}", instance.playerTag.fillSpaces(17, resultSet.getString("playername") + ":"))
                                        .replace("{amount}", format.replace("{num}", value))));

                                    index++;

                                }

                            } while (index < 11 && resultSet.next());

                            player.sendMessage("\n" + Message.MSG_PAGEAMOUNT.getRawMessage()
                                .replace("{page}", Integer.toString(pageAmount))
                                .replace("{pagetotal}", Integer.toString(totalPageAmount)));
                            player.sendMessage(ChatColorHelper.chat(Message.MSG_MAPTOP_FOOTER.getRawMessage()));

                    } else {

                        player.sendMessage(Message.Prefix.getRawMessage() + "No Stats available");

                    }

                } catch (SQLException throwables) { throwables.printStackTrace(); }

            }

        });

    }

}
