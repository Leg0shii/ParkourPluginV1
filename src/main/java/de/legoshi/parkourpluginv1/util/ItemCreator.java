package de.legoshi.parkourpluginv1.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemCreator {

			public ItemStack createItem(Material material, int amount, String itemName) {

						ItemStack itemStack = new ItemStack(material, amount);
						ItemMeta itemMeta = itemStack.getItemMeta();
						itemMeta.setDisplayName(itemName);
						itemStack.setItemMeta(itemMeta);


						return itemStack;

			}

			public ItemStack createItem(Material material, int amount, String itemName, List<String> lore) {

						ItemStack itemStack = new ItemStack(material, amount);
						ItemMeta itemMeta = itemStack.getItemMeta();
						itemMeta.setDisplayName(itemName);
						itemMeta.setLore(lore);
						itemStack.setItemMeta(itemMeta);

						return itemStack;

			}

			public ItemStack createItem(Material material, int amount, String itemName, short val) {

						ItemStack itemStack = new ItemStack(material, amount, val);
						ItemMeta itemMeta = itemStack.getItemMeta();
						itemMeta.setDisplayName(itemName);
						itemStack.setItemMeta(itemMeta);

						return itemStack;

			}

			public ItemStack createItem(Material material, int amount, String itemName, List<String> lore, short val) {

						ItemStack itemStack = new ItemStack(material, amount, val);
						ItemMeta itemMeta = itemStack.getItemMeta();
						itemMeta.setDisplayName(itemName);
						itemMeta.setLore(lore);
						itemStack.setItemMeta(itemMeta);

						return itemStack;

			}

}
