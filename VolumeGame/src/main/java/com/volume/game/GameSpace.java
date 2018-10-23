package com.volume.game;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.volmit.volume.bukkit.util.world.Cuboid;
import com.volmit.volume.lang.collections.GList;

public interface GameSpace extends Ticked
{
	public void setTime(long time);

	public long getTime();

	public void resyncTime();

	public WeatherType getWeather();

	public void setWeather(WeatherType type);

	public void resyncWeather();

	public GList<Entity> getEntities();

	public GList<LivingEntity> getLivingEntities();

	public GList<Chunk> getChunks();

	public World getWorld();

	public Cuboid getCuboid();

	public boolean isWithin(Entity e);

	public boolean isWithin(Location l);

	public boolean isWithin(Chunk c);
}
