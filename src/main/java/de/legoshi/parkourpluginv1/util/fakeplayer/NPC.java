package de.legoshi.parkourpluginv1.util.fakeplayer;

import net.minecraft.server.v1_12_R1.*;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo.*;
import org.bukkit.Location;

import org.bukkit.craftbukkit.v1_12_R1.util.CraftChatMessage;

import com.mojang.authlib.GameProfile;

import java.util.List;
import java.util.UUID;

import com.mojang.authlib.properties.Property;
import org.bukkit.entity.Player;


public class NPC extends Reflections {

			private int entityID;
			private Location location;
			private GameProfile gameprofile;

			public NPC(String name,Location location){

						entityID = (int)Math.ceil(Math.random() * 1000) + 2000;
						gameprofile = new GameProfile(UUID.randomUUID(), name);
						changeSkin();
						this.location = location.clone();

			}

			public void changeSkin(){

						String value = "eyJ0aW1lc3RhbXAiOjE0NDI4MzY1MTU1NzksInByb2ZpbGVJZCI6IjkwZWQ3YWY0NmU4YzRkNTQ4MjRkZTc0YzI1MTljNjU1IiwicHJvZmlsZU5hbWUiOiJDb25DcmFmdGVyIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8xMWNlZDMzMjNmYjczMmFjMTc3MTc5Yjg5NWQ5YzJmNjFjNzczZWYxNTVlYmQ1Y2M4YzM5NTZiZjlhMDlkMTIifX19";
						String signature = "tFGNBQNpxNGvD27SN7fqh3LqNinjJJFidcdF8LTRHOdoMNXcE5ezN172BnDlRsExspE9X4z7FPglqh/b9jrLFDfQrdqX3dGm1cKjYbvOXL9BO2WIOEJLTDCgUQJC4/n/3PZHEG2mVADc4v125MFYMfjzkznkA6zbs7w6z8f7pny9eCWNXPOQklstcdc1h/LvflnR+E4TUuxCf0jVsdT5AZsUYIsJa6fvr0+vItUXUdQ3pps0zthObPEnBdLYMtNY3G6ZLGVKcSGa/KRK2D/k69fmu/uTKbjAWtniFB/sdO0VNhLuvyr/PcZVXB78l1SfBR88ZMiW6XSaVqNnSP+MEfRkxgkJWUG+aiRRLE8G5083EQ8vhIle5GxzK68ZR48IrEX/JwFjALslCLXAGR05KrtuTD3xyq2Nut12GCaooBEhb46sipWLq4AXI9IpJORLOW8+GvY+FcDwMqXYN94juDQtbJGCQo8PX670YjbmVx7+IeFjLJJTZotemXu1wiQmDmtAAmug4U5jgMYIJryXMitD7r5pEop/cw42JbCO2u0b5NB7sI/mr4OhBKEesyC5usiARzuk6e/4aJUvwQ9nsiXfeYxZz8L/mh6e8YPJMyhVkFtblbt/4jPe0bs3xSUXO9XrDyhy9INC0jlLT22QjNzrDkD8aiGAopVvfnTTAug=";
						gameprofile.getProperties().put("textures", new Property("textures", value, signature));

			}


			public void animation(int animation){

						PacketPlayOutAnimation packet = new PacketPlayOutAnimation();
						setValue(packet, "a", entityID);
						setValue(packet, "b", (byte)animation);
						sendPacket(packet);

			}

			public void status(int status){

						PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus();
						setValue(packet, "a", entityID);
						setValue(packet, "b", (byte)status);
						sendPacket(packet);

			}

			public void spawn(Player player){

						PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();

						DataWatcher w = new DataWatcher(null);
						w.register(new DataWatcherObject<>(13,DataWatcherRegistry.a),(byte)127);
						//w.register(new DataWatcherObject<>(6, DataWatcherRegistry.a), (float) 20);

						setValue(packet, "a", this.entityID);
						setValue(packet, "b", this.gameprofile.getId());
						setValue(packet, "c", this.location.getX());
						setValue(packet, "d", this.location.getY());
						setValue(packet, "e", this.location.getZ());
						setValue(packet, "f", getFixRotation(this.location.getYaw()));
						setValue(packet, "g", getFixRotation(this.location.getPitch()));
						setValue(packet, "h", w);

						addToTablist(player);
						sendPacket(packet, player);
						headRotation(location.getYaw(), location.getPitch(), player);

			}

			public void teleport(Location location, Player player){

						PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport();

						setValue(packet, "a", this.entityID);
						setValue(packet, "b", this.location.getX());
						setValue(packet, "c", this.location.getY());
						setValue(packet, "d", this.location.getZ());
						setValue(packet, "e", getFixRotation(this.location.getYaw()));
						setValue(packet, "f", getFixRotation(this.location.getPitch()));

						sendPacket(packet, player);
						headRotation(location.getYaw(), location.getPitch(), player);
						this.location = location.clone();

			}

			public void headRotation(float yaw,float pitch, Player player){

						PacketPlayOutEntity.PacketPlayOutEntityLook packet = new PacketPlayOutEntity.PacketPlayOutEntityLook(entityID, getFixRotation(yaw),getFixRotation(pitch) , true);
						PacketPlayOutEntityHeadRotation packetHead = new PacketPlayOutEntityHeadRotation();
						setValue(packetHead, "a", entityID);
						setValue(packetHead, "b", getFixRotation(yaw));

						sendPacket(packet, player);
						sendPacket(packetHead, player);

			}

			public void destroy(Player player){

						PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] {entityID});
						rmvFromTablist(player);
						sendPacket(packet, player);

			}

			public void addToTablist(Player player){

						PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
						PlayerInfoData data = packet.new PlayerInfoData(gameprofile, 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameprofile.getName())[0]);
						@SuppressWarnings("unchecked")
						List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PlayerInfoData>) getValue(packet, "b");
						players.add(data);

						setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);
						setValue(packet, "b", players);

						sendPacket(packet, player);

			}

			public void rmvFromTablist(Player player){

						PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
						PlayerInfoData data = packet.new PlayerInfoData(gameprofile, 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameprofile.getName())[0]);
						@SuppressWarnings("unchecked")
						List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PlayerInfoData>) getValue(packet, "b");
						players.add(data);

						setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);
						setValue(packet, "b", players);

						sendPacket(packet, player);

			}

			public int getFixLocation(double pos){
						return (int)MathHelper.floor(pos * 32.0D);
			}

			public byte getFixRotation(float yawpitch){
						return (byte) ((int) (yawpitch * 256.0F / 360.0F));
			}

}
