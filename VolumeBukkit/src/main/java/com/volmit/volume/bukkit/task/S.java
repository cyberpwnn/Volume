package com.volmit.volume.bukkit.task;

public class S implements Runnable
{
	public static TaskManager m;

	public S()
	{
		m.sync(this);
	}

	public S(long delay)
	{
		m.sync(delay, this);
	}

	public S(Runnable runnable)
	{
		m.sync(runnable);
	}

	public S(long delay, Runnable runnable)
	{
		m.sync(delay, runnable);
	}

	@Override
	public void run()
	{

	}
}
