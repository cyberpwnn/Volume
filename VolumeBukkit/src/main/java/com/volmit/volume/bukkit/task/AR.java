package com.volmit.volume.bukkit.task;

public class AR implements Runnable
{
	public static TaskManager m;
	private int id = 0;

	public AR()
	{
		this(0);
	}

	public AR(int interval)
	{
		this(interval, 0);
	}

	public AR(int interval, int delay)
	{
		id = m.asyncRepeating(delay, interval, this);
	}

	public AR(Runnable runnable)
	{
		this(0, runnable);
	}

	public AR(int interval, Runnable runnable)
	{
		this(interval, 0, runnable);
	}

	public AR(int interval, int delay, Runnable runnable)
	{
		id = m.asyncRepeating(delay, interval, runnable);
	}

	public void cancel()
	{
		m.cancel(id);
	}

	public int getId()
	{
		return id;
	}

	@Override
	public void run()
	{

	}
}
