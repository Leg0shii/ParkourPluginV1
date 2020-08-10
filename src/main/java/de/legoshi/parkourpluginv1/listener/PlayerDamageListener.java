package de.legoshi.parkourpluginv1.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDamageListener implements Listener{

    @EventHandler
    public void onDamage(EntityDamageEvent e) {

        //Player player = (Player) e.getEntity();
        //player.sendMessage("Canceld: " + e.getDamage());

        e.setCancelled(true);

    }

}
