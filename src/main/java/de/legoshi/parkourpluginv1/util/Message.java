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
    MSG_ANNOUNCEMENT_FAST("" + ChatColor.WHITE + ChatColor.BOLD + "{player}" + ChatColor.RESET +
        ChatColor.GRAY + " got #1 on" +
        ChatColor.WHITE + " {mapname}" +
        ChatColor.GRAY + " with a score of"+
        ChatColor.WHITE + ChatColor.BOLD + " {ppscore}!"),
    MSG_SHOWBESTTENFAILS("is {player} with {theme}: {score}pp."),
    MSG_ranking("Rank: {ranking}"),

    //helpmessages
    ERR_HELPCOMMAND("Please type /pphelp to get informations for the commands!"),
    MSG_HELPHEADER("\n" + ChatColor.WHITE + ChatColor.BOLD + "           Help" +
        "\n" + ChatColor.RESET + ChatColor.WHITE + ChatColor.STRIKETHROUGH + "                                   "),
    MSG_HELPFOOTER("" + ChatColor.WHITE + ChatColor.STRIKETHROUGH + "                                   "),
    MSG_HELPMAPTOP("/maptop - Displays top plays of a selected Map."),
    MSG_HELPPBESTPP("/bestpp - Shows your/others best PP-Scores."),
    MSG_HELPPPSTATS("/ppstats - Shows your personal Serverstats."),
    MSG_HELPPPTOP("/pptop - Shows the global Leaderboard for total PP."),

    //errormessages for commands
    ERR_MAPTOPCOMMAND("/maptop <mapID> <fails/pp/time>"),
    ERR_BESTPPCOMMAND("/bestpp <playername>"),
    ERR_PPSTATSCOMMAND("/ppstats"),
    ERR_PPTOPCOMMAND("/pptop"),
    ERR_PAGENOTEXIST("This page doesnt exist..."),

    //pageMessage
    MSG_PAGEAMOUNT(ChatColor.GRAY + "       Page " + ChatColor.WHITE + "{page}" + ChatColor.GRAY + "/" + "{pagetotal}");



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
