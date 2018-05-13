package com.volmit.volume.bukkit.util.world;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.volmit.volume.bukkit.pawn.Documented;
import com.volmit.volume.lang.collections.GList;
import com.volmit.volume.lang.collections.GListAdapter;

/**
 * Player utilities
 *
 * @author cyberpwn
 */
@Documented
public class Players
{
	/**
	 * Is the given player online?
	 *
	 * @param player
	 *            the player
	 * @return true if the player is
	 */
	public static boolean isOnline(String player)
	{
		return getPlayer(player) != null;
	}

	/**
	 * Get the given player
	 *
	 * @param player
	 *            the player name
	 * @return the player or null
	 */
	public static Player getPlayer(String player)
	{
		return Bukkit.getPlayer(player);
	}

	/**
	 * Get the off hand
	 *
	 * @param p
	 *            the player
	 * @return the item
	 */
	public static ItemStack getOffHand(Player p)
	{
		return p.getInventory().getItemInOffHand();
	}

	/**
	 * Get the main hand
	 *
	 * @param p
	 *            the player
	 * @return the item
	 */
	public static ItemStack getMainHand(Player p)
	{
		return p.getInventory().getItemInMainHand();
	}

	/**
	 * Set off hand
	 *
	 * @param p
	 *            the player
	 * @param is
	 *            the item stack
	 */
	public static void setOffHand(Player p, ItemStack is)
	{
		p.getInventory().setItemInOffHand(is);
	}

	/**
	 * Set main hand
	 *
	 * @param p
	 *            the player
	 * @param is
	 *            the item stack
	 */
	public static void setMainHand(Player p, ItemStack is)
	{
		p.getInventory().setItemInMainHand(is);
	}

	/**
	 * Swap hands (off and main items)
	 *
	 * @param p
	 *            the player
	 */
	public static void swapHands(Player p)
	{
		ItemStack main = getMainHand(p).clone();
		setMainHand(p, getOffHand(p));
		setOffHand(p, main);
	}

	/**
	 * Is there any player online?
	 *
	 * @return true if at least one player is online
	 */
	public static boolean isAnyOnline()
	{
		return !Bukkit.getOnlinePlayers().isEmpty();
	}

	/**
	 * Get all players in the given world
	 *
	 * @param world
	 *            the world
	 * @return the players
	 */
	public static GList<Player> inWorld(World world)
	{
		return new GList<Player>(world.getPlayers());
	}

	/**
	 * Get all players in the given chunk
	 *
	 * @param chunk
	 *            the chunk
	 * @return the list of players
	 */
	public static GList<Player> inChunk(Chunk chunk)
	{
		return new GList<Player>(new GListAdapter<Entity, Player>()
		{
			@Override
			public Player onAdapt(Entity from)
			{
				if(from.getType().equals(EntityType.PLAYER))
				{
					return (Player) from;
				}

				return null;
			}
		}.adapt(new GList<Entity>(chunk.getEntities())));
	}

	/**
	 * Get all players in the given area
	 *
	 * @param l
	 *            the center location
	 * @param radius
	 *            the three dimensional area radius to search
	 * @return a list of players in the given area
	 */
	public static GList<Player> inArea(Location l, double radius)
	{
		return new GList<Player>(new Area(l, radius).getNearbyPlayers());
	}

	/**
	 * Get all players in the given area
	 *
	 * @param l
	 *            the center location
	 * @param radius
	 *            the three dimensional area radius to search
	 * @return a list of players in the given area
	 */
	public static GList<Player> inArea(Location l, int radius)
	{
		return new GList<Player>(new Area(l, radius).getNearbyPlayers());
	}

	/**
	 * Checks if anyone is online
	 *
	 * @return returns true if there is at least one player online.
	 */
	public static boolean isAnyoneOnline()
	{
		return !getPlayers().isEmpty();
	}

	/**
	 * Get the player count
	 *
	 * @return the player count
	 */
	public static int getPlayerCount()
	{
		return getPlayers().size();
	}

	/**
	 * Randomly pick a player
	 *
	 * @return a random player or null if no one is online.
	 */
	public static Player getRandomPlayer()
	{
		if(isAnyoneOnline())
		{
			return getPlayers().pickRandom();
		}

		return null;
	}

