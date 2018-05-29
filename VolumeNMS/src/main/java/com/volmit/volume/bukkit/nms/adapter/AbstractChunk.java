package com.volmit.volume.bukkit.nms.adapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AbstractChunk
{
	private ChunkSection[] sections;
	private int x;
	private int z;

	public AbstractChunk()
	{
		sections = new ChunkSection[16];
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
			boas.write(new byte[256]);
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
