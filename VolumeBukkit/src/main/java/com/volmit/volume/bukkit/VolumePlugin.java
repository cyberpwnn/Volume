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

/**
 * Represents a volume plugin
 *
 * @author cyberpwn
 *
 */
public abstract class VolumePlugin extends JavaPlugin implements IPawn
{
	private TaskManager taskManager;
	private PawnManager pawnManager;
	private ServiceManager serviceManager;
	public static VolumePlugin vpi;
	private File f;

	/**
	 * Exposes the GetFile method in JavaPlugin for obtaining the plugin's jar file
	 * location
	 */
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

		getTaskManager().shutDown();
	}

	/**
	 * Obtain a file within your data folder. Each parameter is a file tree deeper.
	 *
	 * @param path
	 *            path in list form. For example "data", "player.yml" would return
	 *            YourPlugin/data/player.yml
	 * @return the file
	 */
	public File getDataFile(String... path)
	{
		File ff = new File(getDataFolder(), new GList<String>(path).toString("/"));
		ff.getParentFile().mkdirs();
		return ff;
	}

	protected void setTaskManager(TaskManager wTaskManager)
	{
		this.taskManager = wTaskManager;
	}

	/**
	 * Obtain a file (folder) within your data folder. Each parameter is a file tree
	 * deeper. The file tree of folders is created before returning
	 *
	 * @param path
	 *            path in list form. For example "data", "players" would return
	 *            YourPlugin/data/players
	 * @return the file
	 */
	public File getDataFolder(String... folders)
	{
		File ff = new File(getDataFolder(), new GList<String>(folders).toString("/"));
		ff.mkdirs();
		return ff;
	}

	/**
	 * Get the task manager
	 *
	 * @return the task manager
	 */
	public TaskManager getTaskManager()
	{
		return taskManager;
	}

	/**
	 * Get the pawn manager
	 *
	 * @return the pawn manager
	 */
	public PawnManager getPawnManager()
	{
		return pawnManager;
	}

	/**
	 * Shortcut for getting services from U.class
	 *
	 * @param s
	 *            the service class
	 * @return the service
	 */
	public <T extends IService> T getService(Class<? extends T> s)
	{
		return getServiceManager().getService(s);
	}

	/**
	 * Get the service manager
	 *
	 * @return the service manager
	 */
	public ServiceManager getServiceManager()
	{
		return serviceManager;
	}

	/**
	 * Use CommandTag annotation in your plugin class instead
	 *
	 * @param vs
	 *            sender
	 */
	@Deprecated
	public void tagify(VolumeSender vs)
	{

	}
}
