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
                player.sendMessage(Message.ERR_MAPTOPCOMMAND.getMessage());
                return false;


        }

    }

    private void showMapScores(Player player, int id, String object, String objectname, String sort) {

        Main instance = Main.getInstance();
        AsyncMySQL mySQL = instance.mySQL;

        mySQL.query("SELECT "+ object +", playername, cleared FROM clears WHERE mapid = "+ id +" ORDER BY "+ object + " " + sort +";", new Consumer<ResultSet>() {

            @Override
            public void accept(ResultSet resultSet) {

                int index = 1;
                String type;
                String format;

                switch (objectname) {
                    case "Fails":
                        type = "Fails";
                        format = Message.MSG_MAPTOP_FAILS.getRawMessage();
                        break;
                    case "PP":
                        type = "Performace";
                        format = Message.MSG_MAPTOP_PP.getRawMessage();
                        break;
                    default:
                        format = Message.MSG_MAPTOP_TIMES.getRawMessage();
                        type = "Times";
                }

                try {

                    if(resultSet.next()) {

                        if (resultSet.getBoolean("cleared")) {

                            player.sendMessage(ChatColorHelper.chat(Message.MSG_MAPTOP_HEADER.getRawMessage()
                                    .replace("{map}", "MAP")
                                    .replace("{type}", type))); // Implement {map} please
                            do {

                                player.sendMessage(ChatColorHelper.chat(Message.MSG_MAPTOP_FORMAT.getRawMessage()
                                        .replace("{num}", String.valueOf(index))
                                        .replace("{name}", resultSet.getString("playername"))
                                        .replace("{amount}", format.replace("{num}", String.valueOf(resultSet.getInt(object))))));

                            } while (resultSet.next() && index < 10 && resultSet.getBoolean("cleared"));
                            player.sendMessage(ChatColorHelper.chat(Message.MSG_MAPTOP_FOOTER.getRawMessage()));

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
