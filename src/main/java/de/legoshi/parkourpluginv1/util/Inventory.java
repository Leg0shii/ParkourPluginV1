package de.legoshi.parkourpluginv1.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Inventory {

    public Inventory() { }

    //method for creating the Inventory in the parkour
    public void createParkourInventory(Player player) {

        player.getInventory().clear();

        ItemStack redDye;
        ItemStack grayDye;
        ItemStack purpleDye;
        ItemMeta metaRedDye;
        ItemMeta metaGrayDye;
        ItemMeta metaPurpleDye;

        redDye = new ItemStack(Material.INK_SACK, 1, (short) 1);
        metaRedDye = redDye.getItemMeta();
        metaRedDye.setDisplayName(ChatColor.RESET + "Checkpoint");
        redDye.setItemMeta(metaRedDye);

        purpleDye = new ItemStack(Material.INK_SACK, 1, (short) 6);
        metaPurpleDye = purpleDye.getItemMeta();
        metaPurpleDye.setDisplayName(ChatColor.RESET + "Restart");
        purpleDye.setItemMeta(metaPurpleDye);

        grayDye = new ItemStack(Material.INK_SACK, 1, (short) 5);
        metaGrayDye = grayDye.getItemMeta();
        metaGrayDye.setDisplayName(ChatColor.RESET + "Back to Spawn");
        grayDye.setItemMeta(metaGrayDye);


        player.getInventory().setItem(0, redDye);
        player.getInventory().setItem(2, purpleDye);
        player.getInventory().setItem(8, grayDye);
        player.updateInventory();

    }

    //method for creating the Inventory in the parkour
    public void createSpawnInventory(Player player) {

        player.getInventory().clear();

        ItemStack netherStar;
        ItemMeta meta;

        netherStar = new ItemStack(Material.NETHER_STAR, 1);
        meta = netherStar.getItemMeta();
        meta.setDisplayName("Map Selector");
        netherStar.setItemMeta(meta);

        player.getInventory().setItem(0, netherStar);
        player.updateInventory();

    }

}