	/**
	 * Get the player based on uuid
	 *
	 * @param id
	 *            the player uid
	 * @return the player matching the uuid or null
	 */
	public static Player getPlayer(UUID id)
	{
		return Bukkit.getPlayer(id);
	}

	/**
	 * Search for multiple player matches. If there is an identical match, nothing
	 * else will be searched. If there is multiple ignored case matches, partials
	 * will not be matched. Else it will match all partials.
	 *
	 * @param search
	 *            the search query
	 * @return a list of partial matches
	 */
	public GList<Player> getPlayers(String search)
	{
		GList<Player> players = getPlayers();
		GList<Player> found = new GList<Player>();

		for(Player i : players)
		{
			if(i.getName().equals(search))
			{
				found.add(i);
			}
		}

		if(!found.isEmpty())
		{
			return found.removeDuplicates();
		}

		for(Player i : players)
		{
			if(i.getName().equalsIgnoreCase(search))
			{
				found.add(i);
			}
		}

		if(!found.isEmpty())
		{
			return found.removeDuplicates();
		}

		for(Player i : players)
		{
			if(i.getName().toLowerCase().contains(search.toLowerCase()))
			{
				found.add(i);
			}
		}

		return found.removeDuplicates();
	}

	/**
	 * Returns a list of ops
	 *
	 * @return ops in glist
	 */
	public static GList<Player> getPlayerWithOps()
	{
		return getPlayers(new GListAdapter<Player, Player>()
		{
			@Override
			public Player onAdapt(Player from)
			{
				return from.isOp() ? from : null;
			}
		});
	}

	/**
	 * Returns a list of non ops
	 *
	 * @return non op list
	 */
	public static GList<Player> getPlayerWithoutOps()
	{
		return getPlayers(new GListAdapter<Player, Player>()
		{
			@Override
			public Player onAdapt(Player from)
			{
				return from.isOp() ? null : from;
			}
		});
	}

	/**
	 * Returns a list of players who have all of the given permissions.
	 *
	 * @param permissions
	 *            a collection of permissions any player in the return list must
	 *            have. Supplying no permissions will return the source list.
	 * @return a glist of players
	 */
	public static GList<Player> getPlayersWithPermission(String... permissions)
	{
		return getPlayers(new GListAdapter<Player, Player>()
		{
			@Override
			public Player onAdapt(Player from)
			{
				for(String i : permissions)
				{
					if(!from.hasPermission(i))
					{
						return null;
					}
				}

				return from;
			}
		});
	}

	/**
	 * Returns a list of players who do not have all of the given permissions.
	 *
	 * @param permissions
	 *            a collection of permissions any player in the return list cannot
	 *            have. Supplying no permissions will return the source list.
	 * @return a glist of players
	 */
	public static GList<Player> getPlayersWithoutPermission(String... permissions)
	{
		return getPlayers(new GListAdapter<Player, Player>()
		{
			@Override
			public Player onAdapt(Player from)
			{
				for(String i : permissions)
				{
					if(from.hasPermission(i))
					{
						return null;
					}
				}

				return from;
			}
		});
	}

	/**
	 * Get a list of players who match the given adapter. Source list comes from all
	 * players currently connected.
	 *
	 * @param adapter
	 *            the glist adapter to determine if the player should be adapted to
	 *            the next list. If the adapter returns null instead of the source
	 *            player, it will not be added to the list.
	 * @return the adapted list of players
	 */
	public static GList<Player> getPlayers(GListAdapter<Player, Player> adapter)
	{
		return (GList<Player>) adapter.adapt(getPlayers());
	}

	/**
	 * Returns a glist of all players currently online
	 *
	 * @return a glist representing all online players
	 */
	public static GList<Player> getPlayers()
	{
		GList<Player> p = new GList<Player>();

		for(Player i : Bukkit.getOnlinePlayers())
		{
			p.add(i);
		}

		return p;
	}

	public static Player getAnyPlayer()
	{
		if(!isAnyOnline())
		{
			return null;
		}

		return getPlayers().get(0);
	}
}
