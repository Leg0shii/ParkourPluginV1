package de.legoshi.parkourpluginv1.commands;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.AsyncMySQL;
import de.legoshi.parkourpluginv1.util.Message;
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

        if (!(sender instanceof Player)) return true;

        Player player = ((Player) sender).getPlayer();

        if (args.length == 0) {

            mySQL.query("SELECT ppcountp, playername, playeruuid FROM tablename ORDER BY ppcountp DESC;", new Consumer<ResultSet>() {

                @Override
                public void accept(ResultSet resultSet) {

                    int i = 1;

                    try {

                        player.sendMessage(Message.MSG_HEADERCOURSECLEAR.getRawMessage());

                        while (i < 11 && resultSet.next()) {

                            player.sendMessage(Message.MSG_COURSECLEAR.getRawMessage().replace("{num}", Integer.toString(i))
                                .replace("{player}", resultSet.getString("playername"))
                                .replace("{ppscore}", Double.toString(resultSet.getDouble("ppcountp"))));

                            i++;

                        }

                        player.sendMessage(Message.MSG_FOOTERCOURSECLEAR.getRawMessage());

                    } catch (SQLException throwables) {

                        throwables.printStackTrace();

                    }

                }

            });

        } else {

            player.sendMessage(Message.ERR_wrongCommandInput.getMessage());
            return false;

        }

        return false;

    }

}
