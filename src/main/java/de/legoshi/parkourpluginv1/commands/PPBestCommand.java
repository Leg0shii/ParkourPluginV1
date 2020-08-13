package de.legoshi.parkourpluginv1.commands;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.AsyncMySQL;
import de.legoshi.parkourpluginv1.util.ChatColorHelper;
import de.legoshi.parkourpluginv1.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public class PPBestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) return false;

        Player player = ((Player) sender).getPlayer();

        if(args.length == 0) {

            showTopPlays(player, player.getName(), args);
            return false;

        } else if (args.length == 1) {

            showTopPlays(player, args[0], args);
            return false;

        }

        player.sendMessage(Message.ERR_BESTPPCOMMAND.getMessage());
        return false;

    }

    private void showTopPlays(Player player, String playername, String[] args) {

        Main instance = Main.getInstance();
        AsyncMySQL mySQL = instance.mySQL;

        mySQL.query("SELECT * FROM clears, maps WHERE clears.mapid = maps.mapid AND playername = '" + playername + "' ORDER BY clears.ppcountc DESC;",
            new Consumer<ResultSet>() {

            @Override
            public void accept(ResultSet resultSet) {

                int index = 1;
                String mapName;
                int fails;
                double ppcount;
                double time;

                try {

                    if(resultSet.next() && resultSet.getBoolean("cleared")) {

                        player.sendMessage(ChatColorHelper.chat(Message.MSG_BEST_HEADER.getRawMessage().replace("{player}", playername)));
                        do {

                            mapName = resultSet.getString("mapname");
                            fails = resultSet.getInt("pfails");
                            ppcount = resultSet.getDouble("ppcountc");
                            time = resultSet.getDouble("ptime");

                            player.sendMessage(ChatColorHelper.chat(Message.MSG_BEST_FORMAT.getRawMessage()
                                    .replace("{num}", String.valueOf(index))
                                    .replace("{map}", mapName)
                                    .replace("{time}", String.valueOf(time))
                                    .replace("{fails}", String.valueOf(fails))
                                    .replace("{pp}", String.valueOf(ppcount))));
                            index++;

                        } while(resultSet.next() && index < 10 && resultSet.getBoolean("cleared"));
                        player.sendMessage(ChatColorHelper.chat(Message.MSG_BEST_FOOTER.getRawMessage()));

                    } else {

                        player.sendMessage(Message.Prefix.getRawMessage() + "No Stats available");
                    }

                } catch (SQLException throwables) { throwables.printStackTrace(); }

            }

        });

    }

}
