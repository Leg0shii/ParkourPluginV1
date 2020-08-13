package de.legoshi.parkourpluginv1.manager;

import net.minecraft.server.v1_12_R1.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitelManager {

			@SuppressWarnings("rawtypes")
			public void sendTitle(Player player, String msgTitle, String msgSubTitle, int ticks) {
						IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a((String)("{\"text\": \"" + msgTitle + "\"}"));
						IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer.a((String)("{\"text\": \"" + msgSubTitle + "\"}"));
						PacketPlayOutTitle p = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
						PacketPlayOutTitle p2 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubTitle);
						((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)p);
						((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)p2);
						sendTime(player, ticks);
			}

			@SuppressWarnings("rawtypes")
			private void sendTime(Player player, int ticks) {
						PacketPlayOutTitle p = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 20, ticks, 20);
						((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)p);
			}

			@SuppressWarnings("rawtypes")
			public void sendActionBar(Player player, String message) {
						IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a((String)("{\"text\": \"" + message + "\"}"));
						PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, ChatMessageType.GAME_INFO);
						((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)ppoc);
			}

}
