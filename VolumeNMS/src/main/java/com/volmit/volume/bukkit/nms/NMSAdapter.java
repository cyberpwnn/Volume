package com.volmit.volume.bukkit.nms;

import com.volmit.volume.bukkit.nms.adapter.ChunkSendQueue;
import com.volmit.volume.bukkit.util.net.Protocol;
import com.volmit.volume.bukkit.util.net.ProtocolRange;
import com.volmit.volume.lang.collections.GMap;

public abstract class NMSAdapter implements IAdapter
{
	public static int INTERVAL = 1;
	public static int VOLUME = 24;
	private ProtocolRange pr;
	private ChunkSendQueue chunkQueue;
	protected GMap<String, String> teamCache;

	public NMSAdapter()
	{
		this(Protocol.EARLIEST, Protocol.LATEST);
	}

	public NMSAdapter(ProtocolRange pr)
	{
		this.pr = pr;
		chunkQueue = new ChunkSendQueue(INTERVAL, VOLUME);
		chunkQueue.start();
		teamCache = new GMap<String, String>();
	}

	public NMSAdapter(Protocol from, Protocol to)
	{
		this(from.to(to));
	}

	@Override
	public ProtocolRange getSupportedProtocol()
	{
		return pr;
	}

	@Override
	public ChunkSendQueue getChunkQueue()
	{
		return chunkQueue;
	}
}
