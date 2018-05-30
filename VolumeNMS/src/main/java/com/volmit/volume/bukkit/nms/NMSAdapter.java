package com.volmit.volume.bukkit.nms;

import com.volmit.volume.bukkit.util.net.Protocol;
import com.volmit.volume.bukkit.util.net.ProtocolRange;

public abstract class NMSAdapter implements IAdapter
{
	private ProtocolRange pr;

	public NMSAdapter()
	{
		this(Protocol.EARLIEST, Protocol.LATEST);
	}

	public NMSAdapter(ProtocolRange pr)
	{
		this.pr = pr;
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
}
