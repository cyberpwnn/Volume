package com.volmit.volume.bukkit.nms.adapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.bukkit.Chunk;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;

public class AbstractChunk
{
	public static final int DEFAULT_BIOME = Biome.PLAINS.ordinal();

	private ChunkSection[] sections;
	private byte[] biome;
	private int x;
	private int z;
	private boolean sky;

	public AbstractChunk()
	{
		sections = new ChunkSection[16];
		biome = new byte[256];
	}

	public AbstractChunk(Chunk chunk)
	{
		this();
		x = chunk.getX();
		z = chunk.getZ();
		absorb(chunk);
	}

	@SuppressWarnings("deprecation")
	public void absorb(Chunk c)
	{
		setX(c.getX());
		setZ(c.getZ());

		for(int i = 0; i < 16; i++)
		{
			for(int j = 0; j < 16; j++)
			{
				for(int k = 0; k < 256; k++)
				{
					Block b = c.getBlock(i, k, j);

					if(!b.isEmpty())
					{
						set(i, k, j, b.getTypeId(), b.getData());
						setBlockLight(i, k, j, b.getLightFromBlocks());
						setSkyLight(i, k, j, b.getLightFromSky());
					}
				}

				c.getWorld().getBiome(i + (x * 16), j + (z * 16));
			}
		}
	}

	public boolean isSky()
	{
		return sky;
	}

	public void setSky(boolean sky)
	{
		this.sky = sky;
	}

	public void setBiome(int x, int z, Biome b)
	{
		biome[z * 16 | x] = (byte) b.ordinal();
	}

	public Biome getBiome(int x, int z)
	{
		return Biome.values()[biome[z * 16 | x]];
	}

	public void makeContinuous()
	{
		int h = -1;

		for(int i = 0; i < sections.length; i++)
		{
			if(sections[i] != null)
			{
				h = i;
			}
		}

		if(h >= 0)
		{
			forceContinuous(h + 1);
		}
	}

	public void forceContinuous(int below)
	{
		for(int i = 0; i < below; i++)
		{
			ensureSection(i);
		}
	}

	public void forceFullColumn()
	{
		for(int i = 0; i < sections.length; i++)
		{
			ensureSection(i);
		}
	}

	public byte[] write() throws IllegalStateException, IOException
	{
		ByteArrayOutputStream boas = new ByteArrayOutputStream();

		for(int i = 0; i < sections.length; i++)
		{
			if(sections[i] != null)
			{
				sections[i].writeToBuf(boas, hasSky());
			}
		}

		if(isContinuous())
		{
			boas.write(biome);
		}

		return boas.toByteArray();
	}

	private boolean hasSky()
	{
		return true;
	}

	public boolean isContinuous()
	{
		int m = 0;
		int d = 0;

		for(int section = 0; section < sections.length; section++)
		{
			if(sections[section] != null)
			{
				m++;
			}
		}

		for(int section = 0; section < m; section++)
		{
			if(sections[section] != null)
			{
				d++;
			}
		}

		return d == m;
	}

	public void setSkyLight(int x, int y, int z, byte level)
	{
		int sect = getSection(y);
		int sectY = getSectionY(y);
		ensureSection(sect);
		sections[sect].setSkyLight(x, sectY, z, level);
	}

	public void setBlockLight(int x, int y, int z, byte level)
	{
		int sect = getSection(y);
		int sectY = getSectionY(y);
		ensureSection(sect);
		sections[sect].setBlockLight(x, sectY, z, level);
	}

	public void set(int x, int y, int z, int id, byte data)
	{
		int sect = getSection(y);
		int sectY = getSectionY(y);
		ensureSection(sect);
		sections[sect].setType(x, sectY, z, id, data);
	}

	public void ensureSection(int sect)
	{
		if(sections[sect] == null)
		{
			sections[sect] = new ChunkSection();
		}
	}

	public int getSectionY(int y)
	{
		return y - (getSection(y) * 16);
	}

	public int getBitMask()
	{
		int bitMask = 0;

		for(int section = 0; section < sections.length; section++)
		{
			if(sections[section] != null)
			{
				bitMask += 1 << section;
			}
		}

		return bitMask;
	}

	public int getSection(int y)
	{
		return y >> 4;
	}

	public ChunkSection[] getSections()
	{
		return sections;
	}

	public int getX()
	{
		return x;
	}

	public int getZ()
	{
		return z;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setZ(int z)
	{
		this.z = z;
	}
}
