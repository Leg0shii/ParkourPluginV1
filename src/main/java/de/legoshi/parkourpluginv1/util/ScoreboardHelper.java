package de.legoshi.parkourpluginv1.util;

import de.legoshi.parkourpluginv1.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.sql.ResultSet;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ScoreboardHelper {

    public void initializeScoreboard(Player player) {

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("rankedpk", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        scoreboard.registerNewTeam("noCollide").addEntry("noCollide_dummy");
        scoreboard.registerNewTeam("rank").addEntry(ChatColor.YELLOW + "" + ChatColor.WHITE);
        scoreboard.registerNewTeam("ppscore").addEntry(ChatColor.GRAY + "" + ChatColor.WHITE);
        scoreboard.registerNewTeam("fails").addEntry(ChatColor.GREEN + "" + ChatColor.WHITE);
        scoreboard.registerNewTeam("playtime").addEntry(ChatColor.DARK_GRAY + "" + ChatColor.WHITE);

        Bukkit.getConsoleSender().sendMessage("Scoreboard Created!");

        objective.setDisplayName("" + ChatColor.DARK_AQUA + ChatColor.BOLD + "RANKED PK");

        Team noCollide = scoreboard.getTeam("noCollide");
        noCollide.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        noCollide.addEntry(player.getName());

        //space
        objective.getScore(ChatColor.YELLOW + " ").setScore(13);

        //set ranks
        objective.getScore("" + ChatColor.GRAY + ChatColor.BOLD + "Rank").setScore(12);
        scoreboard.getTeam("rank").setPrefix("" + ChatColor.GRAY + "-");
        objective.getScore(ChatColor.YELLOW + "" + ChatColor.WHITE).setScore(11);

        //space
        objective.getScore(ChatColor.DARK_BLUE + " ").setScore(10);

        //set pp
        objective.getScore("" + ChatColor.GRAY + ChatColor.BOLD + "PP-Score").setScore(9);
        scoreboard.getTeam("ppscore").setPrefix("" + ChatColor.GRAY + "-");
        objective.getScore(ChatColor.GRAY + "" + ChatColor.WHITE).setScore(8);

        //space
        objective.getScore(ChatColor.BLUE + " ").setScore(7);

        //set fails
        objective.getScore("" + ChatColor.GRAY + ChatColor.BOLD + "Fails").setScore(6);
        scoreboard.getTeam("fails").setPrefix("" + ChatColor.GRAY + "-");
        objective.getScore(ChatColor.GREEN + "" + ChatColor.WHITE).setScore(5);

        //space
        objective.getScore(ChatColor.WHITE + " ").setScore(4);

        //set playtime
        objective.getScore("" + ChatColor.GRAY + ChatColor.BOLD + "Playtime").setScore(3);
        scoreboard.getTeam("playtime").setPrefix("" + ChatColor.GRAY + "-");
        objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.WHITE).setScore(2);

        player.setScoreboard(scoreboard);
        Bukkit.getConsoleSender().sendMessage("Scoreboard added to player!");

    }

    public void updateRankOnScoreBoard(Player player, int rank) {

        Main instance = Main.getInstance();
        String prefix = "";
        //prefix = instance.playerTag.getPrefix(rank);

        Scoreboard scoreboard = player.getScoreboard();
        scoreboard.getTeam("rank").setPrefix("" + ChatColor.WHITE + rank);

    }

    public void updatePPScoreOnScoreBoard(Player player, double pp) {

        Scoreboard scoreboard = player.getScoreboard();
        scoreboard.getTeam("ppscore").setPrefix("" + ChatColor.WHITE + String.format("%.2f", pp));

    }

    public void updateFailsOnScoreBoard(Player player, int fails) {

        Scoreboard scoreboard = player.getScoreboard();
        scoreboard.getTeam("fails").setPrefix(ChatColor.WHITE + "" + fails);

    }

    public void updatePlaytimeOnScoreBoard(Player player, long playtime) {

        Scoreboard scoreboard = player.getScoreboard();
        scoreboard.getTeam("playtime").setPrefix("" + ChatColor.WHITE
            + TimeUnit.HOURS.convert(playtime, TimeUnit.MILLISECONDS) + "h");

    }

}
