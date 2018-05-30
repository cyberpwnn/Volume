package com.volmit.volume.bukkit.dungeon;

import com.volmit.volume.bukkit.util.world.Direction;

public interface IRoom
{
	public boolean canConnect(Direction d);
}
