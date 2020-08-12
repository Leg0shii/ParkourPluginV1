package de.legoshi.parkourpluginv1.commands;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.Message;
import de.legoshi.parkourpluginv1.util.PlayerObject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.legoshi.parkourpluginv1.util.ChatColorHelper;

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

            player.sendMessage(Message.ERR_PPSTATSCOMMAND.getMessage());
            return true;

        }

        PlayerObject playerObject = Main.getInstance().playerManager.playerObjectHashMap.get(player);

        String failcount = Integer.toString(playerObject.getFailscount());
        String scorecount = Integer.toString(playerObject.getScorecount());
        String ranking = Integer.toString(playerObject.getRank());
        String playtime = Long.toString(TimeUnit.HOURS.convert(playerObject.getPlaytime(), TimeUnit.MILLISECONDS));

        player.sendMessage(ChatColorHelper.chat(Message.MSG_STATS_HEADER.getRawMessage().replace("{player}", player.getDisplayName())));
        player.sendMessage(ChatColorHelper.chat(Message.MSG_STATS_RANKING.getRawMessage().replace("{number}", ranking)));
        player.sendMessage(ChatColorHelper.chat(Message.MSG_STATS_PP.getRawMessage().replace("{number}", String.format("%.2f", playerObject.getPpcount()))));
        player.sendMessage(ChatColorHelper.chat(Message.MSG_STATS_SCORE.getRawMessage().replace("{number}", scorecount)));
        player.sendMessage(ChatColorHelper.chat(Message.MSG_STATS_FAILS.getRawMessage().replace("{number}", failcount)));
        player.sendMessage(ChatColorHelper.chat(Message.MSG_STATS_PLAYTIME.getRawMessage().replace("{number}", playtime)));
        player.sendMessage(ChatColorHelper.chat(Message.MSG_STATS_FOOTER.getRawMessage()));


        return false;

    }

}
