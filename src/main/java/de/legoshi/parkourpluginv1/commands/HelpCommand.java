package de.legoshi.parkourpluginv1.commands;

import de.legoshi.parkourpluginv1.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

                if(!(sender instanceof Player)) return false;

                Player player = ((Player) sender).getPlayer();

                if(args.length != 0) {

                        player.sendMessage(Message.ERR_HELPCOMMAND.getMessage());

                } else {

                        //List of all Commands
                        player.sendMessage(Message.MSG_HELPHEADER.getRawMessage());

                        player.sendMessage(Message.MSG_HELPPPTOP.getRawMessage());
                        player.sendMessage(Message.MSG_HELPPPSTATS.getRawMessage());
                        player.sendMessage(Message.MSG_HELPPBESTPP.getRawMessage());
                        player.sendMessage(Message.MSG_HELPMAPTOP.getRawMessage());

                        player.sendMessage(Message.MSG_HELPFOOTER.getRawMessage());

                }

                return true;

        }

}
