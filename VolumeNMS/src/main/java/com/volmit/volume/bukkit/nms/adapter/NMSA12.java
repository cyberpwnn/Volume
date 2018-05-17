package com.volmit.volume.bukkit.nms.adapter;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.volmit.volume.bukkit.VolumePlugin;
import com.volmit.volume.bukkit.nms.IAdapter;
import com.volmit.volume.bukkit.nms.IPacketHandler;
import com.volmit.volume.bukkit.nms.NMSAdapter;
import com.volmit.volume.bukkit.nms.TinyProtocol;
import com.volmit.volume.bukkit.pawn.Start;
import com.volmit.volume.bukkit.pawn.Stop;
import com.volmit.volume.bukkit.util.net.Protocol;
import com.volmit.volume.lang.collections.GList;
import com.volmit.volume.lang.collections.GMap;
import com.volmit.volume.math.M;
import com.volmit.volume.reflect.V;

import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayInSettings;
import net.minecraft.server.v1_12_R1.PacketPlayOutCollect;

public class NMSA12 extends NMSAdapter
{
	private TinyProtocol p;
	private GMap<Player, Integer> viewDistance;
	private GList<IPacketHandler> packetHandlers;

	public NMSA12()
	{
		super(Protocol.R1_12, Protocol.R1_12_2);
		packetHandlers = new GList<IPacketHandler>();
		viewDistance = new GMap<Player, Integer>();
	}

	@Start
	public void start()
	{
		p = new TinyProtocol(VolumePlugin.vpi)
		{
			@Override
			public Object onPacketOutAsync(Player reciever, Object packet)
			{
				for(IPacketHandler i : packetHandlers)
				{
					packet = i.onPacketOutAsync(reciever, packet);
				}

				return super.onPacketOutAsync(reciever, packet);
			}

			@Override
			public Object onPacketInAsync(Player sender, Object packet)
			{
				if(packet instanceof PacketPlayInSettings)
				{
					PacketPlayInSettings s = (PacketPlayInSettings) packet;
					int v = new V(s).get("b");
					viewDistance.put(sender, v);
				}

				for(IPacketHandler i : packetHandlers)
				{
					packet = i.onPacketInAsync(sender, packet);
				}

				return super.onPacketInAsync(sender, packet);
			}
		};
	}

	@Stop
	public void stop()
	{
		p.close();
	}

	@Override
	public void sendPacket(Object packet)
	{
		for(Player i : Bukkit.getOnlinePlayers())
		{
			sendPacket(packet, i);
		}
	}

	@Override
	public void sendPacket(Object packet, World world)
	{
		for(Player i : world.getPlayers())
		{
			sendPacket(packet, i);
		}
	}

	@Override
	public void sendPacket(Object packet, Player player)
	{
		((CraftPlayer) player).getHandle().playerConnection.sendPacket((Packet<?>) packet);
	}

	@Override
	public void sendPacket(Object packet, Location location)
	{
		for(Player i : location.getWorld().getPlayers())
		{
			if(canSee(i, location))
			{
				sendPacket(packet, i);
			}
		}
	}

	@Override
	public void sendPacket(Object packet, Chunk chunk)
	{
		for(Player i : chunk.getWorld().getPlayers())
		{
			if(canSee(i, chunk))
			{
				sendPacket(packet, i);
			}
		}
	}

	@Override
	public int getViewDistance(Player player)
	{
		if(!viewDistance.containsKey(player))
		{
			return Bukkit.getServer().getViewDistance();
		}

		return (int) M.clip(viewDistance.get(player), 1, Bukkit.getServer().getViewDistance());
	}

	@Override
	public boolean canSee(Player player, Location location)
	{
		return IAdapter.isWithin(player.getLocation().getChunk(), location.getChunk(), getViewDistance(player));
	}

	@Override
	public boolean canSee(Player player, Chunk chunk)
	{
		return IAdapter.isWithin(player.getLocation().getChunk(), chunk, getViewDistance(player));
	}

	@Override
	public void addPacketHandler(IPacketHandler h)
	{
		packetHandlers.add(h);
	}

	@Override
	public void removePacketHandler(IPacketHandler h)
	{
		packetHandlers.remove(h);
	}

	@Override
	public void sendPickup(Entity drop, Entity who)
	{
		sendPacket(new PacketPlayOutCollect(drop.getEntityId(), who.getEntityId(), 1), drop.getLocation());
	}
}
