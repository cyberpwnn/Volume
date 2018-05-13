package com.volmit.volume.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import com.volmit.volume.bukkit.pawn.IPawn;
import com.volmit.volume.bukkit.pawn.PawnManager;
import com.volmit.volume.bukkit.service.IService;
import com.volmit.volume.bukkit.service.ServiceManager;
import com.volmit.volume.bukkit.task.TaskManager;

public abstract class VolumePlugin extends JavaPlugin implements IPawn
{
	private TaskManager taskManager;
	private PawnManager pawnManager;
	private ServiceManager serviceManager;
	public static VolumePlugin vpi;

	@Override
	public void onEnable()
	{
		vpi = this;
		taskManager = new TaskManager(this);

		try
		{
			pawnManager = new PawnManager(this);
			pawnManager.getBase().attach(serviceManager = new ServiceManager(this));
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

	public PawnManager getPawnManager()
	{
		return pawnManager;
	}

	public <T extends IService> T getService(Class<? extends T> s)
	{
		return getServiceManager().getService(s);
	}

	public ServiceManager getServiceManager()
	{
		return serviceManager;
	}
}
