package com.volmit.volume.bukkit.nms.adapter;

public class IDUtils
{
	public static int getCombined(int id, int data)
	{
		return (id << 4) + data;
	}

	public static int getId(int combined)
	{
		return combined >> 4;
	}

	public static byte getData(int combined)
	{
		return (byte) (combined & 15);
	}
}
