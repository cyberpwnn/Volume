package com.volmit.volume.task;

import org.bukkit.Bukkit;

import com.volmit.volume.bukkit.VolumePlugin;

public class TaskManager
{
	private VolumePlugin p;

	public TaskManager(VolumePlugin p)
	{
		this.p = p;
		A.m = this;
		AR.m = this;
		S.m = this;
		SR.m = this;
	}

	public void cancel(int i)
	{
		Bukkit.getScheduler().cancelTask(i);
	}

	public int sync(long delay, Runnable r)
	{
		return Bukkit.getScheduler().scheduleSyncDelayedTask(p, r, delay);
	}

	public int sync(Runnable r)
	{
		return sync(0, r);
	}

	public int syncRepeating(long interval, Runnable r)
	{
		return syncRepeating(0, interval, r);
	}

	public int syncRepeating(long delay, long interval, Runnable r)
	{
		return Bukkit.getScheduler().scheduleSyncRepeatingTask(p, r, delay, interval);
	}

	@SuppressWarnings("deprecation")
	public int async(long delay, Runnable r)
	{
		return Bukkit.getScheduler().scheduleAsyncDelayedTask(p, r, delay);
	}

	public int async(Runnable r)
	{
		return async(0, r);
	}

	public int asyncRepeating(long interval, Runnable r)
	{
		return asyncRepeating(0, interval, r);
	}

	@SuppressWarnings("deprecation")
	public int asyncRepeating(long delay, long interval, Runnable r)
	{
		return Bukkit.getScheduler().scheduleAsyncRepeatingTask(p, r, delay, interval);
	}
}
