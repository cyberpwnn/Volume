package com.volmit.volume.bukkit.command;

import com.volmit.volume.lang.collections.GList;

public abstract class PawnCommand implements ICommand
{
	private GList<String> nodes;
	private String node;

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
