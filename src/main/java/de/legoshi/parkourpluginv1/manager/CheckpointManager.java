package de.legoshi.parkourpluginv1.manager;

import de.legoshi.parkourpluginv1.util.CheckpointObject;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CheckpointManager {

    public HashMap<Player, CheckpointObject> checkpointObjectHashMap;

    public CheckpointManager() {

        this.checkpointObjectHashMap = new HashMap<>();

    }

    public void playerJoin(Player player) {

        checkpointObjectHashMap.put(player, new CheckpointObject(player));

    }

}
