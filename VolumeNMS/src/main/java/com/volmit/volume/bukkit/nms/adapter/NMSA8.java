package com.volmit.volume.bukkit.nms.adapter;

import java.util.Collection;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.volmit.volume.bukkit.VolumePlugin;
import com.volmit.volume.bukkit.nms.IAdapter;
import com.volmit.volume.bukkit.nms.IPacketHandler;
import com.volmit.volume.bukkit.nms.NMSAdapter;
import com.volmit.volume.bukkit.nms.TinyProtocol;
import com.volmit.volume.bukkit.pawn.Start;
import com.volmit.volume.bukkit.pawn.Stop;
import com.volmit.volume.bukkit.task.SR;
import com.volmit.volume.bukkit.util.net.Protocol;
import com.volmit.volume.bukkit.util.text.C;
import com.volmit.volume.bukkit.util.world.MaterialBlock;
import com.volmit.volume.lang.collections.FinalInteger;
import com.volmit.volume.lang.collections.GList;
import com.volmit.volume.lang.collections.GMap;
import com.volmit.volume.lang.collections.GSet;
import com.volmit.volume.math.M;
import com.volmit.volume.reflect.V;

import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityAnimal;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.IScoreboardCriteria.EnumScoreboardHealthDisplay;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NavigationAbstract;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayInEntityAction;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInSettings;
import net.minecraft.server.v1_8_R3.PacketPlayOutCollect;
import net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity.PacketPlayOutRelEntityMove;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.PacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunk;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore.EnumScoreboardAction;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_8_R3.PathEntity;
import net.minecraft.server.v1_8_R3.TileEntity;

public class NMSA8 extends NMSAdapter
{
	private TinyProtocol p;
	private GMap<Player, Integer> viewDistance;
	private GList<IPacketHandler> packetHandlers;

	public NMSA8()
	{
		super(Protocol.R1_12, Protocol.R1_12_2);
		packetHandlers = new GList<IPacketHandler>();
		viewDistance = new GMap<Player, Integer>();
	}

	@Override
	public void sendBrand(Player p, String brand)
	{
		PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload("minecraft:brand", new PacketDataSerializer(Unpooled.buffer()).a(brand));
		sendPacket(packet, p);
	}

