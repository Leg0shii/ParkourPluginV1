package de.legoshi.parkourpluginv1.util;

import de.legoshi.parkourpluginv1.Main;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Inventory {

    public Inventory() { }

    //method for creating the Inventory in the parkour
    public void createParkourInventory(Player player) {

        player.getInventory().clear();
        Main instance = Main.getInstance();

        ItemStack redDye = instance.itemCreator.createItem(Material.INK_SACK, 1, ChatColor.RESET + "Checkpoint", (short) 1);
        ItemStack grayDye = instance.itemCreator.createItem(Material.INK_SACK, 1, ChatColor.RESET + "Restart", (short) 6);
        ItemStack purpleDye = instance.itemCreator.createItem(Material.INK_SACK, 1, ChatColor.RESET + "Back to Spawn", (short) 5);
        ItemStack glowStone = instance.itemCreator.createItem(Material.GLOWSTONE_DUST, 1, ChatColor.RESET + "Player Invisible");

        player.getInventory().setItem(0, redDye);
        player.getInventory().setItem(8, purpleDye);
        player.getInventory().setItem(2, grayDye);
        player.getInventory().setItem(6, glowStone);

        player.setGameMode(GameMode.ADVENTURE);
        player.updateInventory();

    }

    //method for creating the Inventory in the parkour
    public void createSpawnInventory(Player player) {

        player.getInventory().clear();
        Main instance = Main.getInstance();

        ItemStack netherStar = instance.itemCreator.createItem(Material.NETHER_STAR, 1, ChatColor.RESET + "Map Selector");
        ItemStack diamondPickaxe = instance.itemCreator.createItem(Material.DIAMOND_PICKAXE, 1, ChatColor.RESET + "Building");
        ItemStack glowStone = instance.itemCreator.createItem(Material.GLOWSTONE_DUST, 1, ChatColor.RESET + "Player Invisible");

        player.getInventory().setItem(0, netherStar);
        player.getInventory().setItem(6, glowStone);

        //removes potion effect
        for(PotionEffect potionEffect : player.getActivePotionEffects()) {

            if(!potionEffect.getType().equals(PotionEffectType.NIGHT_VISION)) {
                player.removePotionEffect(potionEffect.getType());
            }

        }

        player.getInventory().setItem(4, diamondPickaxe);
        player.setGameMode(GameMode.ADVENTURE);
        player.updateInventory();

    }

    public void builderInventory(Player player) {

        player.getInventory().clear();
        Main instance = Main.getInstance();

        ItemStack redDye = instance.itemCreator.createItem(Material.INK_SACK, 1, ChatColor.RESET + "Checkpoint", (short)1);
        ItemStack grayDye = instance.itemCreator.createItem(Material.INK_SACK, 1, ChatColor.RESET + "Back to Spawn", (short)5);

        player.getInventory().setItem(0, redDye);
        player.getInventory().setItem(8, grayDye);

        player.setGameMode(GameMode.CREATIVE);
        player.updateInventory();

    }

    public void replayInventory(Player player) {

        player.getInventory().clear();
        Main instance = Main.getInstance();

        ItemStack grayDye = instance.itemCreator.createItem(Material.INK_SACK, 1, ChatColor.RESET + "Back to Spawn", (short)5);
        ItemStack greenWool = instance.itemCreator.createItem(Material.WOOL, 1, ChatColor.RESET + "Start", (short)5);

        player.getInventory().setItem(8, grayDye);
        player.getInventory().setItem(4, greenWool);
        player.setGameMode(GameMode.CREATIVE);
        player.updateInventory();

    }

}
