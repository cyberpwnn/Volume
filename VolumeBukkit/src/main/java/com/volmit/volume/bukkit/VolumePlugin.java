package com.volmit.volume.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import com.volmit.volume.pawn.IPawn;
import com.volmit.volume.pawn.PawnManager;
import com.volmit.volume.task.TaskManager;

public abstract class VolumePlugin extends JavaPlugin implements IPawn
{
	private TaskManager taskManager;
	private PawnManager pawnManager;

	@Override
	public void onEnable()
	{
		taskManager = new TaskManager(this);

		try
		{
			pawnManager = new PawnManager(this);
			pawnManager.start();
		}

		catch(Throwable e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable()
	{
		try
		{
			pawnManager.stop();
		}

		catch(Throwable e)
		{
			e.printStackTrace();
		}
	}

	public TaskManager getTaskManager()
	{
		return taskManager;
	}
}
