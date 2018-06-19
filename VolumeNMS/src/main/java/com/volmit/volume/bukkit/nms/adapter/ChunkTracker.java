package com.volmit.volume.bukkit.nms.adapter;

import org.bukkit.Chunk;
import org.bukkit.Location;

import com.volmit.volume.bukkit.U;
import com.volmit.volume.bukkit.nms.NMSSVC;
import com.volmit.volume.lang.collections.GMap;
import com.volmit.volume.lang.collections.GSet;

public class ChunkTracker
{
	private GMap<Chunk, GSet<Integer>> ig;

	public ChunkTracker()
	{
		ig = new GMap<Chunk, GSet<Integer>>();
	}

	public void hit(Location l)
	{
		if(!ig.containsKey(l.getChunk()))
		{
			ig.put(l.getChunk(), new GSet<Integer>());
		}

		ig.get(l.getChunk()).add(l.getBlockY() >> 4);
	}

	public void flush()
	{
		for(Chunk i : ig.k())
		{
			for(int j : ig.get(i))
			{
				U.getService(NMSSVC.class).queueSection(i, j);
			}
		}

		ig.clear();
	}
}
