package de.legoshi.parkourpluginv1.util;

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
        ItemMeta metaRedDye;
        ItemMeta metaGrayDye;

        redDye = new ItemStack(Material.INK_SACK, 1, (short) 1);
        metaRedDye = redDye.getItemMeta();
        metaRedDye.setDisplayName("Checkpoint");
        redDye.setItemMeta(metaRedDye);

        grayDye = new ItemStack(Material.INK_SACK, 1, (short) 5);
        metaGrayDye = grayDye.getItemMeta();
        metaGrayDye.setDisplayName("Back to Spawn");
        grayDye.setItemMeta(metaGrayDye);


        player.getInventory().setItem(0, redDye);
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
