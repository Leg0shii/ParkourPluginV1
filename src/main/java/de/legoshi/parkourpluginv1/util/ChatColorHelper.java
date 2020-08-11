package de.legoshi.parkourpluginv1.util;

import org.bukkit.ChatColor;

public class ChatColorHelper {

    static public String chat(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}