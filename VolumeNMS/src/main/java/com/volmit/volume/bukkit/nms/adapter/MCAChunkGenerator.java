package com.volmit.volume.bukkit.nms.adapter;

import org.bukkit.World;

import com.volmit.volume.lang.collections.Callback;

public class MCAChunkGenerator
{
	private int minx;
	private int maxx;
	private int minz;
	private int maxz;
	private World world;

	public static final int START_GEN = -13371;
	public static final int DONE = -16371111;

	public MCAChunkGenerator(World world, int x, int z)
	{
		this.world = world;
		this.minx = x * 32;
		this.minz = z * 32;
		this.maxx = minx + 32;
		this.maxz = minz + 32;
	}

	public void gen(Callback<Integer> genned)
	{
		genned.run(START_GEN);
		int g = 0;

		for(int i = minx; i < maxx; i++)
		{
			for(int j = minz; j < maxz; j++)
			{
				g++;
				world.loadChunk(i, j, true);
				genned.run(g);
			}
		}

		g = 0;
		genned.run(DONE);
	}
}
