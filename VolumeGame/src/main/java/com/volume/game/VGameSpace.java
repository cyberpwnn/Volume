package com.volume.game;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.volmit.volume.bukkit.task.TICK;
import com.volmit.volume.bukkit.util.world.Cuboid;
import com.volmit.volume.lang.collections.GList;
import com.volmit.volume.lang.collections.ObjectCache;

public class VGameSpace implements GameSpace
{
	private long time;
	private WeatherType weather;
	private World world;
	private Cuboid cuboid;
	private long timed;
	private ObjectCache<GList<Chunk>> chunks;

	public VGameSpace()
	{
		timed = TICK.tick;
		chunks = new ObjectCache<GList<Chunk>>(() -> new GList<Chunk>(getCuboid().getChunks()));
		resyncTime();
		resyncWeather();
	}

	@Override
	public void setTime(long time)
	{
		this.time = time;
	}

	@Override
	public long getTime()
	{
		return time + (TICK.tick - timed);
	}

	@Override
	public void resyncTime()
	{
		this.time = getWorld().getTime();
	}

	@Override
	public WeatherType getWeather()
	{
		return weather;
	}

	@Override
	public void setWeather(WeatherType type)
	{
		this.weather = type;
	}

	@Override
	public void resyncWeather()
	{
		setWeather(getWorld().hasStorm() ? WeatherType.DOWNFALL : WeatherType.CLEAR);
	}

	@Override
	public GList<Entity> getEntities()
	{
		return getCuboid().getEntities();
	}

	@Override
	public GList<LivingEntity> getLivingEntities()
	{
		return getCuboid().getLivingEntities();
	}

	@Override
	public GList<Chunk> getChunks()
	{
		return chunks.get();
	}

	@Override
	public World getWorld()
	{
		return world;
	}

	@Override
	public Cuboid getCuboid()
	{
		return cuboid;
	}

	@Override
	public boolean isWithin(Entity e)
	{
		return isWithin(e.getLocation());
	}

	@Override
	public boolean isWithin(Location l)
	{
		return getCuboid().contains(l);
	}

	@Override
	public boolean isWithin(Chunk c)
	{
		return getChunks().contains(c);
	}

	@Override
	public void tick()
	{
		time += 200;
		timed = TICK.tick;
	}

	@Override
	public int getTickRate()
	{
		return 200;
	}
}
