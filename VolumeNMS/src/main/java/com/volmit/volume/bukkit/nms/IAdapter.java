package com.volmit.volume.bukkit.nms;

import java.util.UUID;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.volmit.volume.bukkit.nms.adapter.AbstractChunk;
import com.volmit.volume.bukkit.nms.adapter.ChunkSendQueue;
import com.volmit.volume.bukkit.pawn.IPawn;
import com.volmit.volume.bukkit.util.net.ProtocolRange;
import com.volmit.volume.bukkit.util.text.C;
import com.volmit.volume.bukkit.util.world.MaterialBlock;
import com.volmit.volume.lang.collections.GSet;

public interface IAdapter extends IPawn
{
	public void spawnFallingBlock(int eid, UUID id, Location l, Player player, MaterialBlock mb);

	public void sendBrand(Player p, String brand);

	public void sendRemoveGlowingColorMeta(Player p, Entity glowing);

	public void sendRemoveGlowingColorMetaEntity(Player p, UUID glowing);

	public void sendRemoveGlowingColorMetaPlayer(Player p, UUID glowing, String name);

	public void sendGlowingColorMeta(Player p, Entity glowing, C color);

	public void sendGlowingColorMetaEntity(Player p, UUID euid, C color);

	public void sendGlowingColorMetaName(Player p, String euid, C color);

	public void sendEffect(Player p, Entity entity, PotionEffectType type, int duration, int amp, boolean ambient, boolean showParticles);

	public void removeEffect(Player p, Entity entity, PotionEffectType type);

	public void sendTeam(Player p, String id, String name, String prefix, String suffix, C color, int mode);

	public void addTeam(Player p, String id, String name, String prefix, String suffix, C color);

	public void updateTeam(Player p, String id, String name, String prefix, String suffix, C color);

	public void addToTeam(Player p, String id, String... entities);

	public void removeTeam(Player p, String id);

	public void removeFromTeam(Player p, String id, String... entities);

	public void displayScoreboard(Player p, int slot, String id);

	public void displayScoreboard(Player p, C c, String id);

	public void sendNewObjective(Player p, String id, String name);

	public void sendDeleteObjective(Player p, String id);

	public void sendEditObjective(Player p, String id, String name);

	public void sendScoreUpdate(Player p, String name, String objective, int score);

	public void sendScoreRemove(Player p, String name, String objective);

	public void updatePassengers(Player p, int vehicle, int... passengers);

	public void injectBlockInstance(MaterialBlock mb, Object o);

	public void updateSection(Chunk c, int section);

	public void updateSections(Chunk c, int from, int to);

	public void updateSections(Chunk c, GSet<Integer> v);

	public void queueSection(Chunk c, int section);

	public void queueSection(Location c);

	public void removeEntity(int eid, Player p);

	public void moveEntityRelative(int eid, Player p, double x, double y, double z, boolean onGround);

	public void teleportEntity(int eid, Player p, Location l, boolean onGround);

	public void spawnArmorStand(int eid, UUID id, Location l, int data, Player player);

	public void relight(Chunk c);

	public void queueChunkUpdate(Chunk c);

	public void setBlock(Location l, MaterialBlock m);

	public void generateChunk(World world, int x, int z);

	public void sendChunkUnload(int x, int z, Player p);

	public void sendChunkUnload(int x, int z, Chunk area);

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

	public ChunkSendQueue getChunkQueue();

	public void scroll(Player sender, int previous);

	public int getAction(Object packetIn);

	public Vector getDirection(Object packet);
}
