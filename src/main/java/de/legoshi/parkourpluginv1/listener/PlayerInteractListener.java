package de.legoshi.parkourpluginv1.listener;

import de.legoshi.parkourpluginv1.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        Main instance = Main.getInstance();

        instance.playerInteractManager.getPlayerStepPressureplate().onPressureplatStep(event);
        instance.playerInteractManager.getNetherStarClick().onNetherStarClick(event);
        instance.playerInteractManager.getRedDyeClick().onReDyeClick(event);
        instance.playerInteractManager.getPlayerGrayDyeClick().onGrayClick(event);
        instance.playerInteractManager.getPlayerCyanDyeClick().onCyanDyeInventoryClick(event);

    }

}
