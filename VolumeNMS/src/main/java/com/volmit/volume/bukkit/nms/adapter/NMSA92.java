package com.volmit.volume.bukkit.nms.adapter;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.volmit.volume.bukkit.VolumePlugin;
import com.volmit.volume.bukkit.nms.IAdapter;
import com.volmit.volume.bukkit.nms.IPacketHandler;
import com.volmit.volume.bukkit.nms.NMSAdapter;
import com.volmit.volume.bukkit.nms.TinyProtocol;
import com.volmit.volume.bukkit.pawn.Start;
import com.volmit.volume.bukkit.pawn.Stop;
import com.volmit.volume.bukkit.task.SR;
import com.volmit.volume.bukkit.util.net.Protocol;
import com.volmit.volume.lang.collections.FinalInteger;
import com.volmit.volume.lang.collections.GList;
import com.volmit.volume.lang.collections.GMap;
import com.volmit.volume.math.M;
import com.volmit.volume.reflect.V;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.PacketPlayOutMapChunk;
import net.minecraft.server.v1_9_R1.BlockPosition;
import net.minecraft.server.v1_9_R1.EntityAnimal;
import net.minecraft.server.v1_9_R1.EntityInsentient;
import net.minecraft.server.v1_9_R1.NavigationAbstract;
import net.minecraft.server.v1_9_R1.Packet;
import net.minecraft.server.v1_9_R1.PacketPlayInSettings;
import net.minecraft.server.v1_9_R1.PacketPlayOutCollect;
import net.minecraft.server.v1_9_R1.PathEntity;

public class NMSA92 extends NMSAdapter
{
	private TinyProtocol p;
	private GMap<Player, Integer> viewDistance;
	private GList<IPacketHandler> packetHandlers;

	public NMSA92()
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
		sendPacket(new PacketPlayOutCollect(drop.getEntityId(), who.getEntityId()), drop.getLocation());
	}

	@Override
	public void pathFind(LivingEntity e, Location l, boolean sprint, double speed)
	{
		if(!(e instanceof Creature))
		{
			return;
		}
		EntityInsentient le = ((CraftCreature) e).getHandle();
		NavigationAbstract na = le.getNavigation();
		PathEntity pe = na.a(new BlockPosition(l.getX(), l.getY(), l.getZ()));
		na.a(pe, speed);

		if(le instanceof EntityAnimal)
		{
			EntityAnimal a = (EntityAnimal) le;
			a.lastDamager = null;
			a.lastDamage = 0;
		}

		le.setSprinting(sprint);
		FinalInteger fe = new FinalInteger(100);

		new SR()
		{
			@Override
			public void run()
			{
				try
				{
					fe.sub(1);

					if(fe.get() < 0)
					{
						le.setSprinting(false);
						cancel();
						return;
					}

					if(e.isDead())
					{
						cancel();
						return;
					}

					if(pe.b())
					{
						cancel();

						if(sprint)
						{
							le.setSprinting(false);
						}
					}
				}

				catch(Exception e)
				{
					le.setSprinting(false);
					cancel();
				}
			}
		};
	}

	@Override
	public void sendChunkMap(AbstractChunk c, Player p)
	{
		try
		{
			PacketPlayOutMapChunk m = new PacketPlayOutMapChunk();
			new V(m).set("a", c.getX());
			new V(m).set("b", c.getZ());
			new V(m).set("c", c.getBitMask());
			new V(m).set("d", c.write());
			new V(m).set("e", new ArrayList<NBTTagCompound>());
			new V(m).set("f", c.isContinuous());
			sendPacket(m, p);
		}

		catch(Throwable e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void sendChunkMap(AbstractChunk c, Chunk area)
	{
		try
		{
			PacketPlayOutMapChunk m = new PacketPlayOutMapChunk();
			new V(m).set("a", c.getX());
			new V(m).set("b", c.getZ());
			new V(m).set("c", c.getBitMask());
			new V(m).set("d", c.write());
			new V(m).set("e", new ArrayList<NBTTagCompound>());
			new V(m).set("f", c.isContinuous());
			sendPacket(m, area);
		}

		catch(Throwable e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public AbstractChunk copy(Chunk c)
	{
		AbstractChunk as = new AbstractChunk();
		as.setX(c.getX());
		as.setZ(c.getZ());

		for(int i = 0; i < 16; i++)
		{
			for(int j = 0; j < 16; j++)
			{
				for(int k = 0; k < 256; k++)
				{
					Block b = c.getBlock(i, k, j);

					if(!b.isEmpty())
					{
						as.set(i, k, j, b.getTypeId(), b.getData());
						as.setBlockLight(i, k, j, b.getLightFromBlocks());
						as.setSkyLight(i, k, j, b.getLightFromSky());
					}
				}
			}
		}

		return as;
	}
}
