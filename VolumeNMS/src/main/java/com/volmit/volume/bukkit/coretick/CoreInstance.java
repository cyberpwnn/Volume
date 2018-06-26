package com.volmit.volume.bukkit.coretick;

import com.volmit.volume.lang.collections.GMap;

public class CoreInstance
{
	protected GMap<String, Class<?>> swap;
	protected GMap<String, Integer> ids;
	protected GMap<String, String> enumKeys;

	public CoreInstance()
	{
		swap = new GMap<String, Class<?>>();
		enumKeys = new GMap<String, String>();
		ids = new GMap<String, Integer>();
	}

	public void putSwap(String swap, String mcKey, int id, Class<? extends BlockInjection> clazz)
	{
		this.swap.put(swap, clazz);
		this.enumKeys.put(swap, mcKey);
		this.ids.put(swap, id);
	}

	public void inject()
	{

	}

	public GMap<String, Class<?>> getSwap()
	{
		return swap;
	}

	public GMap<String, Integer> getIds()
	{
		return ids;
	}

	public GMap<String, String> getEnumKeys()
	{
		return enumKeys;
	}
}