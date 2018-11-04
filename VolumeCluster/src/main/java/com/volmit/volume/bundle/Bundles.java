package com.volmit.volume.bundle;

import com.volmit.volume.bundle.adapter.NodeAdapter;
import com.volmit.volume.lang.collections.GList;

public class Bundles {
	private static final GList<NodeAdapter<?>> adapters = new GList<NodeAdapter<?>>();

	public static void registerAdapter(NodeAdapter<?> a)
	{
		adapters.add(a);
	}
	
	private static void loadAdapters() {
		if(adapters.isEmpty())
		{
			
		}
	}
	
	static
	{
		loadAdapters();
	}
}
