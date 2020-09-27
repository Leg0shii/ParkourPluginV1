package de.legoshi.parkourpluginv1.listener;

import de.legoshi.parkourpluginv1.Main;
import de.legoshi.parkourpluginv1.util.playerinformation.PlayerStatus;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.material.Door;
import org.bukkit.material.TrapDoor;

public class PlayerTDClick implements Listener {

			public void onTDClick(PlayerInteractEvent event) {

						PlayerStatus playerStatus = Main.getInstance().playerManager.playerObjectHashMap.get(event.getPlayer()).getPlayerStatus();

						if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !event.hasItem()
								&& event.getHand().equals(EquipmentSlot.HAND) && playerStatus.isBuildmode()) {

									event.getPlayer().sendMessage("" + event.getClickedBlock().getType().toString());

									if(event.getClickedBlock().getType().equals(Material.IRON_TRAPDOOR)) {

												BlockState state = event.getClickedBlock().getState();
												TrapDoor trapdoor = (TrapDoor)state.getData();
												trapdoor.setOpen(!trapdoor.isOpen());
												state.update();

									} else if(event.getClickedBlock().getType().equals(Material.IRON_DOOR_BLOCK)) {

												BlockState state = event.getClickedBlock().getState();
												Door door = (Door)state.getData();
												door.setOpen(!door.isOpen());
												state.update();

									}

						}

			}

}
