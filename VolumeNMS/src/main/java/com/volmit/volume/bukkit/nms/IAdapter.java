package com.volmit.volume.bukkit.nms;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.volmit.volume.bukkit.nms.adapter.AbstractChunk;
import com.volmit.volume.bukkit.pawn.IPawn;
import com.volmit.volume.bukkit.util.net.ProtocolRange;

public interface IAdapter extends IPawn
{
	public AbstractChunk copy(Chunk c);

	public void sendChunkMap(AbstractChunk c, Player p);

	public void sendChunkMap(AbstractChunk c, Chunk area);

	public void pathFind(LivingEntity e, Location l, boolean sprint, double speed);

	public ProtocolRange getSupportedProtocol();

	public void addPacketHandler(IPacketHandler h);

	public void removePacketHandler(IPacketHandler h);

	public void sendPacket(Object packet);

	public void sendPacket(Object packet, World world);

	public void sendPacket(Object packet, Player player);

	public void sendPacket(Object packet, Location location);

	public void sendPacket(Object packet, Chunk chunk);

	public int getViewDistance(Player player);

	public boolean canSee(Player player, Location location);

	public boolean canSee(Player player, Chunk chunk);

	public void sendPickup(Entity drop, Entity who);

	public static boolean isWithin(Chunk center, Chunk check, int viewDistance)
	{
		return Math.abs(center.getX() - check.getX()) <= viewDistance && Math.abs(center.getZ() - check.getZ()) <= viewDistance;
	}
}
