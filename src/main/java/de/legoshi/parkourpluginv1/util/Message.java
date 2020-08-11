package de.legoshi.parkourpluginv1.util;

import org.bukkit.ChatColor;

public enum Message {

    Prefix(ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + ChatColor.BOLD + "RPK" + ChatColor.RESET + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY),
    MSG_Welcome("Welcome to Ranked Parkour!"),
    MSG_FirstJoined("{Player} joined for the first time!"),
    MSG_Join("{Player} is now on the Server!"),
    MSG_Leave("{Player} left the Server!"),
    ERR_NoPermission("You dont have permission for that!"),
    ERR_NoPlayer("You have to be player for that!"),
    MSG_SetCP("CP set"),
    ERR_createRankedMap("/createRankedMap [id] [name] [difficulty] [minFails] [minTime]"),
    ERR_nameExist("Name already exists"),
    ERR_noNegative("No negative numbers allowed"),
    MSG_successfulCreatedMap("Successfully created new Ranked Map"),
    MSG_stats("--- Stats of {Player} ---"),
    MSG_playcount("Playcount: {playcount}"),
    MSG_failcount("Failcount: {failcount}"),
    MSG_ppcount("PP: {ppcount}"),
    MSG_playtime("Playtime: {playtime}"),
    MSG_scorecount("Score: {scorecount}"),
    MSG_accuracy("Accuracy: {accuracy}"),
    MSG_top5pp("#1: {pp1} \n #2: {pp2} \n #3: {pp3} \n #4: {pp4} \n #5: {pp5} \n"),
    ERR_wrongCommandInput("Wrong Syntax"),
    MYSQL_RECONNECT_FAILED("MYSQL Reconnect failed..."),
    MYSQL_RECONNECT_SUCCESS("MYSQL Reconnect success..."),
    MYSQL_ALREADY_CONNECTED("MYSQL already connected..."),
    MYSQL_RECONNECTING("MYSQL reconnecting..."),
    MYSQL_TRY_RECONNECT("MYSQL trying to reconnect..."),
    MSG_setCP("Your cp has been set!"),
    ERR_WrongInput("Wrong Input."),
    MSG_HEADERCOURSECLEAR("\n" + ChatColor.AQUA + ChatColor.BOLD + "     Global Ranking" +
        "\n" + ChatColor.RESET + ChatColor.AQUA + ChatColor.STRIKETHROUGH + "                                   " ),
    MSG_COURSECLEAR("{num}. {player}: " +
        ChatColor.DARK_AQUA + ChatColor.BOLD + "{ppscore}pp"),
    MSG_FOOTERCOURSECLEAR("" + ChatColor.AQUA + ChatColor.STRIKETHROUGH + "                                   "),
    MSG_ANNOUNCEMENT_FAST("" + ChatColor.GOLD + ChatColor.BOLD + "{player}" + ChatColor.RESET +
        ChatColor.GREEN + " got #1 on" +
        ChatColor.YELLOW + " {mapname} " +
        ChatColor.GREEN + " with a score of "+
        ChatColor.GOLD + " {ppscore}!"),
    MSG_SHOWBESTTENFAILS("is {player} with {theme}: {score}!"),
    MSG_ranking("Rank: {ranking}");


    String m;

    Message(String message) {
        this.m = message;
    }

    public String getMessage() {
        return Message.Prefix.getRawMessage() + this.m;
    }
    public String getRawMessage() {
        return this.m;
    }


}
