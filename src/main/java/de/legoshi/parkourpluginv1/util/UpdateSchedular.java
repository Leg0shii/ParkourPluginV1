package de.legoshi.parkourpluginv1.util;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UpdateSchedular {

    public void onRun() {

        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {

            @Override
            public void run() {

                for(Player all : Bukkit.getOnlinePlayers()) {

                    Main instance = Main.getInstance();
                    PlayerObject playerObject = instance.playerManager.playerObjectHashMap.get(all);

                    if(playerObject.getPlayer().getWorld().equals("world")) {
                        instance.playerManager.updatePlaytimeOfPlayer(playerObject);
                    }

                }

            }

        }, 0, (1800*20));

    }

}
