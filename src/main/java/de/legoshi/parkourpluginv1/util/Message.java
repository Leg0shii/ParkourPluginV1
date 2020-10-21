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
    ERR_createRankedMap("/createRankedMap [name] [difficulty] [minFails] [minTime] [builder]"),
    ERR_nameExist("Name already exists"),
    ERR_noNegative("No negative numbers allowed"),
    MSG_successfulCreatedMap("Successfully created new Ranked Map"),
    ERR_wrongCommandInput("Wrong Syntax"),
    MYSQL_RECONNECT_FAILED("MYSQL Reconnect failed..."),
    MYSQL_RECONNECT_SUCCESS("MYSQL Reconnect success..."),
    MYSQL_ALREADY_CONNECTED("MYSQL already connected..."),
    MYSQL_RECONNECTING("MYSQL reconnecting..."),
    MYSQL_TRY_RECONNECT("MYSQL trying to reconnect..."),
    MSG_setCP("Your cp has been set!"),
    ERR_WrongInput("Wrong Input."),
    MSG_JOINED_COURSE("You selected the course: " +ChatColor.WHITE+ "{mapname}"),

    //pptop command
    MSG_HEADERCOURSECLEAR(
        "\n&8&m                                            " +
        "\n&7&lGlobal Ranking"),
    MSG_COURSECLEAR("{color}{num}. {player} &6{ppscore}&fpp"),
    MSG_FOOTERCOURSECLEAR("&8&m                                            "),

    // ppstats command
    MSG_STATS_HEADER(
        "\n&8&m                                            " +
        "&r\n &7&l{player}'s Stats"),
    MSG_STATS_RANKING(" Rank: #{number}"),
    MSG_STATS_PP(" PP: {number}pp"),
    MSG_STATS_FAILS(" Fails: {number} fails"),
    MSG_STATS_PLAYTIME(" Playtime: {number}h"),
    MSG_STATS_FOOTER("&8&m                                            "),

    // bestpp command
    MSG_BEST_HEADER(
            "\n&8&m                                            " +
                    "&r\n &7&l{player}'s Top Scores"),
    MSG_BEST_FORMAT(" {num}. {map} {maprank} &f{acc}%: &a{time}&fs - &c{fails} &ffails - &6{pp}&fpp"),
    MSG_BEST_FOOTER("&8&m                                            "),

    // maptop command
    MSG_MAPTOP_HEADER(
            "\n&8&m                                            " +
                    "&r\n &7&l{map} Top {type}"),
    MSG_MAPTOP_FAILS("&c{num} &ffails"),
    MSG_MAPTOP_PP("&6{num}&fpp"),
    MSG_MAPTOP_TIMES("&a{num}&fs"),
    MSG_MAPTOP_FORMAT(" {num}. {name} {maprank} &f{acc}%: &a{time}&fs - &c{fails} &ffails - &6{pp}&fpp"),
    MSG_MAPTOP_FOOTER("&8&m                                            "),

    //announcement
    MSG_ANNOUNCEMENT_FAST("" + ChatColor.WHITE + ChatColor.BOLD + "{player}" + ChatColor.RESET +
        ChatColor.GRAY + " got #1 on" +
        ChatColor.WHITE + " {mapname}" +
        ChatColor.GRAY + " with a score of"+
        ChatColor.WHITE + ChatColor.BOLD + " {ppscore}!"),
    MSG_SHOWBESTTENFAILS("is {player} with {theme}: {score}pp."),
    MSG_ranking("Rank: {ranking}"),

    //helpmessages
    ERR_HELPCOMMAND("Please type /pphelp to get informations for the commands!"),
    MSG_HELPHEADER("\n" + ChatColor.RESET + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "                                            " +
        "\n" + ChatColor.GRAY + ChatColor.BOLD + " Help"),
    MSG_HELPFOOTER("" + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "                                            "),
    MSG_HELPMAPTOP(" /pptopmap <mapID> [page] - Shows top plays of a selected Map."),
    MSG_HELPPBESTPP(" /ppbest <name> [page] - Shows best PP-Scores."),
    MSG_HELPPPSTATS(" /ppstats - Shows your personal Serverstats."),
    MSG_HELPPPTOP("\n /pptop [page] - Shows the global Leaderboard for total PP."),
    MSG_HELPNV(" /nv - gives/removes nightvision"),
    MSG_HELPPPMAP(" /ppmap <name/difficulty/status> <mapid> <fact>"),
    MSG_HELPBRACKETS("Please note that arguments with [ ] are optional."),

    //ppmap
    MSG_PPMAP_SET_NAME("Successfully changed Mapname to {mapname}!"),
    MSG_PPMAP_SET_STATUS("Successfully changed Mapstatus to {mapstatus}!"),
    MSG_PPMAP_SET_DIFFICULTY("Successfully changed difficulty to {difficulty}!"),
    MSG_SETSPAWN("Successfully set new Spawnpoint"),

    //errormessages for commands
    ERR_MAPTOPCOMMAND("/pptopmap <mapID> [page]"),
    ERR_BESTPPCOMMAND("/ppbest <playername> [page]"),
    ERR_PPSTATSCOMMAND("/ppstats"),
    ERR_PPTOPCOMMAND("/pptop [page]"),
    ERR_PPMAP("/ppmap <name/status/difficulty> <mapid> <value>"),
    ERR_PAGENOTEXIST("This page doesnt exist..."),
    ERR_NOTANUMBER("Please enter a number..."),
    ERR_INVITE("/invite <playername>"),
    ERR_ACCEPT("/accept <playername>"),
    ERR_NOT_YOURSELF("You cant use your own name here."),

    //inviteCommand
    ERR_INVITE_NOT_ONLINE("{playername} is not online."),
    ERR_INVITE_NOT_MAINWORLD("{playername} is not in the Lobby."),
    ERR_INVITE_NOT_MAP("You are not in a build map."),
    MSG_INVITE_PLAYER("{playername} invited you to his Course."),
    MSG_SUCC_INVITE("{playername} invited successfully."),

    //acceptCommand
    ERR_ACCEPT_PLAYER_LEFT("{playername} isnt online anymore."),
    ERR_ACCEPT_PLAYER_NOT_WORLD("{playername} is not in his world anymore"),
    ERR_ACCEPT_NOT_INV("This player didnt invite you!"),
    ERR_ACCEPT_NOT_LOBBY("You are not in the Lobby!"),

    //pageMessage
    MSG_PAGEAMOUNT(ChatColor.GRAY + "       Page " + ChatColor.WHITE + "{page}" + ChatColor.GRAY + "/" + "{pagetotal}"),

    //nvMessage
    MSG_NV_ACTIVATE("Nightvision activated!"),
    MSG_NV_DEACTIVATE("Nightvision deactivated!"),

    //setspawn
    MSG_SUCC_SET_SPAWN("Successfully changed Spawn of Map"),

    //resultscreen
    MSG_RESULT_HEADER("" + ChatColor.BOLD + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "                                            "
    + ChatColor.RESET + ChatColor.GRAY + ChatColor.BOLD + "\n You completed the course " + ChatColor.WHITE + "{mapname}!\n"),
    MSG_RESULT_SCREEN(
          ChatColor.RESET + "Rank: {performance} "+ChatColor.GRAY +"(#{rank})\n"
        + ChatColor.RESET + "Accuracy:  {newacc}% " + ChatColor.GRAY + " ({oldacc}%) \n"
        + ChatColor.RESET + "PP-Score:  {newpp}pp " + ChatColor.GRAY + " ({oldpp}pp) \n"
        + ChatColor.RESET + "Time:  {newTime}s " + ChatColor.GRAY + " ({oldTime}s) \n"
        + ChatColor.RESET + "Fails:  {newFails} " + ChatColor.GRAY + " ({oldFails})"),
    MSG_RESUT_FOOTER("" + ChatColor.BOLD + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "                                            ");


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
