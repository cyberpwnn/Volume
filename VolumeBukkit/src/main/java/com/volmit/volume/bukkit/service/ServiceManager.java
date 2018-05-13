package com.volmit.volume.bukkit.service;

import com.volmit.volume.bukkit.VolumePlugin;
import com.volmit.volume.bukkit.pawn.IPawn;
import com.volmit.volume.lang.collections.GMap;

public class ServiceManager implements IPawn
{
	private GMap<Class<? extends IService>, IService> running;
	private VolumePlugin vp;

	public ServiceManager(VolumePlugin vp)
	{
		this.vp = vp;
		running = new GMap<Class<? extends IService>, IService>();
	}

	@SuppressWarnings("unchecked")
	public <T extends IService> T getService(Class<? extends T> s)
	{
		if(!isRunning(s))
		{
			try
			{
				IService svc = s.getConstructor().newInstance();
				vp.getPawnManager().getBase().getNextPawnObject(this.getClass()).attach(svc);
				running.put(s, svc);
			}

			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		return (T) running.get(s);
	}

	public boolean isRunning(Class<? extends IService> s)
	{
		return running.containsKey(s);
	}
}
