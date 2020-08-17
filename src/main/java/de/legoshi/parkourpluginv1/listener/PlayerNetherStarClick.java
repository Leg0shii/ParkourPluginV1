package de.legoshi.parkourpluginv1.listener;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.AsyncMySQL;
import de.legoshi.parkourpluginv1.util.MapObject;
import de.legoshi.parkourpluginv1.util.Message;
import de.legoshi.parkourpluginv1.util.PlayerObject;
import de.themoep.inventorygui.GuiElementGroup;
import de.themoep.inventorygui.GuiPageElement;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerNetherStarClick {

    @EventHandler
    public void onNetherStarClick(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        AsyncMySQL mySQL = Main.getInstance().mySQL;
        Action action = event.getAction();
        Main instance = Main.getInstance();
        ArrayList<MapObject> mapObjectArrayList = instance.mapObjectMananger.getMapObjectArrayList();

        //sorts maps
        mapObjectArrayList.sort(Comparator.comparing(MapObject::getDifficulty));

        if ((action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) && event.hasItem()) {

            if (!event.getItem().getType().equals(Material.NETHER_STAR)) {
                return;
            }

            //prevents calling function with offhand
            if(event.getHand() == EquipmentSlot.OFF_HAND) return;

            String[] guiSetup = {
                "ggggggggg",
                "ggggggggg",
                "  fpdnl  "
            };

            InventoryGui gui = new InventoryGui(Main.getInstance(), player, "Map Selection", guiSetup);

            // First page
            gui.addElement(new GuiPageElement('f', new ItemStack(Material.ARROW), GuiPageElement.PageAction.FIRST, "Go to first page (current: %page%)"));

            // Previous page
            gui.addElement(new GuiPageElement('p', new ItemStack(Material.SIGN), GuiPageElement.PageAction.PREVIOUS, "Go to previous page (%prevpage%)"));

            // Next page
            gui.addElement(new GuiPageElement('n', new ItemStack(Material.SIGN), GuiPageElement.PageAction.NEXT, "Go to next page (%nextpage%)"));

            // Last page
            gui.addElement(new GuiPageElement('l', new ItemStack(Material.ARROW), GuiPageElement.PageAction.LAST, "Go to last page (%pages%)"));


            GuiElementGroup group = new GuiElementGroup('g');

            for (MapObject maps : mapObjectArrayList) {

                ItemStack material = null;
                PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);
                double difficulty = maps.getDifficulty();
                int mapId = maps.getID();

                //creates the block that is displaed in the GUI depending on the difficulty
                if(difficulty <= 1.99) material = new ItemStack(Material.WOOL, 1, (short) 5);
                else if(difficulty <= 2.69) material = new ItemStack(Material.WOOL, 1, (short) 3);
                else if(difficulty <= 3.99) material = new ItemStack(Material.WOOL, 1, (short) 4);
                else if(difficulty <= 5.29) material = new ItemStack(Material.WOOL, 1, (short) 6);
                else if(difficulty <= 6.49) material = new ItemStack(Material.WOOL, 1, (short) 10);
                else if(difficulty >= 6.50) material = new ItemStack(Material.WOOL, 1, (short) 15);

                String title = maps.getName();
                // Add an element to the group
                // Elements are in the order they got added to the group and don't need to have the same type.
                group.addElement((new StaticGuiElement('e', new ItemStack(material),
                    click -> {

                        playerObject.setJumpmode(true);
                        playerObject.setFailsrelative(0);
                        playerObject.setMapObject(maps);
                        playerObject.setTimerelative(0);
                        instance.inventory.createParkourInventory(event.getPlayer());

                        click.getEvent().getWhoClicked().sendMessage(Message.MSG_JOINED_COURSE.getMessage().replace("{mapname}", maps.getName()));
                        click.getEvent().getWhoClicked().teleport(maps.getSpawn());
                        playerObject.setTimer(timer(player));

                        //adding mapattempt into db
                        instance.mapObjectMananger.firstPlayerMapLoad(player, maps);

                        return true; // returning true will cancel the click event and stop taking the item

                    },
                    "" + ChatColor.RESET + ChatColor.BOLD + title,
                        "" + ChatColor.RESET + ChatColor.GRAY + "Builder: " + ChatColor.GOLD + " - \n" +
                        "" + ChatColor.RESET + ChatColor.GRAY + "Maptype: " + ChatColor.GOLD + maps.getMapType() + "\n" +
                        ChatColor.RESET + ChatColor.GRAY + "Difficulty: " + instance.playerTag.difficultyString(difficulty) + ChatColor.DARK_GRAY + " (" +difficulty + ")\n" +
                        ChatColor.RESET + ChatColor.GRAY + "MapID: " + ChatColor.GOLD + mapId +
                        "\n\n" +
                        ChatColor.RESET + ChatColor.GRAY +ChatColor.GRAY + "------[ Map-Judge ]------\n" +
                        ChatColor.RESET + ChatColor.GRAY + "Min-Fails: " + ChatColor.GOLD + maps.getMinFails() + "\n" +
                        ChatColor.RESET + ChatColor.GRAY + "Min-Time: " + ChatColor.GOLD + maps.getMinTime()
                )));
            }
            gui.addElement(group);
            gui.show(player);

        }

    }

    public Timer timer(Player player) {

        Timer timer = new Timer();
        Main instance = Main.getInstance();
        PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(player);

            timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {

                if(!(Main.getInstance().playerManager.playerObjectHashMap.get(player).isJumpmode())) {

                    timer.cancel();

                }

                //outputs the time and current pp of player over hotbar
                double currentPP = instance.playerStepPressureplate.calculatePPFromMap(playerObject);
                double currentTime = playerObject.getTimerelative();

                instance.titelManager.sendActionBar(player,
                    ChatColor.BLUE + "Time: " + ChatColor.GRAY + String.format("%.3f",currentTime) + ChatColor.BLUE + " || Current PP: " + ChatColor.GRAY + String.format("%.2f", currentPP));

                playerObject.setTimerelative(playerObject.getTimerelative() + 0.05);

            }

        }, 0, 50);

        return timer;

    }

}
