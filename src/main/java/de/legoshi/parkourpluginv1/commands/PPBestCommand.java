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
        int pages = 1;

        if (args.length == 1) {

            showTopPlays(player, args[0], pages);
            return false;

        } else if(args.length == 2) {

            try { pages = Integer.parseInt(args[1]); }
            catch (NumberFormatException nfe) {

                player.sendMessage(Message.ERR_NOTANUMBER.getMessage());
                return false;

            }

            showTopPlays(player, args[0], pages);
            return false;

        }

        player.sendMessage(Message.ERR_BESTPPCOMMAND.getMessage());
        return false;

    }

    private void showTopPlays(Player player, String playername, int page) {

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
                int pageAmount = 1;

                try {

                    int totalPageAmount = instance.mySQLManager.getPages(resultSet);

                        if (page <= totalPageAmount && page >= 1) {

                            pageAmount = page;

                        } else {

                            player.sendMessage(Message.ERR_PAGENOTEXIST.getMessage());
                            return;

                        }

                    resultSet.absolute(((pageAmount-1)*10));

                    if(resultSet.next() && resultSet.getBoolean("cleared")) {

                        player.sendMessage(ChatColorHelper.chat(Message.MSG_BEST_HEADER.getRawMessage().replace("{player}", playername)));
                        player.sendMessage("\n");

                        do {

                            if(resultSet.getBoolean("cleared")) {

                                mapName = resultSet.getString("mapname");
                                fails = resultSet.getInt("pfails");
                                ppcount = resultSet.getDouble("ppcountc");
                                time = resultSet.getDouble("ptime");

                                player.sendMessage(ChatColorHelper.chat(Message.MSG_BEST_FORMAT.getRawMessage()
                                    .replace("{num}", Integer.toString(((pageAmount - 1) * 10) + index))
                                    .replace("{map}", instance.playerTag.fillSpaces(16, mapName + ":"))
                                    .replace("{time}", String.format("%.3f", time))
                                    .replace("{fails}", String.valueOf(fails))
                                    .replace("{pp}", String.format("%.2f", ppcount))));
                                index++;

                            }

                        } while(resultSet.next() && index < 11);

                        player.sendMessage("\n" + Message.MSG_PAGEAMOUNT.getRawMessage()
                            .replace("{page}", Integer.toString(pageAmount))
                            .replace("{pagetotal}", Integer.toString(totalPageAmount)));
                        player.sendMessage(ChatColorHelper.chat(Message.MSG_BEST_FOOTER.getRawMessage()));

                    } else {

                        player.sendMessage(Message.Prefix.getRawMessage() + "No Stats available");

                    }

                } catch (SQLException throwables) { throwables.printStackTrace(); }

            }

        });

    }

}
