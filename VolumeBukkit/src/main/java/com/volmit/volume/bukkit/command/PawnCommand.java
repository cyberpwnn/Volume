package com.volmit.volume.bukkit.command;

import com.volmit.volume.lang.collections.GList;

/**
 * Represents a pawn command
 *
 * @author cyberpwn
 *
 */
public abstract class PawnCommand implements ICommand
{
	private GList<String> nodes;
	private String node;

	/**
	 * Override this with a super constructor as most commands shouldnt change these
	 * parameters
	 * 
	 * @param node
	 *            the node (primary node) i.e. volume
	 * @param nodes
	 *            the aliases. i.e. v, vol, bile
	 */
	public PawnCommand(String node, String... nodes)
	{
		this.node = node;
		this.nodes = new GList<String>(nodes);
	}

	@Override
	public String getNode()
	{
		return node;
	}

	@Override
	public GList<String> getNodes()
	{
		return nodes;
	}

	@Override
	public GList<String> getAllNodes()
	{
		return getNodes().copy().qadd(getNode());

	}

	@Override
	public void addNode(String node)
	{
		getNodes().add(node);
	}
}