	@Override
	public void displayScoreboard(Player p, C slot, String id)
	{
		displayScoreboard(p, 3 + slot.getItemMeta(), id);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void spawnFallingBlock(int eid, UUID id, Location l, Player player, MaterialBlock mb)
	{
		int bid = mb.getMaterial().getId() + (mb.getData() << 12);
		PacketPlayOutSpawnEntity m = new PacketPlayOutSpawnEntity();
		new V(m).set("a", 70); // EntityType.FALLING_BLOCK.getTypeId());
		new V(m).set("b", (int) l.getX());
		new V(m).set("c", (int) l.getY());
		new V(m).set("d", (int) l.getZ());
		new V(m).set("e", 0);
		new V(m).set("f", 0);
		new V(m).set("h", 0);
		new V(m).set("i", 0);
		new V(m).set("j", 70);
		new V(m).set("k", bid);
		sendPacket(m, player);
	}

	@Override
	public void displayScoreboard(Player p, int slot, String id)
	{
		PacketPlayOutScoreboardDisplayObjective k = new PacketPlayOutScoreboardDisplayObjective();
		new V(k).set("a", slot);
		new V(k).set("b", id);
		sendPacket(k, p);
	}

	@Override
	public void sendTeam(Player p, String id, String name, String prefix, String suffix, C color, int mode)
	{
		PacketPlayOutScoreboardTeam k = new PacketPlayOutScoreboardTeam();
		new V(k).set("a", id);
		new V(k).set("b", name);
		new V(k).set("i", mode); // 0 = new, 1 = remove, 2 = update, 3 = addplayer, 4 = removeplayer
		new V(k).set("c", prefix);
		new V(k).set("d", suffix);
		new V(k).set("j", 0);
		new V(k).set("f", "never");
		new V(k).set("e", "always");
		new V(k).set("g", color.getItemMeta());
		sendPacket(k, p);
	}

	@Override
	public void sendNewObjective(Player p, String id, String name)
	{
		PacketPlayOutScoreboardObjective k = new PacketPlayOutScoreboardObjective();
		new V(k).set("d", 0);
		new V(k).set("a", id);
		new V(k).set("b", name);
		new V(k).set("c", EnumScoreboardHealthDisplay.INTEGER);
		sendPacket(k, p);
	}

	@Override
	public void sendDeleteObjective(Player p, String id)
	{
		PacketPlayOutScoreboardObjective k = new PacketPlayOutScoreboardObjective();
		new V(k).set("d", 1);
		new V(k).set("a", id);
		new V(k).set("b", "memes");
		new V(k).set("c", EnumScoreboardHealthDisplay.INTEGER);
		sendPacket(k, p);
	}

	@Override
	public void sendEditObjective(Player p, String id, String name)
	{
		PacketPlayOutScoreboardObjective k = new PacketPlayOutScoreboardObjective();
		new V(k).set("d", 2);
		new V(k).set("a", id);
		new V(k).set("b", name);
		new V(k).set("c", EnumScoreboardHealthDisplay.INTEGER);
		sendPacket(k, p);
	}

	@Override
	public void sendScoreUpdate(Player p, String name, String objective, int score)
	{
		PacketPlayOutScoreboardScore k = new PacketPlayOutScoreboardScore();
		new V(k).set("a", name);
		new V(k).set("b", objective);
		new V(k).set("c", score);
		new V(k).set("d", EnumScoreboardAction.CHANGE);
		sendPacket(k, p);
	}

	@Override
	public void sendScoreRemove(Player p, String name, String objective)
	{
		PacketPlayOutScoreboardScore k = new PacketPlayOutScoreboardScore();
		new V(k).set("a", name);
		new V(k).set("b", objective);
		new V(k).set("c", 0);
		new V(k).set("d", EnumScoreboardAction.REMOVE);
		sendPacket(k, p);
	}

	@Override
	public void addTeam(Player p, String id, String name, String prefix, String suffix, C color)
	{
		sendTeam(p, id, name, prefix, suffix, color, 0);
	}

	@Override
	public void updateTeam(Player p, String id, String name, String prefix, String suffix, C color)
	{
		sendTeam(p, id, name, prefix, suffix, color, 2);
	}

	@Override
	public void removeTeam(Player p, String id)
	{
		sendTeam(p, id, "", "", "", C.WHITE, 1);
	}

	@Override
	public void addToTeam(Player p, String id, String... entities)
	{
		PacketPlayOutScoreboardTeam k = new PacketPlayOutScoreboardTeam();
		new V(k).set("a", id);
		new V(k).set("i", 3);
		Collection<String> h = new V(k).get("h");
		h.addAll(new GList<String>(entities));
		sendPacket(k, p);
	}

	@Override
	public void removeFromTeam(Player p, String id, String... entities)
	{
		PacketPlayOutScoreboardTeam k = new PacketPlayOutScoreboardTeam();
		new V(k).set("a", id);
		new V(k).set("i", 4);
		Collection<String> h = new V(k).get("h");
		h.addAll(new GList<String>(entities));
		sendPacket(k, p);
	}

	@Override
	public void updatePassengers(Player p, int vehicle, int... passengers)
	{

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
			Chunk area = p.getWorld().getChunkAt(c.getX(), c.getZ());
			GList<NBTTagCompound> tags = new GList<NBTTagCompound>();

			for(BlockPosition i : ((CraftChunk) area).getHandle().tileEntities.keySet())
			{
				TileEntity tile = ((CraftChunk) area).getHandle().tileEntities.get(i);
				NBTTagCompound tag = new NBTTagCompound();
				tile.b(tag);
				tags.add(tag);
			}

			PacketPlayOutMapChunk m = new PacketPlayOutMapChunk();
			new V(m).set("a", c.getX());
			new V(m).set("b", c.getZ());
			new V(m).set("c", c.getBitMask());
			new V(m).set("d", c.write());
			new V(m).set("e", tags);
			new V(m).set("f", c.isContinuous());

			if(c.isContinuous())
			{
				sendChunkUnload(c.getX(), c.getZ(), p);
			}

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
		for(Player i : area.getWorld().getPlayers())
		{
			if(canSee(i, area))
			{
				sendChunkMap(c, i);
			}
		}
	}

	@Override
	public void sendChunkUnload(int x, int z, Player p)
	{

	}

	@Override
	public void sendChunkUnload(int x, int z, Chunk area)
	{

	}

	@Override
	public void generateChunk(World world, int x, int z)
	{
		world.loadChunk(x, z, true);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setBlock(Location l, MaterialBlock m)
	{
		l.getBlock().setType(m.getMaterial());
		l.getBlock().setData(m.getData());
	}

	@Override
	public void queueChunkUpdate(Chunk c)
	{
		getChunkQueue().queue(c);
	}

	@Override
	public void relight(Chunk c)
	{
		((CraftChunk) c).getHandle().initLighting();
	}

	private int getBitMask(boolean[] sections)
	{
		int bitMask = 0;

		for(int section = 0; section < sections.length; section++)
		{
			if(sections[section])
			{
				bitMask += 1 << section;
			}
		}

		return bitMask;
	}

	private boolean[] getBitMask(int... sections)
	{
		boolean[] m = new boolean[16];

		for(int i : sections)
		{
			m[i] = true;
		}

		return m;
	}

	private boolean[] getBitMaskFT(int from, int to)
	{
		boolean[] m = new boolean[16];

		for(int i = from; i <= to; i++)
		{
			m[i] = true;
		}

		return m;
	}

	@Override
	public void updateSection(Chunk c, int section)
	{
		// TODO real skylight check
		sendPacket(new PacketPlayOutMapChunk(((CraftChunk) c).getHandle(), true, getBitMask(getBitMask(section))), c);
	}

	@Override
	public void updateSections(Chunk c, int from, int to)
	{
		// TODO real skylight check
		sendPacket(new PacketPlayOutMapChunk(((CraftChunk) c).getHandle(), true, getBitMask(getBitMaskFT(from, to))), c);
	}

	@Override
	public void queueSection(Chunk c, int section)
	{
		getChunkQueue().queueSection(c, section);
	}

	@Override
	public void queueSection(Location c)
	{
		queueSection(c.getChunk(), c.getBlockY() >> 4);
	}

	@Override
	public void updateSections(Chunk c, GSet<Integer> v)
	{
		// TODO real skylight check
		sendPacket(new PacketPlayOutMapChunk(((CraftChunk) c).getHandle(), true, getBitMask(getBitMask(v))), c);
	}

	private boolean[] getBitMask(GSet<Integer> v)
	{
		boolean[] m = new boolean[16];

		for(int i : v)
		{
			m[i] = true;
		}

		return m;
	}

	@Override
	public void injectBlockInstance(MaterialBlock mb, Object o)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void scroll(Player p, int slot)
	{
		sendPacket(new PacketPlayOutHeldItemSlot(slot), p);
	}

	@Override
	public int getAction(Object packetIn)
	{
		return ((PacketPlayInEntityAction) packetIn).b().ordinal();
	}

	@Override
	public Vector getDirection(Object packet)
	{
		float yaw = new V(((PacketPlayInFlying) packet), true).get("yaw");
		float pitch = new V(((PacketPlayInFlying) packet), true).get("pitch");
		return new Vector(-Math.cos(pitch) * Math.sin(yaw), -Math.sin(pitch), Math.cos(pitch) * Math.cos(yaw));
	}

	@Override
	public void spawnArmorStand(int eid, UUID id, Location l, int data, Player player)
	{
		PacketPlayOutSpawnEntity m = new PacketPlayOutSpawnEntity();
		new V(m).set("a", eid);
		new V(m).set("b", id);
		new V(m).set("c", l.getX());
		new V(m).set("d", l.getY());
		new V(m).set("e", l.getZ());
		new V(m).set("f", 0);
		new V(m).set("g", 0);
		new V(m).set("h", 0);
		new V(m).set("i", 0);
		new V(m).set("j", 0);
		new V(m).set("k", 78);
		new V(m).set("l", 0);
		sendPacket(m, player);
	}

	@Override
	public void removeEntity(int eid, Player p)
	{
		PacketPlayOutEntityDestroy d = new PacketPlayOutEntityDestroy(eid);
		sendPacket(d, p);
	}

	@Override
	public void moveEntityRelative(int eid, Player p, double x, double y, double z, boolean onGround)
	{
		PacketPlayOutRelEntityMove r = new PacketPlayOutRelEntityMove(
				eid, // super: a
				(byte) (x * 4096), // b
				(byte) (y * 4096), // c
				(byte) (z * 4096), // d
				onGround // g
		);
		sendPacket(r, p);
	}

	@Override
	public void teleportEntity(int eid, Player p, Location l, boolean onGround)
	{
		PacketPlayOutEntityTeleport t = new PacketPlayOutEntityTeleport();
		new V(t).set("a", eid);
		new V(t).set("b", (int) l.getX());
		new V(t).set("c", (int) l.getY());
		new V(t).set("d", (int) l.getZ());
		new V(t).set("e", (byte) 0);
		new V(t).set("f", (byte) 0);
		new V(t).set("g", onGround);
		sendPacket(t, p);
	}

	@Override
	public void sendGlowingColorMeta(Player p, Entity glowing, C color)
	{

	}

	@Override
	public void sendRemoveGlowingColorMeta(Player p, Entity glowing)
	{

	}

	@SuppressWarnings("deprecation")
	@Override
	public void sendEffect(Player p, Entity entity, PotionEffectType type, int duration, int amp, boolean ambient, boolean showParticles)
	{
		PacketPlayOutEntityEffect k = new PacketPlayOutEntityEffect();
		int e = 0;

		if(ambient)
		{
			e = (byte) (e | 1);
		}

		if(showParticles)
		{
			e = (byte) (e | 2);
		}

		new V(k).set("a", entity.getEntityId());
		new V(k).set("b", (byte) (type.getId() & 255));
		new V(k).set("c", (byte) (amp & 255));
		new V(k).set("d", duration > 32767 ? 32767 : duration);
		new V(k).set("e", (byte) e);
		sendPacket(k, p);
	}

	@Override
	public void removeEffect(Player p, Entity entity, PotionEffectType type)
	{
		sendEffect(p, entity, type, 0, 0, true, false);
	}

	@Override
	public void sendGlowingColorMetaEntity(Player p, UUID euid, C color)
	{

	}

	@Override
	public void sendGlowingColorMetaName(Player p, String euid, C color)
	{

	}

	@Override
	public void sendRemoveGlowingColorMetaEntity(Player p, UUID glowing)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void sendRemoveGlowingColorMetaPlayer(Player p, UUID glowing, String name)
	{
		// TODO Auto-generated method stub

	}
}
