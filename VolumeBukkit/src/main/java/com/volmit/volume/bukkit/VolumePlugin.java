package com.volmit.volume.bukkit;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import com.volmit.volume.bukkit.command.VolumeSender;
import com.volmit.volume.bukkit.pawn.IPawn;
import com.volmit.volume.bukkit.pawn.PawnManager;
import com.volmit.volume.bukkit.service.IService;
import com.volmit.volume.bukkit.service.ServiceManager;
import com.volmit.volume.bukkit.task.TaskManager;
import com.volmit.volume.lang.collections.GList;

public abstract class VolumePlugin extends JavaPlugin implements IPawn
{
	private TaskManager taskManager;
	private PawnManager pawnManager;
	private ServiceManager serviceManager;
	public static VolumePlugin vpi;
	private File f;

	@Override
	public File getFile()
	{
		return f;
	}

	@Override
	public void onEnable()
	{
		vpi = this;
		f = getFile();
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

	public File getDataFile(String... path)
	{
		File ff = new File(getDataFolder(), new GList<String>(path).toString("/"));
		ff.getParentFile().mkdirs();
		return ff;
	}

	public File getDataFolder(String... folders)
	{
		File ff = new File(getDataFolder(), new GList<String>(folders).toString("/"));
		ff.mkdirs();
		return ff;
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

	public void tagify(VolumeSender vs)
	{
		// TODO Auto-generated method stub

	}
}
