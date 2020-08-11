package de.legoshi.parkourpluginv1.commands;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.AsyncMySQL;
import de.legoshi.parkourpluginv1.util.Message;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

                    if (args.length == 1) {

                        int enteredPage = Integer.parseInt(args[0]);

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

                        player.sendMessage(Message.MSG_HEADERCOURSECLEAR.getRawMessage());

                        do {

                            player.sendMessage(Message.MSG_COURSECLEAR.getRawMessage().replace("{num}", Integer.toString(i))
                                    .replace("{player}", resultSet.getString("playername"))
                                    .replace("{ppscore}", Double.toString(resultSet.getDouble("ppcountp"))));

                            i++;

                        } while (i < 11 && resultSet.next());

                        player.sendMessage(Message.MSG_FOOTERCOURSECLEAR.getRawMessage());
                        player.sendMessage(Message.MSG_PAGEAMOUNT.getRawMessage()
                                .replace("{page}", Integer.toString(pageAmount))
                                .replace("{pagetotal}", Integer.toString(totalPageAmount)));

                    }

                } catch (SQLException throwables) { throwables.printStackTrace(); }

            }

        });

        return false;

    }

}
