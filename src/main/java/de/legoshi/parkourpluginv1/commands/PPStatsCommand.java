package de.legoshi.parkourpluginv1.commands;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.Message;
import de.legoshi.parkourpluginv1.util.PlayerObject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class PPStatsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //checks if commandsender is a player
        if (!(sender instanceof Player)) {

            sender.sendMessage(Message.ERR_NoPlayer.getMessage());
            return true;

        }

        Player player = ((Player) sender).getPlayer();

        if (!(args.length == 0)) {

            player.sendMessage(Message.ERR_wrongCommandInput.getMessage());
            return true;

        }

        PlayerObject playerObject = Main.getInstance().playerManager.playerObjectHashMap.get(player);

        String failcount = Integer.toString(playerObject.getFailscount());
        String scorecount = Integer.toString(playerObject.getScorecount());
        String ranking = Integer.toString(playerObject.getRank());
        String playtime = Long.toString(TimeUnit.HOURS.convert(playerObject.getPlaytime(), TimeUnit.MILLISECONDS));

        player.sendMessage(Message.MSG_stats.getMessage().replace("{Player}", player.getDisplayName()));
        player.sendMessage(Message.MSG_ranking.getMessage().replace("{ranking}", ranking));
        player.sendMessage(Message.MSG_ppcount.getMessage().replace("{ppcount}", String.format("%.2f", playerObject.getPpcount())));
        player.sendMessage(Message.MSG_scorecount.getMessage().replace("{scorecount}", scorecount));
        player.sendMessage(Message.MSG_failcount.getMessage().replace("{failcount}", failcount));
        player.sendMessage(Message.MSG_playtime.getMessage().replace("{playtime}", playtime) + "h");

        return false;

    }

}
