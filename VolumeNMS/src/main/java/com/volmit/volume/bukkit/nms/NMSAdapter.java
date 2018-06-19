package com.volmit.volume.bukkit.nms;

import com.volmit.volume.bukkit.nms.adapter.ChunkSendQueue;
import com.volmit.volume.bukkit.util.net.Protocol;
import com.volmit.volume.bukkit.util.net.ProtocolRange;

public abstract class NMSAdapter implements IAdapter
{
	public static int INTERVAL = 1;
	public static int VOLUME = 8;
	private ProtocolRange pr;
	private ChunkSendQueue chunkQueue;

	public NMSAdapter()
	{
		this(Protocol.EARLIEST, Protocol.LATEST);
	}

	public NMSAdapter(ProtocolRange pr)
	{
		this.pr = pr;
		chunkQueue = new ChunkSendQueue(INTERVAL, VOLUME);
		chunkQueue.start();
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
