package com.volmit.volume.bukkit.command;

import com.volmit.volume.bukkit.pawn.IPawn;
import com.volmit.volume.lang.collections.GList;

public interface ICommand extends IPawn
{
	public String getNode();

	public GList<String> getNodes();

	public GList<String> getAllNodes();

	public void addNode(String node);

	public boolean handle(VolumeSender sender, String[] args);
}
