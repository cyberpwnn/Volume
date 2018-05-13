package com.volmit.volume.bukkit.util.world;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.volmit.volume.bukkit.pawn.Documented;
import com.volmit.volume.lang.collections.GList;
import com.volmit.volume.lang.collections.GMap;

/**
 * A bunch of chunklets for fast reads
 *
 * @author cyberpwn
 *
 */
@Documented
public class ChunkletMesh
{
	private World world;
	private GMap<Integer, GList<Chunklet>> chunklets;
	private GList<Chunklet> all;

	/**
	 * Create a chunklet mesh for fast reading
	 *
	 * @param world
	 *            the world
	 */
	public ChunkletMesh(World world)
	{
		this.world = world;
		this.chunklets = new GMap<Integer, GList<Chunklet>>();
		this.all = new GList<Chunklet>();
	}

	/**
	 * Rebuilds the list of chunklets from the map reference
	 */
	public void rebuildReferences()
	{
		for(Integer i : chunklets.k())
		{
			all.add(chunklets.get(i));
		}
	}

	/**
	 * Get all chunklets from the rebuilt reference
	 *
	 * @return all chunklets
	 */
	public GList<Chunklet> getChunklets()
	{
		return all;
	}

	/**
	 * Check if this mesh contains a location
	 *
	 * @param l
	 *            the location
	 * @return true if it contains the location l
	 */
	public boolean contains(Location l)
	{
		for(Chunklet i : all)
		{
			if(i.contains(l))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Check if this mesh contains a player based on location
	 *
	 * @param p
	 *            the player
	 * @return true if it contains the location of player
	 */
	public boolean contains(Player p)
	{
		return contains(p.getLocation());
	}

	/**
	 * Check if this mesh contains an entity based on location
	 *
	 * @param e
	 *            the entity
	 * @return true if it contains the location of entity
	 */
	public boolean contains(Entity e)
	{
		return contains(e.getLocation());
	}

	/**
	 * Get a list of all the players in the given chunklet mesh
	 *
	 * @return the list of players
	 */
	public GList<Player> getPlayers()
	{
		GList<Player> players = new GList<Player>();

		for(Chunklet i : all)
		{
			players.add(i.getPlayers());
		}

		return players;
	}

	/**
	 * Get a list of all the entities in the given chunklet mesh
	 *
	 * @return the list of entities
	 */
	public GList<Entity> getEntities()
	{
		GList<Entity> entities = new GList<Entity>();

		for(Chunklet i : all)
		{
			entities.add(i.getEntities());
		}

		return entities;
	}

	/**
	 * Add a chunklet to the mesh and rebuild the reference list
	 *
	 * @param c
	 *            the chunklet
	 */
	public void add(Chunklet c)
	{
		if(c.getWorld().equals(world))
		{
			if(!chunklets.containsKey(c.getX()))
			{
				chunklets.put(c.getX(), new GList<Chunklet>());
			}

			if(!chunklets.get(c.getX()).contains(c))
			{
				chunklets.get(c.getX()).add(c);
				rebuildReferences();
			}
		}
	}
}