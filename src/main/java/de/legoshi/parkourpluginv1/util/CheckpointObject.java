package de.legoshi.parkourpluginv1.util;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CheckpointObject {

    Player player;
    Location location;

    public CheckpointObject(Player player) {

        this.player = player;
        this.location = new Location(player.getWorld(), 0, 0, 0);

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
