package de.legoshi.parkourpluginv1.commands;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.AsyncMySQL;
import de.legoshi.parkourpluginv1.util.ChatColorHelper;
import de.legoshi.parkourpluginv1.util.Message;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPistonEvent;
import org.bukkit.event.block.BlockRedstoneEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public class PPTopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        AsyncMySQL mySQL = Main.getInstance().mySQL;
        Main instance = Main.getInstance();

        if (!(sender instanceof Player)) return true;

        Player player = ((Player) sender).getPlayer();

        mySQL.query("SELECT ppcountp, playername, playeruuid FROM tablename ORDER BY ppcountp DESC;", new Consumer<ResultSet>() {

            @Override
            public void accept(ResultSet resultSet) {

                try {

                    int i = 1;
                    int pageAmount = 1;
                    int totalPageAmount = instance.mySQLManager.getPages(resultSet);
                    int enteredPage;

                    if (args.length == 1) {

                        //checking for another page
                        try {

                            enteredPage = Integer.parseInt(args[0]);

                        } catch (NumberFormatException nfe) {

                            player.sendMessage(Message.ERR_NOTANUMBER.getMessage());
                            return;

                        }

                        if(enteredPage <= totalPageAmount && enteredPage >= 1) {

                            pageAmount = enteredPage;

                        }

                        else {

                            player.sendMessage(Message.ERR_PAGENOTEXIST.getMessage());
                            return;

                        }

                    }

                    resultSet.absolute(((pageAmount-1)*10));

                    if (resultSet.next()) {

                        player.sendMessage(ChatColorHelper.chat(Message.MSG_HEADERCOURSECLEAR.getRawMessage()));
                        player.sendMessage("\n");

                        do {

                            String color = instance.playerTag.getPrefix((i + (pageAmount-1)*10));

                            player.sendMessage(ChatColorHelper.chat(Message.MSG_COURSECLEAR.getRawMessage()
                                    .replace("{color}", color)
                                    .replace("{num}", "" + (i + (pageAmount-1)*10) + ChatColor.RESET)
                                    .replace("{player}", instance.playerTag.fillSpaces(16, resultSet.getString("playername") + ":"))
                                    .replace("{ppscore}", String.format("%.2f", resultSet.getDouble("ppcountp")))));

                            i++;

                        } while (i < 11 && resultSet.next());

                        player.sendMessage("\n" + Message.MSG_PAGEAMOUNT.getRawMessage()
                                .replace("{page}", Integer.toString(pageAmount))
                                .replace("{pagetotal}", Integer.toString(totalPageAmount)));
                        player.sendMessage(ChatColorHelper.chat(Message.MSG_FOOTERCOURSECLEAR.getRawMessage()));

                    }

                } catch (SQLException throwables) { throwables.printStackTrace(); }

            }

        });

        return false;

    }

}
