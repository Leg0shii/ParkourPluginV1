package de.legoshi.parkourpluginv1.commands;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.Message;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerObject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class NightVisionCommand implements CommandExecutor {

			@Override
			public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

						if(!(sender instanceof Player)) return false;

						Player player = ((Player) sender).getPlayer();
						PlayerObject playerObject = Main.getInstance().playerManager.playerObjectHashMap.get(player);
						if(!playerObject.getPlayerStatus().isNightvision()) {

									player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100000, 1));
									playerObject.getPlayerStatus().setNightvision(true);
									player.sendMessage(Message.MSG_NV_ACTIVATE.getMessage());

						} else {

									player.removePotionEffect(PotionEffectType.NIGHT_VISION);
									playerObject.getPlayerStatus().setNightvision(false);
									player.sendMessage(Message.MSG_NV_DEACTIVATE.getMessage());

						}

						return false;

			}

}
