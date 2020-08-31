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
        ItemStack diamondPickaxe;
        ItemMeta diamondPickaxeMeta;
        ItemMeta netherStarMeta;

        netherStar = new ItemStack(Material.NETHER_STAR, 1);
        netherStarMeta = netherStar.getItemMeta();
        netherStarMeta.setDisplayName(ChatColor.RESET + "Map Selector");
        netherStar.setItemMeta(netherStarMeta);

        diamondPickaxe = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        diamondPickaxeMeta = diamondPickaxe.getItemMeta();
        diamondPickaxeMeta.setDisplayName(ChatColor.RESET + "Building");
        diamondPickaxe.setItemMeta(diamondPickaxeMeta);

        player.getInventory().setItem(0, netherStar);
        player.getInventory().setItem(4, diamondPickaxe);
        player.updateInventory();

    }

    public void builderInventory(Player player) {

        player.getInventory().clear();

        ItemStack grayDye;
        ItemStack redDye;
        ItemMeta metaGrayDye;
        ItemMeta metaRedDye;

        grayDye = new ItemStack(Material.INK_SACK, 1, (short) 5);
        metaGrayDye = grayDye.getItemMeta();
        metaGrayDye.setDisplayName(ChatColor.RESET + "Back to Spawn");
        grayDye.setItemMeta(metaGrayDye);

        redDye = new ItemStack(Material.INK_SACK, 1, (short) 1);
        metaRedDye = redDye.getItemMeta();
        metaRedDye.setDisplayName(ChatColor.RESET + "Checkpoint");
        redDye.setItemMeta(metaRedDye);

        player.getInventory().setItem(0, redDye);
        player.getInventory().setItem(8, grayDye);
        player.updateInventory();

    }

}
