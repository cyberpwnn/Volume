package com.volmit.volume.bukkit.util.net;

import com.volmit.volume.bukkit.pawn.Documented;

/**
 * Represents a range of protocols
 *
 * @author cyberpwn
 */
@Documented
public class ProtocolRange
{
	private Protocol from;
	private Protocol to;

	/**
	 * Create a protocol range
	 *
	 * @param from
	 *            from the minimum (inclusive)
	 * @param to
	 *            to the maxiumum (inclusive)
	 */
	public ProtocolRange(Protocol from, Protocol to)
	{
		this.from = from;
		this.to = to;
	}

	/**
	 * Get the minimum protocol
	 *
	 * @return the protocol
	 */
	public Protocol getFrom()
	{
		return from;
	}

	/**
	 * Get the maximum protocol
	 *
	 * @return the protocol
	 */
	public Protocol getTo()
	{
		return to;
	}

	/**
	 * Does the supplied protocol fit within this range?
	 *
	 * @param p
	 *            the protocol range
	 * @return returns true if the protocol fits in the given range
	 */
	public boolean contains(Protocol p)
	{
		return p.getCVersion() >= from.getCVersion() && p.getCVersion() <= to.getCVersion();
	}

	@Override
	public String toString()
	{
		return from.getVersionString() + " - " + to.getVersionString();
	}
}
